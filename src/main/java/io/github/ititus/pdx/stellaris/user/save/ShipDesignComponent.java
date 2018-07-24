package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class ShipDesignComponent {

    private final String slot, template;

    public ShipDesignComponent(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException();
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.slot = o.getString("slot");
        this.template = o.getString("template");
    }

    public ShipDesignComponent(String slot, String template) {
        this.slot = slot;
        this.template = template;
    }

    public String getSlot() {
        return slot;
    }

    public String getTemplate() {
        return template;
    }
}
