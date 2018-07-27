package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Alliances {

    private final IntObjMap<Alliance> alliances;

    public Alliances(PdxScriptObject o) {
        this.alliances = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Alliance::new));
    }

    public Alliances(IntObjMap<Alliance> alliances) {
        this.alliances = HashIntObjMaps.newImmutableMap(alliances);
    }

    public IntObjMap<Alliance> getAlliances() {
        return alliances;
    }
}
