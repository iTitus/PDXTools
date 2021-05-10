package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Texture {

    public final String category;
    public final String file;

    public Texture(PdxScriptObject o) {
        this.category = o.getString("category");
        this.file = o.getString("file");
    }
}
