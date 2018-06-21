package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Pops {

    private final Map<Integer, Pop> pops;

    public Pops(PdxScriptObject o) {
        this.pops = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Pop::new));
    }

    public Pops(Map<Integer, Pop> pops) {
        this.pops = new HashMap<>(pops);
    }

    public Map<Integer, Pop> getPops() {
        return Collections.unmodifiableMap(pops);
    }
}
