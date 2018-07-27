package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.ObjIntMap;
import com.koloboke.collect.map.ObjObjMap;
import com.koloboke.collect.map.hash.HashObjIntMaps;
import com.koloboke.collect.map.hash.HashObjObjMaps;
import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Map;
import java.util.function.Function;

public class Flags {

    private final ObjIntMap<String> intFlags;
    private final ObjObjMap<String, FlagData> complexFlags;

    public Flags(PdxScriptObject o) {
        this.intFlags = o.getAsObjIntMap(Function.identity(), PdxConstants.TO_INT);
        this.complexFlags = o.getAsMap(Function.identity(), PdxScriptObject.objectOrNull(FlagData::new));
    }

    public Flags(ObjIntMap<String> intFlags, Map<String, FlagData> complexFlags) {
        this.intFlags = HashObjIntMaps.newImmutableMap(intFlags);
        this.complexFlags = HashObjObjMaps.newImmutableMap(complexFlags);
    }

    public ObjIntMap<String> getIntFlags() {
        return intFlags;
    }

    public ObjObjMap<String, FlagData> getComplexFlags() {
        return complexFlags;
    }
}
