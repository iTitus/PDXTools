package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableLongObjectMap;

public class Ships {

    private final ImmutableLongObjectMap<Ship> ships;

    public Ships(PdxScriptObject o) {
        this.ships = o.getAsLongObjectMap(Ship::new);
    }

    public Ships(ImmutableLongObjectMap<Ship> ships) {
        this.ships = ships;
    }

    public ImmutableLongObjectMap<Ship> getShips() {
        return ships;
    }
}
