package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

public class Hyperlane {

    private final int to;
    private final double length;

    public Hyperlane(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.to = o.getInt("to");
        this.length = o.getDouble("length");
    }

    public Hyperlane(int to, double length) {
        this.to = to;
        this.length = length;
    }
}
