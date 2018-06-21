package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

public class AsteroidBelt {

    private final String type;
    private final double innerRadius;

    public AsteroidBelt(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.type = o.getString("type");
        this.innerRadius = o.getDouble("inner_radius");
    }

    public AsteroidBelt(String type, double innerRadius) {
        this.type = type;
        this.innerRadius = innerRadius;
    }

    public String getType() {
        return type;
    }

    public double getInnerRadius() {
        return innerRadius;
    }
}
