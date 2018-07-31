package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Ships {

    private final ImmutableIntObjectMap<Ship> ships;

    public Ships(PdxScriptObject o) {
        this.ships = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(Ship::new));
    }

    public Ships(ImmutableIntObjectMap<Ship> ships) {
        this.ships = ships;
    }

    public ImmutableIntObjectMap<Ship> getShips() {
        return ships;
    }
}
