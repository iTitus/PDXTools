package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.HashMap;
import java.util.Map;

public class Bypasses {

    private final Map<Integer, Bypass> bypasses;

    public Bypasses(PdxScriptObject o) {
        this.bypasses = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Bypass::new));
    }

    public Bypasses(Map<Integer, Bypass> bypasses) {
        this.bypasses = new HashMap<>(bypasses);
    }
}
