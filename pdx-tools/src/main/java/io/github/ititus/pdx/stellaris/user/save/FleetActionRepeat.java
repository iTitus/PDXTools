package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetActionRepeat {

    public final FleetActionRepeatData data;
    public final int index;
    public final Scope scope;
    public final int iterations;

    public FleetActionRepeat(PdxScriptObject o) {
        this.data = o.getObjectAs("data", FleetActionRepeatData::new);
        this.index = o.getInt("index");
        this.scope = o.getObjectAs("scope", Scope::new);
        this.iterations = o.getInt("iterations");
    }
}
