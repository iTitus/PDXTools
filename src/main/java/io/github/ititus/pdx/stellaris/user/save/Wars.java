package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Wars {

    private final IntObjMap<War> wars;

    public Wars(PdxScriptObject o) {
        this.wars = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(War::new));
    }

    public Wars(IntObjMap<War> wars) {
        this.wars = HashIntObjMaps.newImmutableMap(wars);
    }

    public IntObjMap<War> getWars() {
        return wars;
    }
}
