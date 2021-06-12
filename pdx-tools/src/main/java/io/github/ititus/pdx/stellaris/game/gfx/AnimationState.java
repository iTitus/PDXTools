package io.github.ititus.pdx.stellaris.game.gfx;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class AnimationState {

    public final String name;
    public final String animation;

    public AnimationState(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.animation = o.getString("animation");
    }
}
