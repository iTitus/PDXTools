package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

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
}
