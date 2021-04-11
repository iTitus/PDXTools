package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.ImmutableMap;

public class HalfSpecies {

    public final int speciesOne;
    public final int speciesTwo;
    public final int halfSpecies;

    public HalfSpecies(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.speciesOne = o.getInt("species_one");
        this.speciesTwo = o.getInt("species_two");
        this.halfSpecies = o.getInt("half_species");
    }
}
