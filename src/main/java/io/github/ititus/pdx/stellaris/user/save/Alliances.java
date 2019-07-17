package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Alliances {

    private final ImmutableIntObjectMap<Alliance> alliances;

    public Alliances(PdxScriptObject o) {
        this.alliances = o.getAsIntObjectMap(Alliance::new);
    }

    public Alliances(ImmutableIntObjectMap<Alliance> alliances) {
        this.alliances = alliances;
    }

    public ImmutableIntObjectMap<Alliance> getAlliances() {
        return alliances;
    }
}
