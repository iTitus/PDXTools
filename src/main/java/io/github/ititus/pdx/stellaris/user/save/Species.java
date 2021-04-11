package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.ImmutableMap;

public class Species {

    public final int base;
    public final boolean killed;
    public final String nameList;
    public final String name;
    public final String plural;
    public final String adjective;
    public final String speciesClass;
    public final String portrait;
    public final Traits traits;
    public final int homePlanet;
    public final int uplifter;
    public final boolean sapient;
    public final String nameData;
    public final ImmutableMap<String, FlagData> flags;
    public final boolean halfSpecies;

    public Species(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.base = o.getInt("base", -1);
        this.killed = o.getBoolean("killed", false);
        this.nameList = o.getString("name_list");
        this.name = o.getString("name");
        this.plural = o.getString("plural", null);
        this.adjective = o.getString("adjective", null);
        this.speciesClass = o.getString("class", null);
        this.portrait = o.getString("portrait");
        this.traits = o.getObjectAs("traits", Traits::new);
        this.homePlanet = o.getInt("home_planet", -1);
        this.uplifter = o.getInt("uplifter", -1);
        this.sapient = o.getBoolean("sapient", true);
        this.nameData = o.getString("name_data", null);
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.halfSpecies = o.getBoolean("half_species", false);
    }
}
