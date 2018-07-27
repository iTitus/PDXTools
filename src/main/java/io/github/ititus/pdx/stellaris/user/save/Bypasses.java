package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Bypasses {

    private final IntObjMap<Bypass> bypasses;

    public Bypasses(PdxScriptObject o) {
        this.bypasses = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Bypass::new));
    }

    public Bypasses(IntObjMap<Bypass> bypasses) {
        this.bypasses = HashIntObjMaps.newImmutableMap(bypasses);
    }

    public IntObjMap<Bypass> getBypasses() {
        return bypasses;
    }
}
