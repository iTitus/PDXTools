package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class RandomNameVariables {

    private final RandomNameVariableCounters counters;

    public RandomNameVariables(PdxScriptObject o) {
        this.counters = o.getObject("counters").getAs(RandomNameVariableCounters::new);
    }

    public RandomNameVariables(RandomNameVariableCounters counters) {
        this.counters = counters;
    }

    public RandomNameVariableCounters getCounters() {
        return counters;
    }
}
