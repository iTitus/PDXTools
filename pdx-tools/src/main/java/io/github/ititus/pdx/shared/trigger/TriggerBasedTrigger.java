package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.util.function.Predicate;

public abstract class TriggerBasedTrigger extends Trigger {

    public final ImmutableList<Trigger> children;

    protected TriggerBasedTrigger(Triggers triggers, IPdxScript s) {
        this(triggers, s, null);
    }

    protected TriggerBasedTrigger(Triggers triggers, IPdxScript s, Predicate<String> keyFilter) {
        super(triggers);
        this.children = create(s, keyFilter);
    }

    protected TriggerBasedTrigger(Triggers triggers, ImmutableList<Trigger> children) {
        super(triggers);
        this.children = children;
    }

    protected void localiseChildren(MutableList<String> list, String language, int indent) {
        list.addAllIterable(localise(language, indent, children));
    }

    @FunctionalInterface
    public interface Evaluator {

        static Evaluator not(Evaluator e) {
            return (scope, children) -> !e.evaluate(scope, children);
        }

        boolean evaluate(Scope scope, ImmutableList<Trigger> children);
    }
}
