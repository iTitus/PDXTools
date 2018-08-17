package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class RandomNameVariableCounters {

    private final PreCommunicationsName preCommunicationsName;

    public RandomNameVariableCounters(PdxScriptObject o) {
        this.preCommunicationsName = o.getObject("pre_communications_name").getAs(PreCommunicationsName::new);
    }
}
