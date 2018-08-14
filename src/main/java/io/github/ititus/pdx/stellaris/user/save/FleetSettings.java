package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetSettings {

    private final boolean garrison, spawnDebris, isBoss;

    public FleetSettings(PdxScriptObject o) {
        this.garrison = o.getBoolean("garrison");
        this.spawnDebris = o.getBoolean("spawn_debris", true);
        this.isBoss = o.getBoolean("is_boss");
    }

    public FleetSettings(boolean garrison, boolean spawnDebris, boolean isBoss) {
        this.garrison = garrison;
        this.spawnDebris = spawnDebris;
        this.isBoss = isBoss;
    }

    public boolean isGarrison() {
        return garrison;
    }

    public boolean isSpawnDebris() {
        return spawnDebris;
    }

    public boolean isBoss() {
        return isBoss;
    }
}
