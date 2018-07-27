package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StrikeCrafts {

    private final IntObjMap<StrikeCraft> strikeCrafts;

    public StrikeCrafts(PdxScriptObject o) {
        this.strikeCrafts = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(StrikeCraft::new));
    }

    public StrikeCrafts(IntObjMap<StrikeCraft> strikeCrafts) {
        this.strikeCrafts = HashIntObjMaps.newImmutableMap(strikeCrafts);
    }

    public IntObjMap<StrikeCraft> getStrikeCrafts() {
        return strikeCrafts;
    }
}
