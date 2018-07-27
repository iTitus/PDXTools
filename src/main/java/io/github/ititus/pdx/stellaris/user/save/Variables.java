package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.ObjDoubleMap;
import com.koloboke.collect.map.hash.HashObjDoubleMaps;
import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.function.Function;

public class Variables {

    private final ObjDoubleMap<String> variables;

    public Variables(PdxScriptObject o) {
        this.variables = o.getAsObjDoubleMap(Function.identity(), PdxConstants.TO_DOUBLE);
    }

    public Variables(ObjDoubleMap<String> variables) {
        this.variables = HashObjDoubleMaps.newImmutableMap(variables);
    }

    public ObjDoubleMap<String> getVariables() {
        return variables;
    }
}
