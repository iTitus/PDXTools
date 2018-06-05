package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

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
}
