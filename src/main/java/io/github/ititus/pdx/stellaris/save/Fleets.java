package io.github.ititus.pdx.stellaris.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
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

    public Map<Integer, Fleet> getFleets() {
        return Collections.unmodifiableMap(fleets);
    }
}
