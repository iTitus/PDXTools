package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Pops {

    private final IntObjMap<Pop> pops;

    public Pops(PdxScriptObject o) {
        this.pops = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Pop::new));
    }

    public Pops(IntObjMap<Pop> pops) {
        this.pops = HashIntObjMaps.newImmutableMap(pops);
    }

    public IntObjMap<Pop> getPops() {
        return pops;
    }
}
