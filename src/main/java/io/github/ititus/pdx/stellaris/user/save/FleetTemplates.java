package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class FleetTemplates {

    private final ImmutableIntObjectMap<FleetTemplate> fleetTemplates;

    public FleetTemplates(PdxScriptObject o) {
        this.fleetTemplates = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(FleetTemplate::new));
    }

    public FleetTemplates(ImmutableIntObjectMap<FleetTemplate> fleetTemplates) {
        this.fleetTemplates = fleetTemplates;
    }

    public ImmutableIntObjectMap<FleetTemplate> getFleetTemplates() {
        return fleetTemplates;
    }
}
