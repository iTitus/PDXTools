package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.time.LocalDate;

public class Country {

    private final boolean customName, autoShipDesigns, hasAdvisor, initialized;
    private final int colorIndex, capital, speciesIndex, nextTransportFleetNumber, ruler, alliance, startingSystem;
    private final double militaryPower, fleetSize;
    private final String name, adjective, graphicalCulture, cityGraphicalCulture, room, lastAllianceName, personality, rulerTitle, nameList, shipPrefix, type, customization;
    private final LocalDate lastDateWasHuman, lastDateWarLost, lastDateAtWar, nextElection, governmentDate;
    private final ImmutableIntList surveyed, visitedObjects, intelLevels, highestIntelLevels, sensorRangeFleets, ownedLeaders, ownedFleets, ownedMegastructures, ownedArmies, ownedPlanets, restrictedSystems, controlledPlanets, shipDesigns, usableBypasses, hyperlaneSystems;
    private final ImmutableList<String> policyFlags, shownMessageTypes, traditions, ascensionPerks, seenBypassTypes;
    private final ImmutableList<Intel> intel;
    private final ImmutableList<TimedModifier> timedModifiers;
    private final ImmutableList<ActivePolicy> activePolicies;
    private final ImmutableList<Edict> edicts;
    private final ImmutableList<RegnalNumber> regnalNumbers;
    private final ImmutableList<Species> speciesModTemplates;
    private final Flag flag;
    private final TechStatus techStatus;
    private final CountryBudget budget;
    private final Events events;
    private final TerraIncognita terraIncognita;
    private final AI ai;
    private final Ethos ethos;
    private final FleetTemplateManager fleetTemplateManager;
    private final Government government;
    private final Flags flags;
    private final Variables variables;
    private final Faction faction;
    private final CountingList shipNames;
    private final ControlGroups controlGroups;
    private final Modules modules;
    private final RandomNameVariables randomNameVariables;
    private final RelationsManager relationsManager;
    private final Property location;

