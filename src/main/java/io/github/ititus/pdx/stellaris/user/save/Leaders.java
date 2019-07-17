package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Leaders {

    private final ImmutableIntObjectMap<Leader> leaders;

    public Leaders(PdxScriptObject o) {
        this.leaders = o.getAsIntObjectMap(Leader::new);
    }

    public Leaders(ImmutableIntObjectMap<Leader> leaders) {
        this.leaders = leaders;
    }

    public ImmutableIntObjectMap<Leader> getLeaders() {
        return leaders;
    }
}
