package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Starbases {

    private final Map<Integer, Starbase> starbases;

    public Starbases(PdxScriptObject o) {
        this.starbases = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Starbase::new));
    }

    public Starbases(Map<Integer, Starbase> starbases) {
        this.starbases = new HashMap<>(starbases);
    }

    public Map<Integer, Starbase> getStarbases() {
        return Collections.unmodifiableMap(starbases);
    }
}
