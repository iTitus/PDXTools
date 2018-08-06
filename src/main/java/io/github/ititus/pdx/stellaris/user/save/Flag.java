package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Objects;

public class Flag {

    private final ImmutableList<String> colors;
    private final Texture icon, background;

    public Flag(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.icon = o.getObject("icon").getAs(Texture::new);
        this.background = o.getObject("background").getAs(Texture::new);
        this.colors = o.getList("colors").getAsStringList();
    }

    public Flag(ImmutableList<String> colors, Texture icon, Texture background) {
        this.colors = colors;
        this.icon = icon;
        this.background = background;
    }

    public ImmutableList<String> getColors() {
        return colors;
    }

    public Texture getIcon() {
        return icon;
    }

    public Texture getBackground() {
        return background;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Flag)) {
            return false;
        }
        Flag flag = (Flag) o;
        return Objects.equals(colors, flag.colors) && Objects.equals(icon, flag.icon) && Objects.equals(background, flag.background);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colors, icon, background);
    }
}
