package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;

import java.util.Objects;

public class Building {

    private static final Deduplicator<Building> DEDUPLICATOR = new Deduplicator<>();

    private final boolean ruined, modifier;
    private final String type;

    private Building(PdxScriptObject o) {
        this.type = o.getString("type");
        this.ruined = o.getBoolean("ruined");
        this.modifier = o.getBoolean("modifier");
    }

    private Building(boolean ruined, boolean modifier, String type) {
        this.ruined = ruined;
        this.modifier = modifier;
        this.type = type;
    }

    public static Building of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new Building(o));
    }

    public static Building of(boolean ruined, boolean modifier, String type) {
        return DEDUPLICATOR.deduplicate(new Building(ruined, modifier, type));
    }

    public boolean isRuined() {
        return ruined;
    }

    public boolean isModifier() {
        return modifier;
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
        return ruined == building.ruined && modifier == building.modifier && Objects.equals(type, building.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruined, modifier, type);
    }
}
