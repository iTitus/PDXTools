package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class DebrisMap {

    private final ImmutableIntObjectMap<Debris> debrisMap;

    public DebrisMap(PdxScriptObject o) {
        this.debrisMap = o.getAsIntObjectMap(Debris::new);
    }

    public DebrisMap(ImmutableIntObjectMap<Debris> debrisMap) {
        this.debrisMap = debrisMap;
    }

    public ImmutableIntObjectMap<Debris> getDebrisMap() {
        return debrisMap;
    }
}
