package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Fleets {

    private final IntObjMap<Fleet> fleets;

    public Fleets(PdxScriptObject o) {
        this.fleets = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Fleet::new));
    }

    public Fleets(IntObjMap<Fleet> fleets) {
        this.fleets = HashIntObjMaps.newImmutableMap(fleets);
    }

    public IntObjMap<Fleet> getFleets() {
        return fleets;
    }
}
