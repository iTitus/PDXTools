package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;

import java.util.Objects;

public class Building {

    private static final Deduplicator<Building> DEDUPLICATOR = new Deduplicator<>();

    private final boolean ruined;
    private final String type;

    private Building(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.type = o.getString("type");
        this.ruined = o.getBoolean("ruined");
    }

    private Building(boolean ruined, String type) {
        this.ruined = ruined;
        this.type = type;
    }

    public static Building of(IPdxScript s) {
        return DEDUPLICATOR.deduplicate(new Building(s));
    }

    public static Building of(boolean ruined, String type) {
        return DEDUPLICATOR.deduplicate(new Building(ruined, type));
    }

    public boolean isRuined() {
        return ruined;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Building)) {
            return false;
        }
        Building building = (Building) o;
        return ruined == building.ruined && Objects.equals(type, building.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruined, type);
    }
}
