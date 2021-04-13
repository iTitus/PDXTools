package io.github.ititus.pdx.stellaris.game.gfx;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Light {

    public final String name;

    public Light(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
    }
}
