package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class ShipDesigns {

    private final ImmutableIntObjectMap<ShipDesign> shipDesigns;

    public ShipDesigns(PdxScriptObject o) {
        this.shipDesigns = o.getAsIntObjectMap(ShipDesign::new);
    }

    public ShipDesigns(ImmutableIntObjectMap<ShipDesign> shipDesigns) {
        this.shipDesigns = shipDesigns;
    }

    public ImmutableIntObjectMap<ShipDesign> getShipDesigns() {
        return shipDesigns;
    }
}
