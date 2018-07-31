package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Starbases {

    private final ImmutableIntObjectMap<Starbase> starbases;

    public Starbases(PdxScriptObject o) {
        this.starbases = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(Starbase::new));
    }

    public Starbases(ImmutableIntObjectMap<Starbase> starbases) {
        this.starbases = starbases;
    }

    public ImmutableIntObjectMap<Starbase> getStarbases() {
        return starbases;
    }
}
