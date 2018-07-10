package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetTemplateDesign {

    private final int design, count;

    public FleetTemplateDesign(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.design = o.getInt("design");
        this.count = o.getInt("count");
    }

    public FleetTemplateDesign(int design, int count) {
        this.design = design;
        this.count = count;
    }

    public int getDesign() {
        return design;
    }

    public int getCount() {
        return count;
    }
}
