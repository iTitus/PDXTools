package io.github.ititus.pdx.stellaris.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Ships {

    private final Map<Integer, Ship> ships;

    public Ships(PdxScriptObject o) {
        this.ships = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Ship::new));
    }

    public Ships(Map<Integer, Ship> ships) {
        this.ships = new HashMap<>(ships);
    }

    public Map<Integer, Ship> getShips() {
        return Collections.unmodifiableMap(ships);
    }
}
