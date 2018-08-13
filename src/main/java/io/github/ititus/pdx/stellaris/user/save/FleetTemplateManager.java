package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

public class FleetTemplateManager {

    private final ImmutableIntList fleetTemplate;

    public FleetTemplateManager(PdxScriptObject o) {
        PdxScriptList l = o.getList("fleet_template");
        this.fleetTemplate = l != null ? l.getAsIntList() : IntLists.immutable.empty();
    }

    public FleetTemplateManager(ImmutableIntList fleetTemplate) {
        this.fleetTemplate = fleetTemplate;
    }

    public ImmutableIntList getFleetTemplate() {
        return fleetTemplate;
    }
}
