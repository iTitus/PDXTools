package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Hyperlane {

    public final int to;
    public final double length;
    public final boolean bridge;

    public Hyperlane(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.to = o.getInt("to");
        this.length = o.getDouble("length");
        this.bridge = o.getBoolean("bridge", false);
    }
}
