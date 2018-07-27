package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FleetTemplates {

    private final IntObjMap<FleetTemplate> fleetTemplates;

    public FleetTemplates(PdxScriptObject o) {
        this.fleetTemplates = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(FleetTemplate::new));
    }

    public FleetTemplates(IntObjMap<FleetTemplate> fleetTemplates) {
        this.fleetTemplates = HashIntObjMaps.newImmutableMap(fleetTemplates);
    }

    public IntObjMap<FleetTemplate> getFleetTemplates() {
        return fleetTemplates;
    }
}
