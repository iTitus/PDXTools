package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Species {

    private final boolean sapient;
    private final int base, homePlanet, uplifter;
    private final String nameList, name, plural, adjective, speciesBio, speciesClass, portrait, nameData;
    private final Traits traits;
    private final Flags flags;

    public Species(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.base = o.getInt("base", -1);
        this.nameList = o.getString("name_list");
        this.name = o.getString("name");
        this.plural = o.getString("plural");
        this.adjective = o.getString("adjective");
        this.speciesBio = o.getString("species_bio");
        this.speciesClass = o.getString("class");
        this.portrait = o.getString("portrait");
        this.traits = o.getObject("traits").getAs(Traits::of);
        this.homePlanet = o.getInt("home_planet", -1);
        this.uplifter = o.getInt("uplifter", -1);
        this.sapient = o.getBoolean("sapient", true);
        this.nameData = o.getString("name_data");
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::of) : null;
    }

    public Species(boolean sapient, int base, int homePlanet, int uplifter, String nameList, String name,
                   String plural, String adjective, String speciesBio, String speciesClass, String portrait,
                   String nameData, Traits traits, Flags flags) {
        this.sapient = sapient;
        this.base = base;
        this.homePlanet = homePlanet;
        this.uplifter = uplifter;
        this.nameList = nameList;
        this.name = name;
        this.plural = plural;
        this.adjective = adjective;
        this.speciesBio = speciesBio;
        this.speciesClass = speciesClass;
        this.portrait = portrait;
        this.nameData = nameData;
        this.traits = traits;
        this.flags = flags;
    }

    public boolean isSapient() {
        return sapient;
    }

    public int getBase() {
        return base;
    }

    public int getHomePlanet() {
        return homePlanet;
    }

    public int getUplifter() {
        return uplifter;
    }

    public String getNameList() {
        return nameList;
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

    public String getSpeciesBio() {
        return speciesBio;
    }

    public String getSpeciesClass() {
        return speciesClass;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getNameData() {
        return nameData;
    }

    public Traits getTraits() {
        return traits;
    }

    public Flags getFlags() {
        return flags;
    }
}
