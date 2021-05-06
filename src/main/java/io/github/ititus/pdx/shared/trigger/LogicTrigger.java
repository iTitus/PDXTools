package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class LogicTrigger extends TriggerBasedTrigger {

    private final String name;
    private final Evaluator evaluator;

    private LogicTrigger(Triggers triggers, IPdxScript s, String name, Evaluator evaluator) {
        super(triggers, s);
        this.name = name;
        this.evaluator = evaluator;
    }

    public static TriggerFactory not() {
        return (triggers, s) -> new LogicTrigger(triggers, s, "not", Trigger::evaluateNand);
    }

    public static TriggerFactory or() {
        return (triggers, s) -> new LogicTrigger(triggers, s, "or", Trigger::evaluateOr);
    }

    public static TriggerFactory and() {
        return (triggers, s) -> new LogicTrigger(triggers, s, "and", Trigger::evaluateAnd);
    }

    public static TriggerFactory nor() {
        return (triggers, s) -> new LogicTrigger(triggers, s, "nor", Trigger::evaluateNor);
    }

    public static TriggerFactory nand() {
        return (triggers, s) -> new LogicTrigger(triggers, s, "nand", Trigger::evaluateNand);
    }

    @Override
    public boolean evaluate(Scope scope) {
        return evaluator.evaluate(scope, children);
    }

    @Override
    protected ImmutableList<String> localise(String language, int indent) {
        MutableList<String> list = Lists.mutable.of(name + ":");
        localiseChildren(list, language, indent + 1);
        return list.toImmutable();
    }
}
