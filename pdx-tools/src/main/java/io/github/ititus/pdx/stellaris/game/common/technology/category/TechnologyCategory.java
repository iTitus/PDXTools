package io.github.ititus.pdx.stellaris.game.common.technology.category;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TechnologyCategory {

    public final String icon;
    public String name;

    public TechnologyCategory(String name, IPdxScript s) {
        this.name = name;
        PdxScriptObject o = s.expectObject();
        this.icon = o.getString("icon");
    }
}
