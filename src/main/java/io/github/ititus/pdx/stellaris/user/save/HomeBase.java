package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class HomeBase {

    private final int starbase;

    public HomeBase(PdxScriptObject o) {
        this.starbase = o.getInt("starbase");
    }

    public HomeBase(int starbase) {
        this.starbase = starbase;
    }

    public int getStarbase() {
        return starbase;
    }
}
