package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class StarbaseBuildings {

    private final IntObjMap<String> starbaseBuildings;

    public StarbaseBuildings(PdxScriptObject o) {
        this.starbaseBuildings = o.getAsIntObjMap(Integer::parseInt, PdxConstants.NULL_OR_STRING);
    }

    public StarbaseBuildings(IntObjMap<String> starbaseBuildings) {
        this.starbaseBuildings = HashIntObjMaps.newImmutableMap(starbaseBuildings);
    }

    public IntObjMap<String> getStarbaseBuildings() {
        return starbaseBuildings;
    }
}
