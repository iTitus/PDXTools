package io.github.ititus.pdx.shared.effect;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.list.ImmutableList;

public abstract class Effect {

    protected final Effects effects;

    protected Effect(Effects effects) {
        this.effects = effects;
    }

    public abstract void execute(Scope scope);

    public ImmutableList<String> localise(PdxLocalisation localisation, String language) {
        return localise(localisation, language, 0);
    }

    protected abstract ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent);

}
