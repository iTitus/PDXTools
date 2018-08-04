package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class AsteroidBelt {

    private final double innerRadius;
    private final String type;

    public AsteroidBelt(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.type = o.getString("type");
        this.innerRadius = o.getDouble("inner_radius");
    }

    public AsteroidBelt(double innerRadius, String type) {
        this.innerRadius = innerRadius;
        this.type = type;
    }

    public double getInnerRadius() {
        return innerRadius;
    }

    public String getType() {
        return type;
    }
}
