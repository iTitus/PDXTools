package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FleetTemplates {

    private final Map<Integer, FleetTemplate> fleetTemplates;

    public FleetTemplates(PdxScriptObject o) {
        this.fleetTemplates = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(FleetTemplate::new));
    }

    public FleetTemplates(Map<Integer, FleetTemplate> fleetTemplates) {
        this.fleetTemplates = new HashMap<>(fleetTemplates);
    }

    public Map<Integer, FleetTemplate> getFleetTemplates() {
        return Collections.unmodifiableMap(fleetTemplates);
    }
}
