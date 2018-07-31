package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class StrikeCrafts {

    private final ImmutableIntObjectMap<StrikeCraft> strikeCrafts;

    public StrikeCrafts(PdxScriptObject o) {
        this.strikeCrafts = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(StrikeCraft::new));
    }

    public StrikeCrafts(ImmutableIntObjectMap<StrikeCraft> strikeCrafts) {
        this.strikeCrafts = strikeCrafts;
    }

    public ImmutableIntObjectMap<StrikeCraft> getStrikeCrafts() {
        return strikeCrafts;
    }
}
