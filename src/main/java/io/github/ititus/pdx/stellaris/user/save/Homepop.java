package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Homepop {

    private final int speciesIndex;

    public Homepop(PdxScriptObject o) {
        this.speciesIndex = o.getInt("species_index");
    }

    public Homepop(int speciesIndex) {
        this.speciesIndex = speciesIndex;
    }

    public int getSpeciesIndex() {
        return speciesIndex;
    }
}
