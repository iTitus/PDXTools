package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
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

    public Map<Integer, Bypass> getBypasses() {
        return Collections.unmodifiableMap(bypasses);
    }
}
