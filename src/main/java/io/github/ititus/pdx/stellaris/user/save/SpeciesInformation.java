package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class SpeciesInformation {

    public final int numPops;
    public final int numEnslaved;

    public SpeciesInformation(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.numPops = o.getInt("num_pops");
        this.numEnslaved = o.getInt("num_enslaved", 0);
    }
}
