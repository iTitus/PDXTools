package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
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

    public Map<Integer, War> getWars() {
        return Collections.unmodifiableMap(wars);
    }
}
