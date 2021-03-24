package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Government {

    public final String type;
    public final String authority;
    public final ImmutableList<String> civics;
    public final String origin;
    public final int heir;

    public Government(PdxScriptObject o) {
        this.type = o.getString("type");
        this.authority = o.getString("authority");
        this.civics = o.getListAsStringList("civics");
        this.origin = o.getString("origin");
        this.heir = o.getInt("heir", -1);
    }
}
