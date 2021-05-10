package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class SpeciesDesign {

    public final String speciesClass;
    public final String portrait;
    public final String name;
    public final String plural;
    public final String adjective;
    public final String nameList;
    public final ImmutableList<String> traits;

    public SpeciesDesign(PdxScriptObject o) {
        this.speciesClass = o.getString("class");
        this.portrait = o.getString("portrait");
        this.name = o.getString("name");
        this.plural = o.getString("plural");
        this.adjective = o.getString("adjective");
        this.nameList = o.getString("name_list");
        this.traits = o.getImplicitListAsStringList("trait");
    }
}
