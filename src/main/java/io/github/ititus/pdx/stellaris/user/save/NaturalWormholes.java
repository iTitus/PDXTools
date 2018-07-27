package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class NaturalWormholes {

    private final IntObjMap<NaturalWormhole> naturalWormholes;

    public NaturalWormholes(PdxScriptObject o) {
        this.naturalWormholes = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(NaturalWormhole::new));
    }

    public NaturalWormholes(IntObjMap<NaturalWormhole> naturalWormholes) {
        this.naturalWormholes = HashIntObjMaps.newImmutableMap(naturalWormholes);
    }

    public IntObjMap<NaturalWormhole> getNaturalWormholes() {
        return naturalWormholes;
    }
}
