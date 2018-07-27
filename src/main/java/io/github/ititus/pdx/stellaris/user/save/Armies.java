package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Armies {

    private final IntObjMap<Army> armies;

    public Armies(PdxScriptObject o) {
        this.armies = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Army::new));
    }

    public Armies(IntObjMap<Army> armies) {
        this.armies = HashIntObjMaps.newImmutableMap(armies);
    }

    public IntObjMap<Army> getArmies() {
        return armies;
    }
}
