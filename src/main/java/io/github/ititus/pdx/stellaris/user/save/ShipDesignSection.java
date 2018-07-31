package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class ShipDesignSection {

    private final String template, slot;
    private final ImmutableList<ShipDesignComponent> components;

    public ShipDesignSection(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException();
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.template = o.getString("template");
        this.slot = o.getString("slot");
        this.components = o.getImplicitList("component").getAsList(ShipDesignComponent::new);
    }

    public ShipDesignSection(String template, String slot, ImmutableList<ShipDesignComponent> components) {
        this.template = template;
        this.slot = slot;
        this.components = components;
    }

    public String getTemplate() {
        return template;
    }

    public String getSlot() {
        return slot;
    }

    public ImmutableList<ShipDesignComponent> getComponents() {
        return components;
    }
}
