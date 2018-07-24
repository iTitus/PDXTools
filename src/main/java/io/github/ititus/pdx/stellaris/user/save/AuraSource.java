package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class AuraSource {

    private final int ship;
    private final String aura;

    public AuraSource(PdxScriptObject o) {
        this.ship = o.getInt("ship");
        this.aura = o.getString("aura");
    }

    public AuraSource(int ship, String aura) {
        this.ship = ship;
        this.aura = aura;
    }

    public int getShip() {
        return ship;
    }

    public String getAura() {
        return aura;
    }
}
