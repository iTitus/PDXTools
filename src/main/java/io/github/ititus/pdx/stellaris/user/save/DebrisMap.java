package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DebrisMap {

    private final Map<Integer, Debris> debrisMap;

    public DebrisMap(PdxScriptObject o) {
        this.debrisMap = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Debris::new));
    }

    public DebrisMap(Map<Integer, Debris> debrisMap) {
        this.debrisMap = new HashMap<>(debrisMap);
    }

    public Map<Integer, Debris> getDebrisMap() {
        return Collections.unmodifiableMap(debrisMap);
    }
}
