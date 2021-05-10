package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetSettings {

    public final boolean garrison;
    public final boolean spawnDebris;
    public final boolean isBoss;

    public FleetSettings(PdxScriptObject o) {
        this.garrison = o.getBoolean("garrison", false);
        this.spawnDebris = o.getBoolean("spawn_debris", true);
        this.isBoss = o.getBoolean("is_boss", false);
    }
}
