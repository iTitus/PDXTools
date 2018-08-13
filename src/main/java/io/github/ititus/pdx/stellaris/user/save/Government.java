package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Government {

    private final int heir;
    private final String type, authority;
    private final ImmutableList<String> civics;

    public Government(PdxScriptObject o) {
        this.type = o.getString("type");
        this.authority = o.getString("authority");
        this.civics = o.getList("civics").getAsStringList();
        this.heir = o.getInt("heir", -1);
    }

    public Government(int heir, String type, String authority, ImmutableList<String> civics) {
        this.heir = heir;
        this.type = type;
        this.authority = authority;
        this.civics = civics;
    }

    public int getHeir() {
        return heir;
    }

    public String getType() {
        return type;
    }

    public String getAuthority() {
        return authority;
    }

    public ImmutableList<String> getCivics() {
        return civics;
    }
}
