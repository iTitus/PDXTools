package io.github.ititus.pdx.stellaris.game.common.deposits;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Deposit {

    public final boolean isNull;
    public final DepositResources resources;

    public Deposit(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.isNull = o.getBoolean("is_null", false);
        this.resources = o.getObjectAs("resources", DepositResources::new, null);
    }
}
