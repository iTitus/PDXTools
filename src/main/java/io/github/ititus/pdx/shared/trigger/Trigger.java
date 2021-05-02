package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.localisation.Localisable;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.util.function.Predicate;

import static io.github.ititus.pdx.pdxscript.PdxConstants.DEFAULT_LANGUAGE;
import static io.github.ititus.pdx.pdxscript.PdxConstants.INDENT;

public abstract class Trigger implements Localisable {

    protected final Triggers triggers;
    protected final PdxLocalisation localisation;

    protected Trigger(Triggers triggers) {
        this.triggers = triggers;
        this.localisation = triggers.getGame().localisation;
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

    public static boolean evaluateNand(Scope scope, Trigger... triggers) {
        return !evaluateAnd(scope, triggers);
    }

    public static boolean evaluateNand(Scope scope, Iterable<Trigger> triggers) {
        return !evaluateAnd(scope, triggers);
    }

    public static boolean evaluateNor(Scope scope, Trigger... triggers) {
        return !evaluateOr(scope, triggers);
    }

    public static boolean evaluateNor(Scope scope, Iterable<Trigger> triggers) {
        return !evaluateOr(scope, triggers);
    }

    public static ImmutableList<String> localise(Iterable<Trigger> triggers) {
        return localise(DEFAULT_LANGUAGE, 0, triggers);
    }

    public static ImmutableList<String> localise(String language, Iterable<Trigger> triggers) {
        return localise(language, 0, triggers);
    }

    public static ImmutableList<String> localise(String language, int indent, Iterable<Trigger> triggers) {
        MutableList<String> list = Lists.mutable.empty();
        boolean empty = true;
        for (Trigger t : triggers) {
            empty = false;
            localiseSingle(list, t, language, indent);
        }

        if (empty) {
            list.add(indent(indent) + " <empty>");
        }

        return list.toImmutable();
    }

    public static ImmutableList<String> localise(Trigger... triggers) {
        return localise(DEFAULT_LANGUAGE, 0, triggers);
    }

    public static ImmutableList<String> localise(String language, Trigger... triggers) {
        return localise(language, 0, triggers);
    }

    public static ImmutableList<String> localise(String language, int indent, Trigger... triggers) {
        MutableList<String> list = Lists.mutable.empty();
        if (triggers.length == 0) {
            list.add(indent(indent) + "<empty>");
        } else {
            for (Trigger t : triggers) {
                localiseSingle(list, t, language, indent);
            }
        }

        return list.toImmutable();
    }

    private static void localiseSingle(MutableList<String> list, Trigger t, String language, int indent) {
        ImmutableList<String> localised = t.localise(language, indent);
        list.add(indent(indent) + " - " + localised.get(0));
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

    @Override
    public ImmutableList<String> localise(String language) {
        return localise(language, 0);
    }

    protected abstract ImmutableList<String> localise(String language, int indent);

}
