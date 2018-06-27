package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ShipDesigns {

    private final Map<Integer, ShipDesign> shipDesigns;

    public ShipDesigns(PdxScriptObject o) {
        this.shipDesigns = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(ShipDesign::new));
    }

    public ShipDesigns(Map<Integer, ShipDesign> shipDesigns) {
        this.shipDesigns = new HashMap<>(shipDesigns);
    }

    public Map<Integer, ShipDesign> getShipDesigns() {
        return Collections.unmodifiableMap(shipDesigns);
    }
}
