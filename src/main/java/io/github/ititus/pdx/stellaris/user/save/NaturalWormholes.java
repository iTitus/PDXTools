package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NaturalWormholes {

    private final Map<Integer, NaturalWormhole> naturalWormholes;

    public NaturalWormholes(PdxScriptObject o) {
        this.naturalWormholes = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(NaturalWormhole::new));
    }

    public NaturalWormholes(Map<Integer, NaturalWormhole> naturalWormholes) {
        this.naturalWormholes = new HashMap<>(naturalWormholes);
    }

    public Map<Integer, NaturalWormhole> getNaturalWormholes() {
        return Collections.unmodifiableMap(naturalWormholes);
    }
}
