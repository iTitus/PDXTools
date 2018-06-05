package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.HashMap;
import java.util.Map;

public class Fleets {

    private final Map<Integer, Fleet> fleets;

    public Fleets(PdxScriptObject o) {
        this.fleets = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Fleet::new));
    }

    public Fleets(Map<Integer, Fleet> fleets) {
        this.fleets = new HashMap<>(fleets);
    }
}
