package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Objects;

public class ShipDesignSection {

    private static final Deduplicator<ShipDesignSection> DEDUPLICATOR = new Deduplicator<>();

    private final String template, slot;
    private final ImmutableList<ShipDesignComponent> components;

    private ShipDesignSection(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.template = o.getString("template");
        this.slot = o.getString("slot");
        this.components = o.getImplicitList("component").getAsList(ShipDesignComponent::of);
    }

    private ShipDesignSection(String template, String slot, ImmutableList<ShipDesignComponent> components) {
        this.template = template;
        this.slot = slot;
        this.components = components;
    }

    public static ShipDesignSection of(IPdxScript s) {
        return DEDUPLICATOR.deduplicate(new ShipDesignSection(s));
    }

    public static ShipDesignSection of(String template, String slot, ImmutableList<ShipDesignComponent> components) {
        return DEDUPLICATOR.deduplicate(new ShipDesignSection(template, slot, components));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipDesignSection)) {
            return false;
        }
        ShipDesignSection that = (ShipDesignSection) o;
        return Objects.equals(template, that.template) && Objects.equals(slot, that.slot) && Objects.equals(components, that.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(template, slot, components);
    }
}
