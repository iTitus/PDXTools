package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class ShipDesignComponent {

    public final String slot;
    public final String template;

    public ShipDesignComponent(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.slot = o.getString("slot");
        this.template = o.getString("template");
    }
}
