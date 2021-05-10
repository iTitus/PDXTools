package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetTemplateDesign {

    public final int design;
    public final int count;

    public FleetTemplateDesign(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.design = o.getInt("design");
        this.count = o.getInt("count", 1);
    }
}
