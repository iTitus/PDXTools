package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Planets {

    private final IntObjMap<Planet> planets;

    public Planets(PdxScriptObject o) {
        this.planets = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Planet::new));
    }

    public Planets(IntObjMap<Planet> planets) {
        this.planets = HashIntObjMaps.newImmutableMap(planets);
    }

    public IntObjMap<Planet> getPlanets() {
        return planets;
    }
}
