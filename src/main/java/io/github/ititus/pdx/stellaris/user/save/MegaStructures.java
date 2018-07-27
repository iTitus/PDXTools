package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class MegaStructures {

    private final IntObjMap<MegaStructure> megaStructures;

    public MegaStructures(PdxScriptObject o) {
        this.megaStructures = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(MegaStructure::new));
    }

    public MegaStructures(IntObjMap<MegaStructure> megaStructures) {
        this.megaStructures = HashIntObjMaps.newImmutableMap(megaStructures);
    }

    public IntObjMap<MegaStructure> getMegaStructures() {
        return megaStructures;
    }
}
