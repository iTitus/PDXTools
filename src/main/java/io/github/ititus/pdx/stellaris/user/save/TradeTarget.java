package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TradeTarget {

    public final int target;
    public final int distance;

    public TradeTarget(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.target = o.getInt("target");
        this.distance = o.getInt("distance");
    }
}
