package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Pops {

    private final ImmutableIntObjectMap<Pop> pops;

    public Pops(PdxScriptObject o) {
        this.pops = o.getAsIntObjectMap(Pop::new);
    }

    public Pops(ImmutableIntObjectMap<Pop> pops) {
        this.pops = pops;
    }

    public ImmutableIntObjectMap<Pop> getPops() {
        return pops;
    }
}
