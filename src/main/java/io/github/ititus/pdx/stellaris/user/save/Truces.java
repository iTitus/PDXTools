package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Truces {

    private final IntObjMap<Truce> truces;

    public Truces(PdxScriptObject o) {
        this.truces = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Truce::new));
    }

    public Truces(IntObjMap<Truce> truces) {
        this.truces = HashIntObjMaps.newImmutableMap(truces);
    }

    public IntObjMap<Truce> getTruces() {
        return truces;
    }
}
