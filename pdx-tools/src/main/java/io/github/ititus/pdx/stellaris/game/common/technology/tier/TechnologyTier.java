package io.github.ititus.pdx.stellaris.game.common.technology.tier;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TechnologyTier {

    public final int tier;
    public final int previouslyUnlocked;

    public TechnologyTier(int tier, IPdxScript s) {
        this.tier = tier;
        PdxScriptObject o = s.expectObject();
        this.previouslyUnlocked = o.getInt("previously_unlocked", 0);
    }
}
