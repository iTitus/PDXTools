package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Wars {

    private final ImmutableIntObjectMap<War> wars;

    public Wars(PdxScriptObject o) {
        this.wars = o.getAsIntObjectMap(War::new);
    }

    public Wars(ImmutableIntObjectMap<War> wars) {
        this.wars = wars;
    }

    public ImmutableIntObjectMap<War> getWars() {
        return wars;
    }
}
