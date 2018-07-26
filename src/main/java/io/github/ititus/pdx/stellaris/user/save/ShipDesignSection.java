package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableList;
import io.github.ititus.pdx.util.collection.ViewableUnmodifiableArrayList;

import java.util.Collection;
import java.util.List;

public class ShipDesignSection {

    private final String template, slot;
    private final ViewableList<ShipDesignComponent> components;

    public ShipDesignSection(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException();
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.template = o.getString("template");
        this.slot = o.getString("slot");
        this.components = o.getImplicitList("component").getAsList(ShipDesignComponent::new);
    }

    public ShipDesignSection(String template, String slot, Collection<ShipDesignComponent> components) {
        this.template = template;
        this.slot = slot;
        this.components = new ViewableUnmodifiableArrayList<>(components);
    }

    public String getTemplate() {
        return template;
    }

    public String getSlot() {
        return slot;
    }

    public List<ShipDesignComponent> getComponents() {
        return components.getView();
    }
}
