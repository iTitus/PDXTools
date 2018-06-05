package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

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
}
