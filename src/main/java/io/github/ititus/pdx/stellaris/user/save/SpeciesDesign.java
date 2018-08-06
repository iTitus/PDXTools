package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class SpeciesDesign {

    private final String speciesClass, portrait, name, plural, adjective, nameList;
    private final ImmutableList<String> traits;

    public SpeciesDesign(PdxScriptObject o) {
        this.speciesClass = o.getString("class");
        this.portrait = o.getString("portrait");
        this.name = o.getString("name");
        this.plural = o.getString("plural");
        this.adjective = o.getString("adjective");
        this.nameList = o.getString("name_list");
        this.traits = o.getImplicitList("trait").getAsStringList();
    }

    public SpeciesDesign(String speciesClass, String portrait, String name, String plural, String adjective, String nameList, ImmutableList<String> traits) {
        this.speciesClass = speciesClass;
        this.portrait = portrait;
        this.name = name;
        this.plural = plural;
        this.adjective = adjective;
        this.nameList = nameList;
        this.traits = traits;
    }

    public String getSpeciesClass() {
        return speciesClass;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getPlural() {
        return plural;
    }

    public String getAdjective() {
        return adjective;
    }

    public String getNameList() {
        return nameList;
    }

    public ImmutableList<String> getTraits() {
        return traits;
    }
}
