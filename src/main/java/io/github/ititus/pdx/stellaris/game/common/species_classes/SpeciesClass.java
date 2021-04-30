package io.github.ititus.pdx.stellaris.game.common.species_classes;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public record SpeciesClass(
        String name,
        String archetype
) {

    public static SpeciesClass of(String name, IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        return new SpeciesClass(
                name,
                o.getString("archetype", null)
        );
    }
}
