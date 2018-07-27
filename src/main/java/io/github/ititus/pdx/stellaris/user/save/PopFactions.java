package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class PopFactions {

    private final IntObjMap<PopFaction> popFactions;

    public PopFactions(PdxScriptObject o) {
        this.popFactions = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(PopFaction::new));
    }

    public PopFactions(IntObjMap<PopFaction> popFactions) {
        this.popFactions = HashIntObjMaps.newImmutableMap(popFactions);
    }

    public IntObjMap<PopFaction> getPopFactions() {
        return popFactions;
    }
}
