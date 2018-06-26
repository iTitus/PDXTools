package io.github.ititus.pdx.stellaris.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Missiles {

    private final Map<Long, Missile> missiles;

    public Missiles(PdxScriptObject o) {
        this.missiles = o.getAsMap(Long::valueOf, PdxScriptObject.nullOr(Missile::new));
    }

    public Missiles(Map<Long, Missile> missiles) {
        this.missiles = new HashMap<>(missiles);
    }

    public Map<Long, Missile> getMissiles() {
        return Collections.unmodifiableMap(missiles);
    }
}
