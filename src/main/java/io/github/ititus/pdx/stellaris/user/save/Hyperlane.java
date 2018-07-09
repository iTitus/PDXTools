package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Hyperlane {

    private final boolean bridge;
    private final int to;
    private final double length;

    public Hyperlane(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.to = o.getInt("to");
        this.length = o.getDouble("length");
        this.bridge = o.getBoolean("bridge");
    }

    public Hyperlane(boolean bridge, int to, double length) {
        this.bridge = bridge;
        this.to = to;
        this.length = length;
    }

    public boolean isBridge() {
        return bridge;
    }

    public int getTo() {
        return to;
    }

    public double getLength() {
        return length;
    }
}
