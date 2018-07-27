package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class ShipDesigns {

    private final IntObjMap<ShipDesign> shipDesigns;

    public ShipDesigns(PdxScriptObject o) {
        this.shipDesigns = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(ShipDesign::new));
    }

    public ShipDesigns(IntObjMap<ShipDesign> shipDesigns) {
        this.shipDesigns = HashIntObjMaps.newImmutableMap(shipDesigns);
    }

    public IntObjMap<ShipDesign> getShipDesigns() {
        return shipDesigns;
    }
}
