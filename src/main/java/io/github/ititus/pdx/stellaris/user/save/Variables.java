package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;

import java.util.function.Function;

public class Variables {

    private final ImmutableObjectDoubleMap<String> variables;

    public Variables(PdxScriptObject o) {
        this.variables = o.getAsObjectDoubleMap(Function.identity(), PdxConstants.TO_DOUBLE);
    }

    public Variables(ImmutableObjectDoubleMap<String> variables) {
        this.variables = variables;
    }

    public ImmutableObjectDoubleMap<String> getVariables() {
        return variables;
    }
}
