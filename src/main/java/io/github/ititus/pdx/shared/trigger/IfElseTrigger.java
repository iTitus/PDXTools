package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public class IfElseTrigger extends TriggerBasedTrigger {

    private static final Predicate<String> LIMIT = "limit"::equals;
    private static final Predicate<String> NOT_LIMIT = not(LIMIT);

    public final ImmutableList<IfElse> branches;

    private IfElseTrigger(Triggers triggers, ImmutableList<IfElse> branches, ImmutableList<Trigger> elseTriggers) {
        super(triggers, elseTriggers);
        this.branches = branches;
    }

    public static IfElseTrigger dummy(Triggers triggers, IPdxScript s) {
        return null;
    }

    public static Builder builder(Triggers triggers) {
        return new Builder(triggers);
    }

    @Override
    public boolean evaluate(Scope scope) {
        for (IfElse b : branches) {
            if (evaluateAnd(scope, b.limit)) {
                return evaluateAnd(scope, b.children);
            }
        }

        if (children == null) {
            throw new IllegalStateException("all if/else_if evaluated to false but there is no else");
        }

        return evaluateAnd(scope, children);
    }

    @Override
    protected ImmutableList<String> localise(String language, int indent) {
        boolean first = true;
        MutableList<String> list = Lists.mutable.of();
        list.add("if_then_else:");
        for (IfElse b : branches) {
            if (first) {
                list.add(indent(indent + 1) + " - if:");
                first = false;
            } else {
                list.add(indent(indent + 1) + " - else_if:");
            }

            list.add(indent(indent + 2) + " - condition:");
            list.addAllIterable(localise(language, indent + 3, b.limit));

            list.add(indent(indent + 2) + " - triggers:");
            list.addAllIterable(localise(language, indent + 3, b.children));
        }

        if (children != null) {
            list.add(indent(indent + 1) + " - else triggers:");
            list.addAllIterable(localise(language, indent + 2, children));
        }

        return list.toImmutable();
    }

    public record IfElse(
            ImmutableList<Trigger> children,
            ImmutableList<Trigger> limit
    ) {
    }

    public static final class Builder {

        private final Triggers triggers;
        private final MutableList<IfElse> branches;
        private ImmutableList<Trigger> elseTriggers;

        private Builder(Triggers triggers) {
            this.triggers = triggers;
            this.branches = Lists.mutable.empty();
            this.elseTriggers = null;
        }

        public Builder addIf(IPdxScript s) {
            if (branches.notEmpty() || elseTriggers != null) {
                throw new IllegalStateException();
            }

            ImmutableList<Trigger> limit = triggers.create(s.expectObject().get("limit"));
            ImmutableList<Trigger> children = triggers.create(s, NOT_LIMIT);
            branches.add(new IfElse(limit, children));

            return this;
        }

        public Builder addElseIf(IPdxScript s) {
            if (branches.isEmpty() || elseTriggers != null) {
                throw new IllegalStateException();
            }

            s.expectImplicitList().forEach(s_ -> {
                ImmutableList<Trigger> limit = triggers.create(s_.expectObject());
                ImmutableList<Trigger> children = triggers.create(s_, NOT_LIMIT);
                branches.add(new IfElse(limit, children));
            });

            return this;
        }

        public Builder addElse(IPdxScript s) {
            if (branches.isEmpty() || elseTriggers != null) {
                throw new IllegalStateException();
            }

            elseTriggers = triggers.create(s);

            return this;
        }

        public IfElseTrigger build() {
            if (branches.isEmpty()) {
                throw new IllegalStateException();
            }

            return new IfElseTrigger(triggers, branches.toImmutable(), elseTriggers);
        }
    }
}
