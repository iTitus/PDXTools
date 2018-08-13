package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Intel {

    private final int object;
    private final ImmutableList<HostileIntel> hostile;

    public Intel(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.object = o.getInt("object");
        this.hostile = o.getList("hostile").getAsList(HostileIntel::new);
    }

    public Intel(int object, ImmutableList<HostileIntel> hostile) {
        this.object = object;
        this.hostile = hostile;
    }

    public int getObject() {
        return object;
    }

    public ImmutableList<HostileIntel> getHostile() {
        return hostile;
    }
}
