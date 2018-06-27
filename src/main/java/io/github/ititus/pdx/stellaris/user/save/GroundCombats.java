package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GroundCombats {

    private final Map<Integer, GroundCombat> groundCombats;

    public GroundCombats(PdxScriptObject o) {
        this.groundCombats = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(GroundCombat::new));
    }

    public GroundCombats(Map<Integer, GroundCombat> groundCombats) {
        this.groundCombats = new HashMap<>(groundCombats);
    }

    public Map<Integer, GroundCombat> getGroundCombats() {
        return Collections.unmodifiableMap(groundCombats);
    }
}
