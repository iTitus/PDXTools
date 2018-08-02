package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;

import java.util.Objects;

public class ShipDesignComponent {

    private static final Deduplicator<ShipDesignComponent> DEDUPLICATOR = new Deduplicator<>();

    private final String slot, template;

    private ShipDesignComponent(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException();
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.slot = o.getString("slot");
        this.template = o.getString("template");
    }

    private ShipDesignComponent(String slot, String template) {
        this.slot = slot;
        this.template = template;
    }

    public static ShipDesignComponent of(IPdxScript s) {
        return DEDUPLICATOR.deduplicate(new ShipDesignComponent(s));
    }

    public static ShipDesignComponent of(String slot, String template) {
        return DEDUPLICATOR.deduplicate(new ShipDesignComponent(slot, template));
    }

    public String getSlot() {
        return slot;
    }

    public String getTemplate() {
        return template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipDesignComponent)) {
            return false;
        }
        ShipDesignComponent that = (ShipDesignComponent) o;
        return Objects.equals(slot, that.slot) && Objects.equals(template, that.template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slot, template);
    }
}
