package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Building {

    private final String type;
    private final boolean ruined, modifier;

    public Building(PdxScriptObject o) {
        this.type = o.getString("type");
        this.ruined = o.getBoolean("ruined");
        this.modifier = o.getBoolean("modifier");
    }

    public Building(String type, boolean ruined, boolean modifier) {
        this.type = type;
        this.ruined = ruined;
        this.modifier = modifier;
    }

    public String getType() {
        return type;
    }

    public boolean isRuined() {
        return ruined;
    }

    public boolean isModifier() {
        return modifier;
    }
}
