package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class StarbaseBuildings {

    private final ImmutableIntObjectMap<String> starbaseBuildings;

    public StarbaseBuildings(PdxScriptObject o) {
        this.starbaseBuildings = o.getAsIntObjectMap(Integer::parseInt, PdxConstants.NULL_OR_STRING);
    }

    public StarbaseBuildings(ImmutableIntObjectMap<String> starbaseBuildings) {
        this.starbaseBuildings = starbaseBuildings;
    }

    public ImmutableIntObjectMap<String> getStarbaseBuildings() {
        return starbaseBuildings;
    }
}
