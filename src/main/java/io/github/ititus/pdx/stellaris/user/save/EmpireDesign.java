package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;

public class EmpireDesign {

    private final boolean spawnAsFallen, ignorePortraitDuplication, spawnEnabled;
    private final String key, shipPrefix, name, adjective, authority, government, planetName, planetClass, systemName, initializer, graphicalCulture, cityGraphicalCulture, room;
    private final ImmutableList<String> flags, ethics, civics;
    private final SpeciesDesign species, secondarySpecies;
    private final Flag empireFlag;
    private final LeaderDesign ruler;

    public EmpireDesign(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.key = o.getString("key");
        this.shipPrefix = o.getString("ship_prefix");
        this.species = o.getObject("species").getAs(SpeciesDesign::new);
        PdxScriptObject o1 = o.getObject("secondary_species");
        this.secondarySpecies = o1 != null ? o1.getAs(SpeciesDesign::new) : null;
        this.name = o.getString("name");
        this.adjective = o.getString("adjective");
        this.authority = o.getString("authority");
        PdxScriptList l = o.getList("flags");
        this.flags = l != null ? l.getAsStringList() : Lists.immutable.empty();
        this.government = o.getString("government");
        this.planetName = o.getString("planet_name");
        this.planetClass = o.getString("planet_class");
        this.systemName = o.getString("system_name");
        this.initializer = o.getString("initializer");
        this.graphicalCulture = o.getString("graphical_culture");
        this.cityGraphicalCulture = o.getString("city_graphical_culture");
        this.empireFlag = o.getObject("empire_flag").getAs(Flag::new);
        this.ruler = o.getObject("ruler").getAs(LeaderDesign::new);
        this.spawnAsFallen = o.getBoolean("spawn_as_fallen");
        this.ignorePortraitDuplication = o.getBoolean("ignore_portrait_duplication");
        this.room = o.getString("room");
        this.spawnEnabled = o.getBoolean("spawn_enabled");
        this.ethics = o.getImplicitList("ethic").getAsStringList();
        l = o.getList("civics");
        this.civics = l != null ? l.getAsStringList() : Lists.immutable.empty();
    }

    public EmpireDesign(boolean spawnAsFallen, boolean ignorePortraitDuplication, boolean spawnEnabled, String key, String shipPrefix, String name, String adjective, String authority, String government, String planetName, String planetClass, String systemName, String initializer, String graphicalCulture, String cityGraphicalCulture, String room, ImmutableList<String> flags, ImmutableList<String> ethics, ImmutableList<String> civics, SpeciesDesign species, SpeciesDesign secondarySpecies, Flag empireFlag, LeaderDesign ruler) {
        this.spawnAsFallen = spawnAsFallen;
        this.ignorePortraitDuplication = ignorePortraitDuplication;
        this.spawnEnabled = spawnEnabled;
        this.key = key;
        this.shipPrefix = shipPrefix;
        this.name = name;
        this.adjective = adjective;
        this.authority = authority;
        this.government = government;
        this.planetName = planetName;
        this.planetClass = planetClass;
        this.systemName = systemName;
        this.initializer = initializer;
        this.graphicalCulture = graphicalCulture;
        this.cityGraphicalCulture = cityGraphicalCulture;
        this.room = room;
        this.flags = flags;
        this.ethics = ethics;
        this.civics = civics;
        this.species = species;
        this.secondarySpecies = secondarySpecies;
        this.empireFlag = empireFlag;
        this.ruler = ruler;
    }

    public boolean isSpawnAsFallen() {
        return spawnAsFallen;
    }

    public boolean isIgnorePortraitDuplication() {
        return ignorePortraitDuplication;
    }

    public boolean isSpawnEnabled() {
        return spawnEnabled;
    }

    public String getKey() {
        return key;
    }

    public String getShipPrefix() {
        return shipPrefix;
    }

    public String getName() {
        return name;
    }

    public String getAdjective() {
        return adjective;
    }

    public String getAuthority() {
        return authority;
    }

    public String getGovernment() {
        return government;
    }

    public String getPlanetName() {
        return planetName;
    }

    public String getPlanetClass() {
        return planetClass;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getInitializer() {
        return initializer;
    }

    public String getGraphicalCulture() {
        return graphicalCulture;
    }

    public String getCityGraphicalCulture() {
        return cityGraphicalCulture;
    }

    public String getRoom() {
        return room;
    }

    public ImmutableList<String> getFlags() {
        return flags;
    }

    public ImmutableList<String> getEthics() {
        return ethics;
    }

    public ImmutableList<String> getCivics() {
        return civics;
    }

    public SpeciesDesign getSpecies() {
        return species;
    }

    public SpeciesDesign getSecondarySpecies() {
        return secondarySpecies;
    }

    public Flag getEmpireFlag() {
        return empireFlag;
    }

    public LeaderDesign getRuler() {
        return ruler;
    }
}
