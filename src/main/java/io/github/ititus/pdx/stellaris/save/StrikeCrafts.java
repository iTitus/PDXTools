package io.github.ititus.pdx.stellaris.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StrikeCrafts {

    private final Map<Integer, StrikeCraft> strikeCrafts;

    public StrikeCrafts(PdxScriptObject o) {
        this.strikeCrafts = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(StrikeCraft::new));
    }

    public StrikeCrafts(Map<Integer, StrikeCraft> strikeCrafts) {
        this.strikeCrafts = new HashMap<>(strikeCrafts);
    }

    public Map<Integer, StrikeCraft> getStrikeCrafts() {
        return Collections.unmodifiableMap(strikeCrafts);
    }
}
