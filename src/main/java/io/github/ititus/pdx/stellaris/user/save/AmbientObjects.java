package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class AmbientObjects {

    private final IntObjMap<AmbientObject> ambientObjects;

    public AmbientObjects(PdxScriptObject o) {
        this.ambientObjects = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(AmbientObject::new));
    }

    public AmbientObjects(IntObjMap<AmbientObject> ambientObjects) {
        this.ambientObjects = HashIntObjMaps.newImmutableMap(ambientObjects);
    }

    public IntObjMap<AmbientObject> getAmbientObjects() {
        return ambientObjects;
    }
}
