package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class AmbientObjects {

    private final ImmutableIntObjectMap<AmbientObject> ambientObjects;

    public AmbientObjects(PdxScriptObject o) {
        this.ambientObjects = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(AmbientObject::new));
    }

    public AmbientObjects(ImmutableIntObjectMap<AmbientObject> ambientObjects) {
        this.ambientObjects = ambientObjects;
    }

    public ImmutableIntObjectMap<AmbientObject> getAmbientObjects() {
        return ambientObjects;
    }
}
