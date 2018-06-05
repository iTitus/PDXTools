package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

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
}
