package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Deposit {

    public final String type;
    public final String swapType;
    public final int planet;

    public Deposit(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.type = o.getString("type", null);
        this.swapType = o.getString("swap_type", null);
        this.planet = o.getInt("planet");
    }
}
