package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class StarbaseManager {

    private final Starbases starbases;

    public StarbaseManager(PdxScriptObject o) {
        this.starbases = o.getObject("starbases").getAs(Starbases::new);
    }

    public StarbaseManager(Starbases starbaseBuildings) {
        this.starbases = starbaseBuildings;
    }

    public Starbases getStarbases() {
        return starbases;
    }
}
