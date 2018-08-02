package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FTLJump {

    private final int to, fleet, bypassFrom, bypassTo;
    private final String jumpMethod;
    private final Coordinate from;

    public FTLJump(PdxScriptObject o) {
        this.from = o.getObject("from").getAs(Coordinate::of);
        this.to = o.getInt("to");
        this.fleet = o.getUnsignedInt("fleet");
        this.jumpMethod = o.getString("jump_method");
        this.bypassFrom = o.getUnsignedInt("bypass_from");
        this.bypassTo = o.getUnsignedInt("bypass_to");
    }

    public FTLJump(int to, int fleet, int bypassFrom, int bypassTo, String jumpMethod, Coordinate from) {
        this.to = to;
        this.fleet = fleet;
        this.bypassFrom = bypassFrom;
        this.bypassTo = bypassTo;
        this.jumpMethod = jumpMethod;
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public int getFleet() {
        return fleet;
    }

    public int getBypassFrom() {
        return bypassFrom;
    }

    public int getBypassTo() {
        return bypassTo;
    }

    public String getJumpMethod() {
        return jumpMethod;
    }

    public Coordinate getFrom() {
        return from;
    }
}
