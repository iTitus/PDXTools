package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class AsteroidBelt {

    public final double innerRadius;
    public final String type;

    public AsteroidBelt(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.type = o.getString("type");
        this.innerRadius = o.getDouble("inner_radius");
    }
}
