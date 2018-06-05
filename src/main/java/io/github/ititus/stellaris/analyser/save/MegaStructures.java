package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

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
}
