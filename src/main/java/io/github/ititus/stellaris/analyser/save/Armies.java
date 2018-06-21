package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Armies {

    private final Map<Integer, Army> armies;

    public Armies(PdxScriptObject o) {
        this.armies = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Army::new));
    }

    public Armies(Map<Integer, Army> armies) {
        this.armies = new HashMap<>(armies);
    }

    public Map<Integer, Army> getArmies() {
        return Collections.unmodifiableMap(armies);
    }
}
