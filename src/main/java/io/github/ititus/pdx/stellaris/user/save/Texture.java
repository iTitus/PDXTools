package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Texture)) {
            return false;
        }
        Texture texture = (Texture) o;
        return Objects.equals(category, texture.category) && Objects.equals(file, texture.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, file);
    }
}
