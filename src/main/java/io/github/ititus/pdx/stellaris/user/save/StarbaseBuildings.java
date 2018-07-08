package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StarbaseBuildings {

    private final Map<Integer, String> starbaseBuildings;

    public StarbaseBuildings(PdxScriptObject o) {
        this.starbaseBuildings = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOrString());
    }

    public StarbaseBuildings(Map<Integer, String> starbaseBuildings) {
        this.starbaseBuildings = new HashMap<>(starbaseBuildings);
    }

    public Map<Integer, String> getStarbaseBuildings() {
        return Collections.unmodifiableMap(starbaseBuildings);
    }
}
