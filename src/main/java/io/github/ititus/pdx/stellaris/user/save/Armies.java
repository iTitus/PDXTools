package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Armies {

    private final ImmutableIntObjectMap<Army> armies;

    public Armies(PdxScriptObject o) {
        this.armies = o.getAsIntObjectMap(Army::new);
    }

    public Armies(ImmutableIntObjectMap<Army> armies) {
        this.armies = armies;
    }

    public ImmutableIntObjectMap<Army> getArmies() {
        return armies;
    }
}
