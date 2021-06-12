package io.github.ititus.pdx.stellaris.game.gfx;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Entity {

    public final String name;
    public final String pdxmesh;
    public final ImmutableList<AnimationState> states;
    public final String defaultState;
    public final double scale;

    public Entity(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.pdxmesh = o.getString("pdxmesh", null);
        this.states = o.getImplicitListAsList("state", AnimationState::new);
        this.defaultState = o.getString("default_state", null);
        this.scale = o.getDouble("scale", 1);
    }
}
