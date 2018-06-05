package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.Arrays;

public class Flag {

    private final Texture icon, background;
    private final String[] colors;

    public Flag(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.icon = new Texture(o.getObject("icon"));
        this.background = o.getObject("background").getAs(Texture::new);
        this.colors = o.getList("colors").getAsStringArray();
    }

    public Flag(Texture icon, Texture background, String[] colors) {
        this.icon = icon;
        this.background = background;
        if (colors.length != 4) {
            throw new IllegalArgumentException(Arrays.toString(colors));
        }
        this.colors = colors;
    }

}
