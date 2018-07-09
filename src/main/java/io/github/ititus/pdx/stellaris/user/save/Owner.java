package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Owner {

    private final int type, id;

    public Owner(PdxScriptObject o) {
        this.type = o.getInt("type");
        this.id = o.getInt("id");
    }

    public Owner(int type, int id) {
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
