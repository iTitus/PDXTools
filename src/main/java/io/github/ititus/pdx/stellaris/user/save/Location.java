package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.game.common.technology.Technology;

public class Location {

    public final String type;
    public final Technology.Area area;
    public final String assignment;
    public final int id;

    public Location(PdxScriptObject o) {
        this.type = o.getString("type");
        this.area = o.getNullOrEnum("area", Technology.Area::of);
        this.assignment = o.getNullOrString("assignment");
        this.id = o.getUnsignedInt("id");
    }
}
