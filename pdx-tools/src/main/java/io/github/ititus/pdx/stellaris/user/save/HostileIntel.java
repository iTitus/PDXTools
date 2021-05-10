package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class HostileIntel {

    public final boolean hasBoss;
    public final int owner;
    public final double militaryPower;
    public final String name;
    public final ImmutableList<String> size;
    public final Coordinate coordinate;

    public HostileIntel(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.militaryPower = o.getDouble("military_power");
        this.hasBoss = o.getBoolean("has_boss", false);
        this.owner = o.getInt("owner");
        this.size = o.getListAsEmptyOrStringList("size");
    }
}
