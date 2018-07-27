package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GalacticObjects {

    private final IntObjMap<GalacticObject> galacticObjects;

    public GalacticObjects(PdxScriptObject o) {
        this.galacticObjects = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(GalacticObject::new));
    }

    public GalacticObjects(IntObjMap<GalacticObject> galacticObjects) {
        this.galacticObjects = HashIntObjMaps.newImmutableMap(galacticObjects);
    }

    public IntObjMap<GalacticObject> getGalacticObjects() {
        return galacticObjects;
    }
}
