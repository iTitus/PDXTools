package io.github.ititus.pdx.stellaris.game.gfx;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Particle {

    public final String name;

    public Particle(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
    }
}
