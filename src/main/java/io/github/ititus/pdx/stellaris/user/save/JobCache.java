package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class JobCache {

    public final int numEmployed;
    public final int maxEmployed;
    public final int maxWithoutPrio;

    public JobCache(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.numEmployed = o.getInt("num_employed");
        this.maxEmployed = o.getInt("max_employed");
        this.maxWithoutPrio = o.getInt("max_without_prio");
    }
}
