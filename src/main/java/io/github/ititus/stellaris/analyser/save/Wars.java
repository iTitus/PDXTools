package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.HashMap;
import java.util.Map;

public class Wars {

    private final Map<Integer, War> wars;

    public Wars(PdxScriptObject o) {
        this.wars = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(War::new));
    }

    public Wars(Map<Integer, War> wars) {
        this.wars = new HashMap<>(wars);
    }
}
