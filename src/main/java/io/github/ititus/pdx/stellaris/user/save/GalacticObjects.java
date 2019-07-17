package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class GalacticObjects {

    private final ImmutableIntObjectMap<GalacticObject> galacticObjects;

    public GalacticObjects(PdxScriptObject o) {
        this.galacticObjects = o.getAsIntObjectMap(GalacticObject::new);
    }

    public GalacticObjects(ImmutableIntObjectMap<GalacticObject> galacticObjects) {
        this.galacticObjects = galacticObjects;
    }

    public ImmutableIntObjectMap<GalacticObject> getGalacticObjects() {
        return galacticObjects;
    }
}
