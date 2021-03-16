package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Flag {

    public final ImmutableList<String> colors;
    public final Texture icon, background;

    public Flag(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.icon = o.getObjectAs("icon", Texture::new);
        this.background = o.getObjectAs("background", Texture::new);
        this.colors = o.getListAsStringList("colors");
    }
}
