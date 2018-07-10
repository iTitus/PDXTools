package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ShipDesign {

    private final boolean allianceShipDesign, autoGenDesigns, isSpecialBuildable, useDesignName;
    private final String name, shipSize, allowBuildableTrigger;
    private final List<String> requiredComponents;
    private final List<ShipSection> sections;

    public ShipDesign(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.shipSize = o.getString("ship_size");
        this.sections = o.getImplicitList("section").getAsList(ShipSection::new);
        this.allianceShipDesign = o.getBoolean("alliance_ship_design");
        this.autoGenDesigns = o.getBoolean("auto_gen_design");
        this.requiredComponents = o.getImplicitList("required_component").getAsStringList();
        this.isSpecialBuildable = o.getBoolean("is_special_buildable");
        this.allowBuildableTrigger = o.getString("allow_buildable_trigger");
        this.useDesignName = o.getBoolean("use_design_name");
    }

    public ShipDesign(boolean allianceShipDesign, boolean autoGenDesigns, boolean isSpecialBuildable, boolean useDesignName, String name, String shipSize, String allowBuildableTrigger, Collection<String> requiredComponents, Collection<ShipSection> sections) {
        this.allianceShipDesign = allianceShipDesign;
        this.autoGenDesigns = autoGenDesigns;
        this.isSpecialBuildable = isSpecialBuildable;
        this.useDesignName = useDesignName;
        this.name = name;
        this.shipSize = shipSize;
        this.allowBuildableTrigger = allowBuildableTrigger;
        this.requiredComponents = new ArrayList<>(requiredComponents);
        this.sections = new ArrayList<>(sections);
    }

    public boolean isAllianceShipDesign() {
        return allianceShipDesign;
    }

    public boolean isAutoGenDesigns() {
        return autoGenDesigns;
    }

    public boolean isSpecialBuildable() {
        return isSpecialBuildable;
    }

    public boolean isUseDesignName() {
        return useDesignName;
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

    public List<String> getRequiredComponents() {
        return Collections.unmodifiableList(requiredComponents);
    }

    public List<ShipSection> getSections() {
        return Collections.unmodifiableList(sections);
    }
}
