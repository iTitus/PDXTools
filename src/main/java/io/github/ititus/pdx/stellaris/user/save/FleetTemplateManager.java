package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class FleetTemplateManager {

    public final ImmutableIntList fleetTemplate;

    public FleetTemplateManager(PdxScriptObject o) {
        this.fleetTemplate = o.getListAsEmptyOrIntList("fleet_template");
    }
}
