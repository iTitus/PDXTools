package io.github.ititus.pdx.shared.effect;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.shared.localisation.Localisable;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import static io.github.ititus.pdx.pdxscript.PdxConstants.DEFAULT_LANGUAGE;
import static io.github.ititus.pdx.pdxscript.PdxConstants.INDENT;

public abstract class Effect implements Localisable {

    protected final Effects effects;
    protected final PdxLocalisation localisation;

    protected Effect(Effects effects) {
        this.effects = effects;
        this.localisation = effects.getGame().localisation;
    }

    public static ImmutableList<String> localise(Iterable<Effect> effects) {
        return localise(DEFAULT_LANGUAGE, 0, effects);
    }

    public static ImmutableList<String> localise(String language, Iterable<Effect> effects) {
        return localise(language, 0, effects);
    }

    protected static ImmutableList<String> localise(String language, int indent, Iterable<Effect> effects) {
        MutableList<String> list = Lists.mutable.empty();
        boolean empty = true;
        for (Effect t : effects) {
            empty = false;
            localiseSingle(list, t, language, indent);
        }

        if (empty) {
            list.add(indent(indent) + "<empty>");
        }

        return list.toImmutable();
    }

    public static ImmutableList<String> localise(Effect... effects) {
        return localise(DEFAULT_LANGUAGE, 0, effects);
    }

    public static ImmutableList<String> localise(String language, Effect... effects) {
        return localise(language, 0, effects);
    }

    protected static ImmutableList<String> localise(String language, int indent, Effect... effects) {
        MutableList<String> list = Lists.mutable.empty();
        if (effects.length == 0) {
            list.add(indent(indent) + "<empty>");
        } else {
            for (Effect t : effects) {
                localiseSingle(list, t, language, indent);
            }
        }

        return list.toImmutable();
    }

    private static void localiseSingle(MutableList<String> list, Effect t, String language, int indent) {
        ImmutableList<String> localised = t.localise(language, indent);
        list.add(indent(indent) + " - " + localised.get(0));
        for (int i = 1; i < localised.size(); i++) {
            list.add(localised.get(i));
        }
    }

    protected static String indent(int indent) {
        return INDENT.repeat(indent);
    }

    public abstract void execute(Scope scope);

    @Override
    public ImmutableList<String> localise(String language) {
        return localise(language, 0);
    }

    protected abstract ImmutableList<String> localise(String language, int indent);

}
