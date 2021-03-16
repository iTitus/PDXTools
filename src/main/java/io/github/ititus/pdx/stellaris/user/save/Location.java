package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Location {

    public final String type;
    public final String area;
    public final String assignment;
    public final int id;

    public Location(PdxScriptObject o) {
        this.type = o.getString("type");
        this.area = o.getNullOrString("area");
        this.assignment = o.getNullOrString("assignment");
        this.id = o.getUnsignedInt("id");
    }
}
