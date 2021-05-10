package io.github.ititus.pdx.stellaris.game.common.planet_classes;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class PlanetSize {

    public final int min;
    public final int max;

    public PlanetSize(IPdxScript s) {
        if (s instanceof PdxScriptObject) {
            PdxScriptObject o = s.expectObject();
            this.min = o.getInt("min");
            this.max = o.getInt("max");
        } else {
            this.min = this.max = s.expectValue().expectInt();
        }
    }
}
