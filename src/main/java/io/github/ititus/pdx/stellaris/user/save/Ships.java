package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Ships {

    private final IntObjMap<Ship> ships;

    public Ships(PdxScriptObject o) {
        this.ships = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Ship::new));
    }

    public Ships(IntObjMap<Ship> ships) {
        this.ships = HashIntObjMaps.newImmutableMap(ships);
    }

    public IntObjMap<Ship> getShips() {
        return ships;
    }
}
