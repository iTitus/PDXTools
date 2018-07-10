package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Property {

    private final int type, id;

    public Property(PdxScriptObject o) {
        this.type = o.getInt("type");
        this.id = o.getUnsignedInt("id");
    }

    public Property(int type, int id) {
        this.type = type;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
