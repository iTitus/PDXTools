package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class EmpireDesign {

    public final boolean spawnAsFallen;
    public final boolean ignorePortraitDuplication;
    public final boolean spawnEnabled;
    public final String key;
    public final String shipPrefix;
    public final String name;
    public final String adjective;
    public final String authority;
    public final String government;
    public final String planetName;
    public final String planetClass;
    public final String systemName;
    public final String initializer;
    public final String graphicalCulture;
    public final String cityGraphicalCulture;
    public final String room;
    public final ImmutableList<String> flags;
    public final ImmutableList<String> ethics;
    public final ImmutableList<String> civics;
    public final SpeciesDesign species;
    public final SpeciesDesign secondarySpecies;
    public final Flag empireFlag;
    public final LeaderDesign ruler;

    public EmpireDesign(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.key = o.getString("key");
        this.shipPrefix = o.getString("ship_prefix");
        this.species = o.getObjectAs("species", SpeciesDesign::new);
        this.secondarySpecies = o.getObjectAsNullOr("secondary_species", SpeciesDesign::new);
        this.name = o.getString("name");
        this.adjective = o.getString("adjective");
        this.authority = o.getString("authority");
        this.flags = o.getListAsEmptyOrStringList("flags");
        this.government = o.getString("government");
        this.planetName = o.getString("planet_name");
        this.planetClass = o.getString("planet_class");
        this.systemName = o.getString("system_name");
        this.initializer = o.getString("initializer");
        this.graphicalCulture = o.getString("graphical_culture");
        this.cityGraphicalCulture = o.getString("city_graphical_culture");
        this.empireFlag = o.getObjectAs("empire_flag", Flag::new);
        this.ruler = o.getObjectAs("ruler", LeaderDesign::new);
        this.spawnAsFallen = o.getBoolean("spawn_as_fallen");
        this.ignorePortraitDuplication = o.getBoolean("ignore_portrait_duplication");
        this.room = o.getString("room");
        this.spawnEnabled = o.getBoolean("spawn_enabled");
        this.ethics = o.getImplicitListAsStringList("ethic");
        this.civics = o.getListAsEmptyOrStringList("civics");
    }
}
