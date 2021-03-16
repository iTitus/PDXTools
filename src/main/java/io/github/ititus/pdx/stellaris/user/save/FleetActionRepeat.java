package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetActionRepeat {

    public final int index;
    public final int iterations;
    public final FleetActionRepeatData data;
    public final Scope scope;
    public final FleetActionRepeatCurrent current;

    public FleetActionRepeat(PdxScriptObject o) {
        this.index = o.getInt("index");
        this.iterations = o.getInt("iterations");
        this.data = o.getObjectAs("data", FleetActionRepeatData::new);
        this.scope = o.getObjectAs("scope", Scope::new);
        this.current = o.getObjectAs("current", FleetActionRepeatCurrent::new);
    }
}
