package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Orbitable {

    public final int planet;
    public final int starbase;

    public Orbitable(PdxScriptObject o) {
        this.planet = o.getInt("planet", -1);
        this.starbase = o.getInt("starbase", -1);
    }
}
