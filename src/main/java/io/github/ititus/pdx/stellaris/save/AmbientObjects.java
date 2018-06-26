package io.github.ititus.pdx.stellaris.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AmbientObjects {

    private final Map<Integer, AmbientObject> ambientObjects;

    public AmbientObjects(PdxScriptObject o) {
        this.ambientObjects = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(AmbientObject::new));
    }

    public AmbientObjects(Map<Integer, AmbientObject> ambientObjects) {
        this.ambientObjects = new HashMap<>(ambientObjects);
    }

    public Map<Integer, AmbientObject> getAmbientObjects() {
        return Collections.unmodifiableMap(ambientObjects);
    }
}
