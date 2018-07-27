package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Starbases {

    private final IntObjMap<Starbase> starbases;

    public Starbases(PdxScriptObject o) {
        this.starbases = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Starbase::new));
    }

    public Starbases(IntObjMap<Starbase> starbases) {
        this.starbases = HashIntObjMaps.newImmutableMap(starbases);
    }

    public IntObjMap<Starbase> getStarbases() {
        return starbases;
    }
}
