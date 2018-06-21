package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Leaders {

    private final Map<Integer, Leader> leaders;

    public Leaders(PdxScriptObject o) {
        this.leaders = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Leader::new));
    }

    public Leaders(Map<Integer, Leader> leaders) {
        this.leaders = new HashMap<>(leaders);
    }

    public Map<Integer, Leader> getLeaders() {
        return Collections.unmodifiableMap(leaders);
    }
}