    public Country(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.flag = o.getObject("flag").getAs(Flag::new);
        this.colorIndex = o.getInt("color_index");
        this.name = o.getString("name");
        this.adjective = o.getString("adjective");
        this.customName = o.getBoolean("custom_name");
        this.techStatus = o.getObject("tech_status").getAs(TechStatus::new);
        this.autoShipDesigns = o.getBoolean("auto_ship_designs", true);
        this.lastDateWasHuman = o.getDate("last_date_was_human");
        this.lastDateWarLost = o.getDate("last_date_war_lost");
        this.lastDateAtWar = o.getDate("last_date_at_war");
        this.budget = o.getObject("budget").getAs(CountryBudget::new);
        this.events = o.getObject("events").getAs(Events::new);
        this.terraIncognita = o.getObject("terra_incognita").getAs(TerraIncognita::new);
        this.militaryPower = o.getDouble("military_power");
        // TODO: economy_power, victory_rank, victory_score, tech_power, immigration, emigration
        this.fleetSize = o.getDouble("fleet_size");
        // TODO: empire_size, empire_cohesion, new_colonies, sapient
        this.graphicalCulture = o.getString("graphical_culture");
        this.cityGraphicalCulture = o.getString("city_graphical_culture");
        this.room = o.getString("room");
        this.ai = o.getObject("ai").getAs(AI::new);
        this.capital = o.getInt("capital", -1);
        this.speciesIndex = o.getInt("species_index");
        // TODO: built_species
        PdxScriptObject o1 = o.getObject("ethos");
        this.ethos = o1 != null ? o1.getAs(Ethos::of) : null;
        this.lastAllianceName = o.getString("last_alliance_name");
        this.fleetTemplateManager = o.getObject("fleet_template_manager").getAs(FleetTemplateManager::new);
        o1 = o.getObject("government");
        this.government = o1 != null ? o1.getAs(Government::new) : null;
        this.personality = o.getString("personality");
        this.rulerTitle = o.getString("ruler_title");
        this.nextElection = o.getDate("next_election");
        this.governmentDate = o.getDate("government_date");
        PdxScriptList l = o.getList("surveyed");
        this.surveyed = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.visitedObjects = o.getList("visited_objects").getAsIntList();
        this.intelLevels = o.getList("intel_level").getAsIntList();
        l = o.getList("highest_intel_level");
        this.highestIntelLevels = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("intel");
        this.intel = l != null ? l.getAsList(Intel::new) : null;
        this.timedModifiers = o.getImplicitList("timed_modifier").getAsList(TimedModifier::new);
        o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::of) : null;
        o1 = o.getObject("variables");
        this.variables = o1 != null ? o1.getAs(Variables::new) : null;
        this.nextTransportFleetNumber = o.getInt("next_transport_fleet_number", 1);
        l = o.getList("sensor_range_fleets");
        this.sensorRangeFleets = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.faction = o.getObject("faction").getAs(Faction::new);
        this.nameList = o.getString("name_list");
        o1 = o.getObject("ship_names");
        this.shipNames = o1 != null ? o1.getAs(CountingList::new) : null;
        this.ruler = o.getInt("ruler", -1);
        o1 = o.getObject("control_groups");
        this.controlGroups = o1 != null ? o1.getAs(ControlGroups::new) : null;
        this.shipPrefix = o.getString("ship_prefix");
        this.activePolicies = o.getList("active_policies").getAsList(ActivePolicy::new);
        this.policyFlags = o.getList("policy_flags").getAsStringList();
        this.alliance = o.getInt("alliance", -1);
        // TODO: subjects
        this.startingSystem = o.getInt("starting_system", -1);
        this.hasAdvisor = o.getBoolean("has_advisor", true);
        l = o.getList("shown_message_types");
        this.shownMessageTypes = l != null ? l.getAsStringList() : Lists.immutable.empty();
        l = o.getList("owned_leaders");
        this.ownedLeaders = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("owned_fleets");
        this.ownedFleets = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("owned_megastructures");
        this.ownedMegastructures = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("traditions");
        this.traditions = l != null ? l.getAsStringList() : Lists.immutable.empty();
        l = o.getList("ascension_perks");
        this.ascensionPerks = l != null ? l.getAsStringList() : Lists.immutable.empty();
        l = o.getList("owned_armies");
        this.ownedArmies = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("owned_planets");
        this.ownedPlanets = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        // TODO: branch_office_systems
        l = o.getList("restricted_systems");
        this.restrictedSystems = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("controlled_planets");
        this.controlledPlanets = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("ship_design");
        this.shipDesigns = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("edicts");
        this.edicts = l != null ? l.getAsList(Edict::new) : Lists.immutable.empty();
        this.type = o.getString("type");
        this.modules = o.getObject("modules").getAs(Modules::new); // TODO: check
        o1 = o.getObject("sectors");
        this.initialized = o.getBoolean("initialized");
        l = o.getList("regnal_numbers");
        this.regnalNumbers = l != null ? l.getAsList(RegnalNumber::new) : Lists.immutable.empty();
        o1 = o.getObject("random_name_variables");
        this.randomNameVariables = o1 != null ? o1.getAs(RandomNameVariables::new) : null;
        this.relationsManager = o.getObject("relations_manager").getAs(RelationsManager::new); // TODO: check
        o1 = o.getObject("location");
        this.location = o1 != null ? o1.getAs(Property::new) : null;
        l = o.getList("species_mod_templates");
        this.speciesModTemplates = l != null ? l.getAsList(Species::new) : Lists.immutable.empty();
        this.customization = o.getString("customization");
        l = o.getList("seen_bypass_types");
        this.seenBypassTypes = l != null ? l.getAsStringList() : Lists.immutable.empty();
        // TODO: seen_bypasses
        l = o.getList("usable_bypasses");
        this.usableBypasses = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("hyperlane_systems");
        this.hyperlaneSystems = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        // TODO: owned_sectors, given_value
    }

    public Country(boolean customName, boolean autoShipDesigns, boolean hasAdvisor, boolean initialized, int colorIndex, int capital, int speciesIndex, int nextTransportFleetNumber, int ruler, int alliance, int startingSystem, double militaryPower, double fleetSize, String name, String adjective, String graphicalCulture, String cityGraphicalCulture, String room, String lastAllianceName, String personality, String rulerTitle, String nameList, String shipPrefix, String type, String customization, LocalDate lastDateWasHuman, LocalDate lastDateWarLost, LocalDate lastDateAtWar, LocalDate nextElection, LocalDate governmentDate, ImmutableIntList surveyed, ImmutableIntList visitedObjects, ImmutableIntList intelLevels, ImmutableIntList highestIntelLevels, ImmutableIntList sensorRangeFleets, ImmutableIntList ownedLeaders, ImmutableIntList ownedFleets, ImmutableIntList ownedMegastructures, ImmutableIntList ownedArmies, ImmutableIntList ownedPlanets, ImmutableIntList restrictedSystems, ImmutableIntList controlledPlanets, ImmutableIntList shipDesigns, ImmutableIntList usableBypasses, ImmutableIntList hyperlaneSystems, ImmutableList<String> policyFlags, ImmutableList<String> shownMessageTypes, ImmutableList<String> traditions, ImmutableList<String> ascensionPerks, ImmutableList<String> seenBypassTypes, ImmutableList<Intel> intel, ImmutableList<TimedModifier> timedModifiers, ImmutableList<ActivePolicy> activePolicies, ImmutableList<Edict> edicts, ImmutableList<RegnalNumber> regnalNumbers, ImmutableList<Species> speciesModTemplates, Flag flag, TechStatus techStatus, CountryBudget budget, Events events, TerraIncognita terraIncognita, AI ai, Ethos ethos, FleetTemplateManager fleetTemplateManager, Government government, Flags flags, Variables variables, Faction faction, CountingList shipNames, ControlGroups controlGroups, Modules modules, RandomNameVariables randomNameVariables, RelationsManager relationsManager, Property location) {
        this.customName = customName;
        this.autoShipDesigns = autoShipDesigns;
        this.hasAdvisor = hasAdvisor;
        this.initialized = initialized;
        this.colorIndex = colorIndex;
        this.capital = capital;
        this.speciesIndex = speciesIndex;
        this.nextTransportFleetNumber = nextTransportFleetNumber;
        this.ruler = ruler;
        this.alliance = alliance;
        this.startingSystem = startingSystem;
        this.militaryPower = militaryPower;
        this.fleetSize = fleetSize;
        this.name = name;
        this.adjective = adjective;
        this.graphicalCulture = graphicalCulture;
        this.cityGraphicalCulture = cityGraphicalCulture;
        this.room = room;
        this.lastAllianceName = lastAllianceName;
        this.personality = personality;
        this.rulerTitle = rulerTitle;
        this.nameList = nameList;
        this.shipPrefix = shipPrefix;
        this.type = type;
        this.customization = customization;
        this.lastDateWasHuman = lastDateWasHuman;
        this.lastDateWarLost = lastDateWarLost;
        this.lastDateAtWar = lastDateAtWar;
        this.nextElection = nextElection;
        this.governmentDate = governmentDate;
        this.surveyed = surveyed;
        this.visitedObjects = visitedObjects;
        this.intelLevels = intelLevels;
        this.highestIntelLevels = highestIntelLevels;
        this.sensorRangeFleets = sensorRangeFleets;
        this.ownedLeaders = ownedLeaders;
        this.ownedFleets = ownedFleets;
        this.ownedMegastructures = ownedMegastructures;
        this.ownedArmies = ownedArmies;
        this.ownedPlanets = ownedPlanets;
        this.restrictedSystems = restrictedSystems;
        this.controlledPlanets = controlledPlanets;
        this.shipDesigns = shipDesigns;
        this.usableBypasses = usableBypasses;
        this.hyperlaneSystems = hyperlaneSystems;
        this.policyFlags = policyFlags;
        this.shownMessageTypes = shownMessageTypes;
        this.traditions = traditions;
        this.ascensionPerks = ascensionPerks;
        this.seenBypassTypes = seenBypassTypes;
        this.intel = intel;
        this.timedModifiers = timedModifiers;
        this.activePolicies = activePolicies;
        this.edicts = edicts;
        this.regnalNumbers = regnalNumbers;
        this.speciesModTemplates = speciesModTemplates;
        this.flag = flag;
        this.techStatus = techStatus;
        this.budget = budget;
        this.events = events;
        this.terraIncognita = terraIncognita;
        this.ai = ai;
        this.ethos = ethos;
        this.fleetTemplateManager = fleetTemplateManager;
        this.government = government;
        this.flags = flags;
        this.variables = variables;
        this.faction = faction;
        this.shipNames = shipNames;
        this.controlGroups = controlGroups;
        this.modules = modules;
        this.randomNameVariables = randomNameVariables;
        this.relationsManager = relationsManager;
        this.location = location;
    }

    public boolean isCustomName() {
        return customName;
    }

    public boolean isAutoShipDesigns() {
        return autoShipDesigns;
    }

    public boolean isHasAdvisor() {
        return hasAdvisor;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public int getCapital() {
        return capital;
    }

    public int getSpeciesIndex() {
        return speciesIndex;
    }

    public int getNextTransportFleetNumber() {
        return nextTransportFleetNumber;
    }

    public int getRuler() {
        return ruler;
    }

    public int getAlliance() {
        return alliance;
    }

    public int getStartingSystem() {
        return startingSystem;
    }

    public double getMilitaryPower() {
        return militaryPower;
    }

    public double getFleetSize() {
        return fleetSize;
    }

    public String getName() {
        return name;
    }

    public String getAdjective() {
        return adjective;
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

    public String getLastAllianceName() {
        return lastAllianceName;
    }

    public String getPersonality() {
        return personality;
    }

    public String getRulerTitle() {
        return rulerTitle;
    }

    public String getNameList() {
        return nameList;
    }

    public String getShipPrefix() {
        return shipPrefix;
    }

    public String getType() {
        return type;
    }

    public String getCustomization() {
        return customization;
    }

    public LocalDate getLastDateWasHuman() {
        return lastDateWasHuman;
    }

    public LocalDate getLastDateWarLost() {
        return lastDateWarLost;
    }

    public LocalDate getLastDateAtWar() {
        return lastDateAtWar;
    }

    public LocalDate getNextElection() {
        return nextElection;
    }

    public LocalDate getGovernmentDate() {
        return governmentDate;
    }

    public ImmutableIntList getSurveyed() {
        return surveyed;
    }

    public ImmutableIntList getVisitedObjects() {
        return visitedObjects;
    }

    public ImmutableIntList getIntelLevels() {
        return intelLevels;
    }

    public ImmutableIntList getHighestIntelLevels() {
        return highestIntelLevels;
    }

    public ImmutableIntList getSensorRangeFleets() {
        return sensorRangeFleets;
    }

    public ImmutableIntList getOwnedLeaders() {
        return ownedLeaders;
    }

    public ImmutableIntList getOwnedFleets() {
        return ownedFleets;
    }

    public ImmutableIntList getOwnedMegastructures() {
        return ownedMegastructures;
    }

    public ImmutableIntList getOwnedArmies() {
        return ownedArmies;
    }

    public ImmutableIntList getOwnedPlanets() {
        return ownedPlanets;
    }

    public ImmutableIntList getRestrictedSystems() {
        return restrictedSystems;
    }

    public ImmutableIntList getControlledPlanets() {
        return controlledPlanets;
    }

    public ImmutableIntList getShipDesigns() {
        return shipDesigns;
    }

    public ImmutableIntList getUsableBypasses() {
        return usableBypasses;
    }

    public ImmutableIntList getHyperlaneSystems() {
        return hyperlaneSystems;
    }

    public ImmutableList<String> getPolicyFlags() {
        return policyFlags;
    }

    public ImmutableList<String> getShownMessageTypes() {
        return shownMessageTypes;
    }

    public ImmutableList<String> getTraditions() {
        return traditions;
    }

    public ImmutableList<String> getAscensionPerks() {
        return ascensionPerks;
    }

    public ImmutableList<String> getSeenBypassTypes() {
        return seenBypassTypes;
    }

    public ImmutableList<Intel> getIntel() {
        return intel;
    }

    public ImmutableList<TimedModifier> getTimedModifiers() {
        return timedModifiers;
    }

    public ImmutableList<ActivePolicy> getActivePolicies() {
        return activePolicies;
    }

    public ImmutableList<Edict> getEdicts() {
        return edicts;
    }

    public ImmutableList<RegnalNumber> getRegnalNumbers() {
        return regnalNumbers;
    }

    public ImmutableList<Species> getSpeciesModTemplates() {
        return speciesModTemplates;
    }

    public Flag getFlag() {
        return flag;
    }

    public TechStatus getTechStatus() {
        return techStatus;
    }

    public CountryBudget getBudget() {
        return budget;
    }

    public Events getEvents() {
        return events;
    }

    public TerraIncognita getTerraIncognita() {
        return terraIncognita;
    }

    public AI getAi() {
        return ai;
    }

    public Ethos getEthos() {
        return ethos;
    }

    public FleetTemplateManager getFleetTemplateManager() {
        return fleetTemplateManager;
    }

    public Government getGovernment() {
        return government;
    }

    public Flags getFlags() {
        return flags;
    }

    public Variables getVariables() {
        return variables;
    }

    public Faction getFaction() {
        return faction;
    }

    public CountingList getShipNames() {
        return shipNames;
    }

    public ControlGroups getControlGroups() {
        return controlGroups;
    }

    public Modules getModules() {
        return modules;
    }

    public RandomNameVariables getRandomNameVariables() {
        return randomNameVariables;
    }

    public RelationsManager getRelationsManager() {
        return relationsManager;
    }

    public Property getLocation() {
        return location;
    }
}
