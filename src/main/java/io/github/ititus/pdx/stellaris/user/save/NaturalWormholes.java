package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class NaturalWormholes {

    private final ImmutableIntObjectMap<NaturalWormhole> naturalWormholes;

    public NaturalWormholes(PdxScriptObject o) {
        this.naturalWormholes = o.getAsIntObjectMap(NaturalWormhole::new);
    }

    public NaturalWormholes(ImmutableIntObjectMap<NaturalWormhole> naturalWormholes) {
        this.naturalWormholes = naturalWormholes;
    }

    public ImmutableIntObjectMap<NaturalWormhole> getNaturalWormholes() {
        return naturalWormholes;
    }
}
