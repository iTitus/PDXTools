package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Planets {

    private final ImmutableIntObjectMap<Planet> planets;

    public Planets(PdxScriptObject o) {
        this.planets = o.getObject("planet").getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(Planet::new));
    }

    public Planets(ImmutableIntObjectMap<Planet> planets) {
        this.planets = planets;
    }

    public ImmutableIntObjectMap<Planet> getPlanets() {
        return planets;
    }
}
