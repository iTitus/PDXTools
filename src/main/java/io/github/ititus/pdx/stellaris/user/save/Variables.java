package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public class Variables {

    private final Map<String, Double> variables;

    public Variables(PdxScriptObject o) {
        this.variables = o.getAsMap(Function.identity(), PdxScriptObject.nullOrDouble());
    }

    public Variables(Map<String, Double> variables) {
        this.variables = Collections.unmodifiableMap(variables);
    }

    public Map<String, Double> getVariables() {
        return Collections.unmodifiableMap(variables);
    }
}
