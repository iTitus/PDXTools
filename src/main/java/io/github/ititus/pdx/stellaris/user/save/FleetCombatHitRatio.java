package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Objects;

public class FleetCombatHitRatio {

    private final int shipDesign, fleetIndex, evades, hits;
    private final String template;

    public FleetCombatHitRatio(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.template = o.getString("template");
        this.shipDesign = o.getInt("ship_design");
        this.fleetIndex = o.getInt("fleet_index");
        this.evades = o.getInt("evades");
        this.hits = o.getInt("hits");
    }

    public FleetCombatHitRatio(int shipDesign, int fleetIndex, int evades, int hits, String template) {
        this.shipDesign = shipDesign;
        this.fleetIndex = fleetIndex;
        this.evades = evades;
        this.hits = hits;
        this.template = template;
    }

    public int getShipDesign() {
        return shipDesign;
    }

    public int getFleetIndex() {
        return fleetIndex;
    }

    public int getEvades() {
        return evades;
    }

    public int getHits() {
        return hits;
    }

    public String getTemplate() {
        return template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FleetCombatHitRatio)) {
            return false;
        }
        FleetCombatHitRatio that = (FleetCombatHitRatio) o;
        return shipDesign == that.shipDesign && fleetIndex == that.fleetIndex && evades == that.evades && hits == that.hits && Objects.equals(template, that.template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipDesign, fleetIndex, evades, hits, template);
    }
}
