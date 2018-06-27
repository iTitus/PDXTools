package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MegaStructures {

    private final Map<Integer, MegaStructure> megaStructures;

    public MegaStructures(PdxScriptObject o) {
        this.megaStructures = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(MegaStructure::new));
    }

    public MegaStructures(Map<Integer, MegaStructure> megaStructures) {
        this.megaStructures = new HashMap<>(megaStructures);
    }

    public Map<Integer, MegaStructure> getMegaStructures() {
        return Collections.unmodifiableMap(megaStructures);
    }
}
