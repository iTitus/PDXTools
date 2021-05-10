package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class ShipDesignSection {

    public final String template;
    public final String slot;
    public final ImmutableList<ShipDesignComponent> components;

    public ShipDesignSection(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.template = o.getString("template");
        this.slot = o.getString("slot");
        this.components = o.getImplicitListAsList("component", ShipDesignComponent::new);
    }
}
