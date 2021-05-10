package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class RandomNameVariables {

    public final RandomNameVariableCounters counters;

    public RandomNameVariables(PdxScriptObject o) {
        this.counters = o.getObjectAs("counters", RandomNameVariableCounters::new);
    }
}
