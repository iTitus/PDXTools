package io.github.ititus.pdx.stellaris.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Truces {

    private final Map<Integer, Truce> truces;

    public Truces(PdxScriptObject o) {
        this.truces = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Truce::new));
    }

    public Truces(Map<Integer, Truce> truces) {
        this.truces = new HashMap<>(truces);
    }

    public Map<Integer, Truce> getTruces() {
        return Collections.unmodifiableMap(truces);
    }
}
