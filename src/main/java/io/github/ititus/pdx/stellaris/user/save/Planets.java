package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Planets {

    public final ImmutableIntObjectMap<Planet> planets;

    public Planets(PdxScriptObject o) {
        this.planets = o.getObjectAsIntObjectMap("planet", Planet::new);
    }

    public Planet get(int id) {
        return planets.get(id);
    }
}
