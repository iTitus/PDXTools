package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class GroundCombats {

    private final ImmutableIntObjectMap<GroundCombat> groundCombats;

    public GroundCombats(PdxScriptObject o) {
        this.groundCombats = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(GroundCombat::new));
    }

    public GroundCombats(ImmutableIntObjectMap<GroundCombat> groundCombats) {
        this.groundCombats = groundCombats;
    }

    public ImmutableIntObjectMap<GroundCombat> getGroundCombats() {
        return groundCombats;
    }
}
