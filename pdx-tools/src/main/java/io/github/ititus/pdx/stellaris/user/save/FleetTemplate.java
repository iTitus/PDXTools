package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class FleetTemplate {

    public final int fleet;
    public final HomeBase homeBase;
    public final ImmutableList<FleetTemplateDesign> fleetTemplateDesigns;
    public final int queued;
    public final int count;
    public final double fleetSize;

    public FleetTemplate(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.fleet = o.getInt("fleet");
        this.homeBase = o.getObjectAsNullOr("home_base", HomeBase::new);
        this.fleetTemplateDesigns = o.getListAsList("fleet_template_design", FleetTemplateDesign::new);
        this.queued = o.getInt("queued", 0);
        this.count = o.getInt("count");
        this.fleetSize = o.getDouble("fleet_size");
    }
}
