package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetActionRepeat {

    private final int index, iterations;
    private final FleetActionRepeatData data;
    private final Scope scope;
    private final FleetActionRepeatCurrent current;

    public FleetActionRepeat(PdxScriptObject o) {
        this.index = o.getInt("index");
        this.iterations = o.getInt("iterations");
        this.data = o.getObject("data").getAs(FleetActionRepeatData::new);
        this.scope = o.getObject("scope").getAs(Scope::new);
        this.current = o.getObject("current").getAs(FleetActionRepeatCurrent::new);
    }

    public FleetActionRepeat(int index, int iterations, FleetActionRepeatData data, Scope scope,
                             FleetActionRepeatCurrent current) {
        this.index = index;
        this.iterations = iterations;
        this.data = data;
        this.scope = scope;
        this.current = current;
    }

    public int getIndex() {
        return index;
    }

    public int getIterations() {
        return iterations;
    }

    public FleetActionRepeatData getData() {
        return data;
    }

    public Scope getScope() {
        return scope;
    }

    public FleetActionRepeatCurrent getCurrent() {
        return current;
    }
}
