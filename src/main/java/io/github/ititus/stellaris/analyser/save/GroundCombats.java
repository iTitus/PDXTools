package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

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
