package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class DebrisMap {

    private final IntObjMap<Debris> debrisMap;

    public DebrisMap(PdxScriptObject o) {
        this.debrisMap = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Debris::new));
    }

    public DebrisMap(IntObjMap<Debris> debrisMap) {
        this.debrisMap = HashIntObjMaps.newImmutableMap(debrisMap);
    }

    public IntObjMap<Debris> getDebrisMap() {
        return debrisMap;
    }
}
