package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.ObjByteMap;
import com.koloboke.collect.map.hash.HashObjByteMaps;
import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.function.Function;

public class Settings {

    private final ObjByteMap<String> settings;

    public Settings(PdxScriptObject o) {
        this.settings = o.getAsObjBooleanMap(Function.identity(), PdxConstants.TO_BOOLEAN);
    }

    public Settings(ObjByteMap<String> settings) {
        this.settings = HashObjByteMaps.newImmutableMap(settings);
    }

    public ObjByteMap<String> getSettings() {
        return settings;
    }
}
