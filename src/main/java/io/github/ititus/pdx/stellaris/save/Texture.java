package io.github.ititus.pdx.stellaris.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Texture {

    private final String category, file;

    public Texture(PdxScriptObject o) {
        this.category = o.getString("category");
        this.file = o.getString("file");
    }

    public Texture(String category, String file) {
        this.category = category;
        this.file = file;
    }

    public String getCategory() {
        return category;
    }

    public String getFile() {
        return file;
    }
}
