package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetCombatHitRatio {

    public final String template;
    public final int shipDesign;
    public final int fleetIndex;
    public final int evades;
    public final int hits;

    public FleetCombatHitRatio(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.template = o.getString("template");
        this.shipDesign = o.getInt("ship_design");
        this.fleetIndex = o.getInt("fleet_index");
        this.evades = o.getInt("evades", 0);
        this.hits = o.getInt("hits", 0);
    }
}
