package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Alliances {

    private final Map<Integer, Alliance> alliances;

    public Alliances(PdxScriptObject o) {
        this.alliances = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Alliance::new));
    }

    public Alliances(Map<Integer, Alliance> alliances) {
        this.alliances = new HashMap<>(alliances);
    }

    public Map<Integer, Alliance> getAlliances() {
        return Collections.unmodifiableMap(alliances);
    }
}
