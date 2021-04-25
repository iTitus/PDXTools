package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.function.Predicate;

public abstract class Trigger {

    protected final Triggers triggers;

    protected Trigger(Triggers triggers) {
        this.triggers = triggers;
    }

    public static boolean evaluateAnd(Scope scope, Trigger... triggers) {
        for (Trigger t : triggers) {
            if (!t.evaluate(scope)) {
                return false;
            }
        }

        return true;
    }

    public static boolean evaluateAnd(Scope scope, Iterable<Trigger> triggers) {
        for (Trigger t : triggers) {
            if (!t.evaluate(scope)) {
                return false;
            }
        }

        return true;
    }

    public static boolean evaluateOr(Scope scope, Trigger... triggers) {
        for (Trigger t : triggers) {
            if (t.evaluate(scope)) {
                return true;
            }
        }

        return false;
    }

    public static boolean evaluateOr(Scope scope, Iterable<Trigger> triggers) {
        for (Trigger t : triggers) {
            if (t.evaluate(scope)) {
                return true;
            }
        }

        return false;
    }

    protected ImmutableList<Trigger> create(IPdxScript s) {
        return triggers.create(s);
    }

    protected Trigger createOne(IPdxScript s) {
        return triggers.createOne(s);
    }

    protected ImmutableList<Trigger> create(IPdxScript s, Predicate<String> keyFilter) {
        return triggers.create(s, keyFilter);
    }

    protected Trigger createOne(IPdxScript s, Predicate<String> keyFilter) {
        return triggers.createOne(s, keyFilter);
    }

    protected ImmutableList<Trigger> create(PdxScriptObject o) {
        return triggers.create(o);
    }

    protected Trigger createOne(PdxScriptObject o) {
        return triggers.createOne(o);
    }

    protected ImmutableList<Trigger> create(PdxScriptObject o, Predicate<String> keyFilter) {
        return triggers.create(o, keyFilter);
    }

    public abstract boolean evaluate(Scope scope);

}
