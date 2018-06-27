package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Planets {

    private final Map<Integer, Planet> planets;

    public Planets(PdxScriptObject o) {
        this.planets = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Planet::new));
    }

    public Planets(Map<Integer, Planet> planets) {
        this.planets = new HashMap<>(planets);
    }

    public Map<Integer, Planet> getPlanets() {
        return Collections.unmodifiableMap(planets);
    }
}
