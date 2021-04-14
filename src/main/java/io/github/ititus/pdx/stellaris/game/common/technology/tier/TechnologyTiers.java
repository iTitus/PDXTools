package io.github.ititus.pdx.stellaris.game.common.technology.tier;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class TechnologyTiers {

    public final ImmutableIntObjectMap<TechnologyTier> tiers;

    public TechnologyTiers(PdxScriptObject o) {
        this.tiers = o.getAsIntObjectMap(TechnologyTier::new);
    }
}
