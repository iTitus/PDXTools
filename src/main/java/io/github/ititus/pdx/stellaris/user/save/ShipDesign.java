package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class ShipDesign {

    private final boolean allianceShipDesign, autoGenDesign, isSpecialBuildable;
    private final String name, shipSize, allowBuildableTrigger;
    private final ImmutableList<String> requiredComponents;
    private final ImmutableList<ShipDesignSection> sections;

    public ShipDesign(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.shipSize = o.getString("ship_size");
        this.sections = o.getImplicitList("section").getAsList(ShipDesignSection::of);
        this.allianceShipDesign = o.getBoolean("alliance_ship_design");
        this.autoGenDesign = o.getBoolean("auto_gen_design");
        this.requiredComponents = o.getImplicitList("required_component").getAsStringList();
        this.isSpecialBuildable = o.getBoolean("is_special_buildable");
        this.allowBuildableTrigger = o.getString("allow_buildable_trigger");
    }

    public ShipDesign(boolean allianceShipDesign, boolean autoGenDesign, boolean isSpecialBuildable, String name, String shipSize, String allowBuildableTrigger, ImmutableList<String> requiredComponents, ImmutableList<ShipDesignSection> sections) {
        this.allianceShipDesign = allianceShipDesign;
        this.autoGenDesign = autoGenDesign;
        this.isSpecialBuildable = isSpecialBuildable;
        this.name = name;
        this.shipSize = shipSize;
        this.allowBuildableTrigger = allowBuildableTrigger;
        this.requiredComponents = requiredComponents;
        this.sections = sections;
    }

    public boolean isAllianceShipDesign() {
        return allianceShipDesign;
    }

    public boolean isAutoGenDesign() {
        return autoGenDesign;
    }

    public boolean isSpecialBuildable() {
        return isSpecialBuildable;
    }

    public String getName() {
        return name;
    }

    public String getShipSize() {
        return shipSize;
    }

    public String getAllowBuildableTrigger() {
        return allowBuildableTrigger;
    }

    public ImmutableList<String> getRequiredComponents() {
        return requiredComponents;
    }

    public ImmutableList<ShipDesignSection> getSections() {
        return sections;
    }
}
