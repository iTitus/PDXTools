package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class MegastructureUpgrade {

    public final String upgradeTo;
    public final double progress;
    public final int halted;

    public MegastructureUpgrade(PdxScriptObject o) {
        this.upgradeTo = o.getString("upgrade_to");
        this.progress = o.getDouble("progress");
        this.halted = o.getInt("halted");
    }
}
