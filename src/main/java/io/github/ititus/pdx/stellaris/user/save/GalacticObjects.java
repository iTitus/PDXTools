package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GalacticObjects {

    private final Map<Integer, GalacticObject> galacticObjects;

    public GalacticObjects(PdxScriptObject o) {
        this.galacticObjects = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(GalacticObject::new));
    }

    public GalacticObjects(Map<Integer, GalacticObject> galacticObjects) {
        this.galacticObjects = new HashMap<>(galacticObjects);
    }

    public Map<Integer, GalacticObject> getGalacticObjects() {
        return Collections.unmodifiableMap(galacticObjects);
    }
}
