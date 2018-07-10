package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FleetTemplate {

    private final int fleet, queued, count;
    private final double fleetSize;
    private final List<FleetTemplateDesign> fleetTemplateDesigns;
    private final Location homeBase;

    public FleetTemplate(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.fleet = o.getInt("fleet");
        PdxScriptObject o1 = o.getObject("home_base");
        this.homeBase = o1 != null ? o1.getAs(Location::new) : null;
        this.fleetTemplateDesigns = o.getList("fleet_template_design").getAsList(FleetTemplateDesign::new);
        this.queued = o.getInt("queued");
        this.count = o.getInt("count");
        this.fleetSize = o.getDouble("fleet_size");
    }

    public FleetTemplate(int fleet, int queued, int count, double fleetSize, Collection<FleetTemplateDesign> fleetTemplateDesigns, Location homeBase) {
        this.fleet = fleet;
        this.queued = queued;
        this.count = count;
        this.fleetSize = fleetSize;
        this.fleetTemplateDesigns = new ArrayList<>(fleetTemplateDesigns);
        this.homeBase = homeBase;
    }

    public int getFleet() {
        return fleet;
    }

    public int getQueued() {
        return queued;
    }

    public int getCount() {
        return count;
    }

    public double getFleetSize() {
        return fleetSize;
    }

    public List<FleetTemplateDesign> getFleetTemplateDesigns() {
        return Collections.unmodifiableList(fleetTemplateDesigns);
    }

    public Location getHomeBase() {
        return homeBase;
    }
}
