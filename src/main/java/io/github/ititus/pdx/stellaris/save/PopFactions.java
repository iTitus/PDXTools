package io.github.ititus.pdx.stellaris.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PopFactions {

    private final Map<Integer, PopFaction> popFactions;

    public PopFactions(PdxScriptObject o) {
        this.popFactions = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(PopFaction::new));
    }

    public PopFactions(Map<Integer, PopFaction> popFactions) {
        this.popFactions = new HashMap<>(popFactions);
    }

    public Map<Integer, PopFaction> getPopFactions() {
        return Collections.unmodifiableMap(popFactions);
    }
}
