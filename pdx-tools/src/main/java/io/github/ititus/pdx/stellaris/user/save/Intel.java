package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Intel {

    public final int object;
    public final ImmutableList<HostileIntel> hostile;

    public Intel(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.object = o.getInt("object");
        this.hostile = o.getListAsList("hostile", HostileIntel::new);
    }
}
