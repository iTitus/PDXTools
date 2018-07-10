package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class ShipComponent {

    private final String slot, template;

    public ShipComponent(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException();
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.slot = o.getString("slot");
        this.template = o.getString("template");
    }

    public ShipComponent(String slot, String template) {
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
