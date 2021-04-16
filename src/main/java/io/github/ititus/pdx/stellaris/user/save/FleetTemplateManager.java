package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class FleetTemplateManager {

    public final ImmutableIntList fleetTemplates;

    public FleetTemplateManager(PdxScriptObject o) {
        this.fleetTemplates = o.getListAsEmptyOrIntList("fleet_template");
    }
}
