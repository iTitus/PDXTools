package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

import static io.github.ititus.pdx.pdxscript.PdxHelper.nullOr;

public class Planets {

    public final ImmutableIntObjectMap<Planet> planets;

    public Planets(PdxScriptObject o) {
        this.planets = o.getObjectAsIntObjectMap("planet", nullOr(Planet::new));
    }

    public Planet get(int id) {
        return planets.get(id);
    }
}
