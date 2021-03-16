package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class StarbaseManager {

    public final ImmutableIntObjectMap<Starbase> starbases;

    public StarbaseManager(PdxScriptObject o) {
        this.starbases = o.getObjectAsIntObjectMap("starbases", Starbase::new);
    }
}
