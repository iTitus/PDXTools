package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableLongObjectMap;

public class Missiles {

    private final ImmutableLongObjectMap<Missile> missiles;

    public Missiles(PdxScriptObject o) {
        this.missiles = o.getAsLongObjectMap(Missile::new);
    }

    public Missiles(ImmutableLongObjectMap<Missile> missiles) {
        this.missiles = missiles;
    }

    public ImmutableLongObjectMap<Missile> getMissiles() {
        return missiles;
    }
}
