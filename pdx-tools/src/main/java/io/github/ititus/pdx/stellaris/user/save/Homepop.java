package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Homepop {

    public final int species;

    public Homepop(PdxScriptObject o) {
        this.species = o.getInt("species");
    }
}
