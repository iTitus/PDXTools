package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Fleets {

    private final ImmutableIntObjectMap<Fleet> fleets;

    public Fleets(PdxScriptObject o) {
        this.fleets = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(Fleet::new));
    }

    public Fleets(ImmutableIntObjectMap<Fleet> fleets) {
        this.fleets = fleets;
    }

    public ImmutableIntObjectMap<Fleet> getFleets() {
        return fleets;
    }
}
