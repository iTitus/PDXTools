package io.github.ititus.pdx.stellaris.game.common;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class PlanetNameList {

    public final String name;
    public final ImmutableList<String> planets;

    public PlanetNameList(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.planets = o.getListAsStringList("planets");
    }
}
