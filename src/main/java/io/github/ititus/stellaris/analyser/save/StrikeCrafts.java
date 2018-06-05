package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

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
}
