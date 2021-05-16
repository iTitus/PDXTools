package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Deposit {

    public final int id;
    public final String type;
    public final boolean killed;
    public final String swapType;
    public final int planet;

    public Deposit(int id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
        this.type = o.getString("type", null);
        this.killed = o.getBoolean("killed", false);
        this.swapType = o.getString("swap_type", null);
        this.planet = o.getUnsignedInt("planet");
    }
}
