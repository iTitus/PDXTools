package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Leaders {

    private final IntObjMap<Leader> leaders;

    public Leaders(PdxScriptObject o) {
        this.leaders = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Leader::new));
    }

    public Leaders(IntObjMap<Leader> leaders) {
        this.leaders = HashIntObjMaps.newImmutableMap(leaders);
    }

    public IntObjMap<Leader> getLeaders() {
        return leaders;
    }
}
