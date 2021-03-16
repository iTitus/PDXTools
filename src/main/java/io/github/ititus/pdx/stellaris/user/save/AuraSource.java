package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class AuraSource {

    public final int ship;
    public final String aura;

    public AuraSource(PdxScriptObject o) {
        this.ship = o.getInt("ship");
        this.aura = o.getString("aura");
    }
}
