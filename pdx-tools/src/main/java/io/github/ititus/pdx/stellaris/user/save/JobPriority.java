package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class JobPriority {

    public final String job;
    public final int priority;

    public JobPriority(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.job = o.getString("job");
        this.priority = o.getInt("priority");
    }
}
