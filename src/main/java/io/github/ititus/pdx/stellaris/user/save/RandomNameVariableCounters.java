package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class RandomNameVariableCounters {

    public final PreCommunicationsName preCommunicationsName;

    public RandomNameVariableCounters(PdxScriptObject o) {
        this.preCommunicationsName = o.getObjectAs("pre_communications_name", PreCommunicationsName::new);
    }
}
