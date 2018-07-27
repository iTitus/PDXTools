package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GroundCombats {

    private final IntObjMap<GroundCombat> groundCombats;

    public GroundCombats(PdxScriptObject o) {
        this.groundCombats = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(GroundCombat::new));
    }

    public GroundCombats(IntObjMap<GroundCombat> groundCombats) {
        this.groundCombats = HashIntObjMaps.newImmutableMap(groundCombats);
    }

    public IntObjMap<GroundCombat> getGroundCombats() {
        return groundCombats;
    }
}
