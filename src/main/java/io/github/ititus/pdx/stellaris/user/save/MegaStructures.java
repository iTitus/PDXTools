package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class MegaStructures {

    private final ImmutableIntObjectMap<MegaStructure> megaStructures;

    public MegaStructures(PdxScriptObject o) {
        this.megaStructures = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(MegaStructure::new));
    }

    public MegaStructures(ImmutableIntObjectMap<MegaStructure> megaStructures) {
        this.megaStructures = megaStructures;
    }

    public ImmutableIntObjectMap<MegaStructure> getMegaStructures() {
        return megaStructures;
    }
}
