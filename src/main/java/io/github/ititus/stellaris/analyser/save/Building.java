package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

public class Building {

    private final String type;
    private final boolean modifier;

    public Building(PdxScriptObject o) {
        this.type = o.getString("type");
        this.modifier = o.getBoolean("modifier");
    }

    public Building(String type, boolean modifier) {
        this.type = type;
        this.modifier = modifier;
    }

    public String getType() {
        return type;
    }

    public boolean isModifier() {
        return modifier;
    }
}
