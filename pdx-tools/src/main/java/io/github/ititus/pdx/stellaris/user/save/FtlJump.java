package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FtlJump {

    public final Coordinate from;
    public final int to;
    public final int fleet;
    public final String jumpMethod;
    public final int bypassFrom;
    public final int bypassTo;

    public FtlJump(PdxScriptObject o) {
        this.from = o.getObjectAs("from", Coordinate::new);
        this.to = o.getInt("to", -1);
        this.fleet = o.getUnsignedInt("fleet");
        this.jumpMethod = o.getString("jump_method");
        this.bypassFrom = o.getUnsignedInt("bypass_from");
        this.bypassTo = o.getUnsignedInt("bypass_to");
    }
}
