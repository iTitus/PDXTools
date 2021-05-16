package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class ShipDesign {

    public final int id;
    public final String name;
    public final String shipSize;
    public final ImmutableList<ShipDesignSection> sections;
    public final boolean allianceShipDesign;
    public final boolean autoGenDesign;
    public final ImmutableList<String> requiredComponents;
    public final boolean isSpecialBuildable;
    public final String allowBuildableTrigger;

    public ShipDesign(int id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name", null);
        this.shipSize = o.getString("ship_size");
        this.sections = o.getImplicitListAsList("section", ShipDesignSection::new);
        this.allianceShipDesign = o.getBoolean("alliance_ship_design", false);
        this.autoGenDesign = o.getBoolean("auto_gen_design", false);
        this.requiredComponents = o.getImplicitListAsStringList("required_component");
        this.isSpecialBuildable = o.getBoolean("is_special_buildable", false);
        this.allowBuildableTrigger = o.getString("allow_buildable_trigger", null);
    }
}
