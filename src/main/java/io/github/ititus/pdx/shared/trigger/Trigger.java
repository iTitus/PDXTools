package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.util.function.Predicate;

import static io.github.ititus.pdx.pdxscript.PdxConstants.INDENT;

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

    public static ImmutableList<String> localise(PdxLocalisation localisation, String language, Iterable<Trigger> triggers) {
        return localise(localisation, language, 0, triggers);
    }

    public static ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent, Iterable<Trigger> triggers) {
        MutableList<String> list = Lists.mutable.empty();
        boolean empty = true;
        for (Trigger t : triggers) {
            empty = false;
            localiseSingle(list, t, localisation, language, indent);
        }

        if (empty) {
            list.add(indent(indent) + "<empty>");
        }

        return list.toImmutable();
    }

    public static ImmutableList<String> localise(PdxLocalisation localisation, String language, Trigger... triggers) {
        return localise(localisation, language, 0, triggers);
    }

    public static ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent, Trigger... triggers) {
        MutableList<String> list = Lists.mutable.empty();
        if (triggers.length == 0) {
            list.add(indent(indent) + "<empty>");
        } else {
            for (Trigger t : triggers) {
                localiseSingle(list, t, localisation, language, indent);
            }
        }

        return list.toImmutable();
    }

    private static void localiseSingle(MutableList<String> list, Trigger t, PdxLocalisation localisation, String language, int indent) {
        ImmutableList<String> localised = t.localise(localisation, language, indent + 1);
        list.add(indent(indent) + "- " + localised.get(0));
        for (int i = 1; i < localised.size(); i++) {
            list.add(localised.get(i));
        }
    }

    protected static String indent(int indent) {
        return INDENT.repeat(indent);
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

    public ImmutableList<String> localise(PdxLocalisation localisation, String language) {
        return localise(localisation, language, 0);
    }

    public abstract ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent);

}
