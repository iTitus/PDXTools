package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Homepop {

    public final int speciesIndex;

    public Homepop(PdxScriptObject o) {
        this.speciesIndex = o.getInt("species_index");
    }
}
