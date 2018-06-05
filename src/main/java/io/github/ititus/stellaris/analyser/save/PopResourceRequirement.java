package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

public class PopResourceRequirement {

    private final String type;
    private final double value;

    public PopResourceRequirement(PdxScriptObject o) {
        this.type = o.getString("type");
        this.value = o.getDouble("value");
    }

    public PopResourceRequirement(String type, double value) {
        this.type = type;
        this.value = value;
    }
}
