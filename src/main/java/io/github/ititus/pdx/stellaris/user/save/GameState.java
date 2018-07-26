package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableList;
import io.github.ititus.pdx.util.collection.ViewableUnmodifiableArrayList;

import java.util.Date;
import java.util.List;

public class GameState {

    private final int versionControlRevision, tick, randomLogDay, lastCreatedSpecies, lastCreatedPop, lastCreatedCountry, lastRefugeeCountry, lastCreatedSystem, lastCreatedFleet, lastCreatedShip, lastCreatedLeader, lastCreatedArmy, lastCreatedDesign, lastCreatedAmbientObject, lastDiploAction, lastNotificationId, lastEventId, lastCreatedPopFaction, randomCount, randomSeed;
    private final double galaxyRadius;
    private final String version, name, lastKilledCountryName;
    private final Date date;
    private final ViewableList<Integer> firedEvents, rimGalacticObjects;
    private final ViewableList<Long> usedSymbols;
    private final ViewableList<String> requiredDLCs, usedColors;
    private final ViewableList<Player> players;
    private final ViewableList<Species> species;
    private final ViewableList<Nebula> nebulas;
    private final ViewableList<Message> messages;
    private final ViewableList<SavedEventTarget> savedEventTargets;
    private final ViewableList<GlobalShipDesign> globalShipDesigns;
    private final ViewableList<Cluster> clusters;
    private final ViewableList<UsedSpeciesClassAssets> usedSpeciesNames, usedSpeciesPortraits;
    private final Pops pops;
    private final GalacticObjects galacticObjects;
    private final Starbases starbases;
    private final Planets planets;
    private final Countries countries;
    private final Alliances alliances;
    private final Truces truces;
    private final TradeDeals tradeDeals;
    private final Leaders leaders;
    private final Ships ships;
    private final Fleets fleets;
    private final FleetTemplates fleetTemplates;
    private final Armies armies;
    private final GroundCombats groundCombats;
    private final Wars wars;
    private final DebrisMap debrisMap;
    private final Missiles missiles;
    private final StrikeCrafts strikeCrafts;
    private final AmbientObjects ambientObjects;
    private final RandomNameDatabase randomNameDatabase;
    private final NameList nameList;
    private final Galaxy galaxy;
    private final Flags flags;
    private final ShipDesigns shipDesigns;
    private final PopFactions popFactions;
    private final MegaStructures megaStructures;
    private final Bypasses bypasses;
    private final NaturalWormholes naturalWormholes;

    public GameState(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException();
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.version = o.getString("version");
        this.versionControlRevision = o.getInt("version_control_revision");
        this.name = o.getString("name");
        this.date = o.getDate("date");
        this.requiredDLCs = o.getList("required_dlcs").getAsStringList();
        this.players = o.getList("player").getAsList(Player::new);
        this.tick = o.getInt("tick");
        this.randomLogDay = o.getInt("random_log_day");
        this.species = o.getList("species").getAsList(Species::new);
        this.lastCreatedSpecies = o.getInt("last_created_species", -1);
        this.nebulas = o.getImplicitList("nebula").getAsList(Nebula::new);
        this.pops = o.getObject("pop").getAs(Pops::new);
        this.lastCreatedPop = o.getInt("last_created_pop", -1);
        this.galacticObjects = o.getObject("galactic_object").getAs(GalacticObjects::new);
        this.starbases = o.getObject("starbases").getAs(Starbases::new);
        this.planets = o.getObject("planet").getAs(Planets::new);
        //...
        this.countries = o.getObject("country").getAs(Countries::new); // 829_851
        // {
        this.alliances = o.getObject("alliance").getAs(Alliances::new);
        this.truces = o.getObject("truce").getAs(Truces::new);
        this.tradeDeals = o.getObject("trade_deal").getAs(TradeDeals::new);
        this.lastCreatedCountry = o.getInt("last_created_country", -1);
        this.lastRefugeeCountry = o.getUnsignedInt("last_refugee_country");
        this.lastCreatedSystem = o.getInt("last_created_system", -1);
        this.leaders = o.getObject("leaders").getAs(Leaders::new);
        this.ships = o.getObject("ships").getAs(Ships::new);
        // }
        this.fleets = o.getObject("fleet").getAs(Fleets::new); // 1_327_996
        // {
        this.fleetTemplates = o.getObject("fleet_template").getAs(FleetTemplates::new);
        this.lastCreatedFleet = o.getInt("last_created_fleet", -1);
        this.lastCreatedShip = o.getInt("last_created_ship", -1);
        this.lastCreatedLeader = o.getInt("last_created_leader", -1);
        this.lastCreatedArmy = o.getInt("last_created_army", -1);
        this.lastCreatedDesign = o.getInt("last_created_design", -1);
        this.armies = o.getObject("army").getAs(Armies::new);
        this.groundCombats = o.getObject("ground_combat").getAs(GroundCombats::new);
        this.firedEvents = o.getList("fired_events").getAsIntegerList();
        this.wars = o.getObject("war").getAs(Wars::new);
        this.debrisMap = o.getObject("debris").getAs(DebrisMap::new);
        this.missiles = o.getObject("missile").getAs(Missiles::new);
        this.strikeCrafts = o.getObject("strike_craft").getAs(StrikeCrafts::new);
        this.ambientObjects = o.getObject("ambient_object").getAs(AmbientObjects::new);
        this.lastCreatedAmbientObject = o.getInt("last_created_ambient_object", -1);
        // }
        this.messages = o.getImplicitList("message").getAsList(Message::new); // 1_606_983
        // {
        this.lastDiploAction = o.getInt("last_diplo_action_id", -1);
        this.lastNotificationId = o.getInt("last_notification_id", -1);
        this.lastEventId = o.getInt("last_event_id", -1);
        this.randomNameDatabase = o.getObject("random_name_database").getAs(RandomNameDatabase::new);
        this.nameList = o.getObject("name_list").getAs(NameList::new);
        // }
        this.galaxy = o.getObject("galaxy").getAs(Galaxy::new); // 1_623_968
        // {
        this.galaxyRadius = o.getDouble("galaxy_radius");
        this.flags = o.getObject("flags").getAs(Flags::new);
        this.savedEventTargets = o.getImplicitList("saved_event_target").getAsList(SavedEventTarget::new);
        this.shipDesigns = o.getObject("ship_design").getAs(ShipDesigns::new);
        this.popFactions = o.getObject("pop_factions").getAs(PopFactions::new);
        this.lastCreatedPopFaction = o.getInt("last_created_pop_faction", -1);
        this.lastKilledCountryName = o.getString("last_killed_country_name");
        this.megaStructures = o.getObject("megastructures").getAs(MegaStructures::new);
        this.bypasses = o.getObject("bypasses").getAs(Bypasses::new);
        this.naturalWormholes = o.getObject("natural_wormholes").getAs(NaturalWormholes::new);
        this.globalShipDesigns = o.getList("global_ship_design").getAsList(GlobalShipDesign::new);
        this.clusters = o.getList("clusters").getAsList(Cluster::new);
        this.rimGalacticObjects = o.getList("rim_galactic_objects").getAsIntegerList();
        this.usedColors = o.getImplicitList("used_color").getAsStringList();
        this.usedSymbols = o.getList("used_symbols").getAsLongList();
        this.usedSpeciesNames = o.getImplicitList("used_species_names").getAsList(UsedSpeciesClassAssets::new);
        this.usedSpeciesPortraits = o.getImplicitList("used_species_portrait").getAsList(UsedSpeciesClassAssets::new);
        this.randomSeed = o.getInt("random_seed");
        this.randomCount = o.getInt("random_count");
        // }
    }

    public GameState(int versionControlRevision, int tick, int randomLogDay, int lastCreatedSpecies, int lastCreatedPop, int lastCreatedCountry, int lastRefugeeCountry, int lastCreatedSystem, int lastCreatedFleet, int lastCreatedShip, int lastCreatedLeader, int lastCreatedArmy, int lastCreatedDesign, int lastCreatedAmbientObject, int lastDiploAction, int lastNotificationId, int lastEventId, int lastCreatedPopFaction, int randomCount, int randomSeed, double galaxyRadius, String version, String name, String lastKilledCountryName, Date date, List<Integer> firedEvents, List<Integer> rimGalacticObjects, List<Long> usedSymbols, List<String> requiredDLCs, List<String> usedColors, List<Player> players, List<Species> species, List<Nebula> nebulas, List<Message> messages, List<SavedEventTarget> savedEventTargets, List<GlobalShipDesign> globalShipDesigns, List<Cluster> clusters, List<UsedSpeciesClassAssets> usedSpeciesNames, List<UsedSpeciesClassAssets> usedSpeciesPortraits, Pops pops, GalacticObjects galacticObjects, Starbases starbases, Planets planets, Countries countries, Alliances alliances, Truces truces, TradeDeals tradeDeals, Leaders leaders, Ships ships, Fleets fleets, FleetTemplates fleetTemplates, Armies armies, GroundCombats groundCombats, Wars wars, DebrisMap debrisMap, Missiles missiles, StrikeCrafts strikeCrafts, AmbientObjects ambientObjects, RandomNameDatabase randomNameDatabase, NameList nameList, Galaxy galaxy, Flags flags, ShipDesigns shipDesigns, PopFactions popFactions, MegaStructures megaStructures, Bypasses bypasses, NaturalWormholes naturalWormholes) {
        this.versionControlRevision = versionControlRevision;
        this.tick = tick;
        this.randomLogDay = randomLogDay;
        this.lastCreatedSpecies = lastCreatedSpecies;
        this.lastCreatedPop = lastCreatedPop;
        this.lastCreatedCountry = lastCreatedCountry;
        this.lastRefugeeCountry = lastRefugeeCountry;
        this.lastCreatedSystem = lastCreatedSystem;
        this.lastCreatedFleet = lastCreatedFleet;
        this.lastCreatedShip = lastCreatedShip;
        this.lastCreatedLeader = lastCreatedLeader;
        this.lastCreatedArmy = lastCreatedArmy;
        this.lastCreatedDesign = lastCreatedDesign;
        this.lastCreatedAmbientObject = lastCreatedAmbientObject;
        this.lastDiploAction = lastDiploAction;
        this.lastNotificationId = lastNotificationId;
        this.lastEventId = lastEventId;
        this.lastCreatedPopFaction = lastCreatedPopFaction;
        this.randomCount = randomCount;
        this.randomSeed = randomSeed;
        this.galaxyRadius = galaxyRadius;
        this.version = version;
        this.name = name;
        this.lastKilledCountryName = lastKilledCountryName;
        this.date = date;
        this.firedEvents = new ViewableUnmodifiableArrayList<>(firedEvents);
        this.rimGalacticObjects = new ViewableUnmodifiableArrayList<>(rimGalacticObjects);
        this.usedSymbols = new ViewableUnmodifiableArrayList<>(usedSymbols);
        this.requiredDLCs = new ViewableUnmodifiableArrayList<>(requiredDLCs);
        this.usedColors = new ViewableUnmodifiableArrayList<>(usedColors);
        this.players = new ViewableUnmodifiableArrayList<>(players);
        this.species = new ViewableUnmodifiableArrayList<>(species);
        this.nebulas = new ViewableUnmodifiableArrayList<>(nebulas);
        this.messages = new ViewableUnmodifiableArrayList<>(messages);
        this.savedEventTargets = new ViewableUnmodifiableArrayList<>(savedEventTargets);
        this.globalShipDesigns = new ViewableUnmodifiableArrayList<>(globalShipDesigns);
        this.clusters = new ViewableUnmodifiableArrayList<>(clusters);
        this.usedSpeciesNames = new ViewableUnmodifiableArrayList<>(usedSpeciesNames);
        this.usedSpeciesPortraits = new ViewableUnmodifiableArrayList<>(usedSpeciesPortraits);
        this.pops = pops;
        this.galacticObjects = galacticObjects;
        this.starbases = starbases;
        this.planets = planets;
        this.countries = countries;
        this.alliances = alliances;
        this.truces = truces;
        this.tradeDeals = tradeDeals;
        this.leaders = leaders;
        this.ships = ships;
        this.fleets = fleets;
        this.fleetTemplates = fleetTemplates;
        this.armies = armies;
        this.groundCombats = groundCombats;
        this.wars = wars;
        this.debrisMap = debrisMap;
        this.missiles = missiles;
        this.strikeCrafts = strikeCrafts;
        this.ambientObjects = ambientObjects;
        this.randomNameDatabase = randomNameDatabase;
        this.nameList = nameList;
        this.galaxy = galaxy;
        this.flags = flags;
        this.shipDesigns = shipDesigns;
        this.popFactions = popFactions;
        this.megaStructures = megaStructures;
        this.bypasses = bypasses;
        this.naturalWormholes = naturalWormholes;
    }

    public int getVersionControlRevision() {
        return versionControlRevision;
    }

    public int getTick() {
        return tick;
    }

    public int getRandomLogDay() {
        return randomLogDay;
    }

    public int getLastCreatedSpecies() {
        return lastCreatedSpecies;
    }

    public int getLastCreatedPop() {
        return lastCreatedPop;
    }

    public int getLastCreatedCountry() {
        return lastCreatedCountry;
    }

    public int getLastRefugeeCountry() {
        return lastRefugeeCountry;
    }

    public int getLastCreatedSystem() {
        return lastCreatedSystem;
    }

    public int getLastCreatedFleet() {
        return lastCreatedFleet;
    }

    public int getLastCreatedShip() {
        return lastCreatedShip;
    }

    public int getLastCreatedLeader() {
        return lastCreatedLeader;
    }

    public int getLastCreatedArmy() {
        return lastCreatedArmy;
    }

    public int getLastCreatedDesign() {
        return lastCreatedDesign;
    }

    public int getLastCreatedAmbientObject() {
        return lastCreatedAmbientObject;
    }

    public int getLastDiploAction() {
        return lastDiploAction;
    }

    public int getLastNotificationId() {
        return lastNotificationId;
    }

    public int getLastEventId() {
        return lastEventId;
    }

    public int getLastCreatedPopFaction() {
        return lastCreatedPopFaction;
    }

    public int getRandomCount() {
        return randomCount;
    }

    public int getRandomSeed() {
        return randomSeed;
    }

    public double getGalaxyRadius() {
        return galaxyRadius;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getLastKilledCountryName() {
        return lastKilledCountryName;
    }

    public Date getDate() {
        return date;
    }

    public List<Integer> getFiredEvents() {
        return firedEvents.getView();
    }

    public List<Integer> getRimGalacticObjects() {
        return rimGalacticObjects.getView();
    }

    public List<Long> getUsedSymbols() {
        return usedSymbols.getView();
    }

    public List<String> getRequiredDLCs() {
        return requiredDLCs.getView();
    }

    public List<String> getUsedColors() {
        return usedColors.getView();
    }

    public List<Player> getPlayers() {
        return players.getView();
    }

    public List<Species> getSpecies() {
        return species.getView();
    }

    public List<Nebula> getNebulas() {
        return nebulas.getView();
    }

    public List<Message> getMessages() {
        return messages.getView();
    }

    public List<SavedEventTarget> getSavedEventTargets() {
        return savedEventTargets.getView();
    }

    public List<GlobalShipDesign> getGlobalShipDesigns() {
        return globalShipDesigns.getView();
    }

    public List<Cluster> getClusters() {
        return clusters.getView();
    }

    public List<UsedSpeciesClassAssets> getUsedSpeciesNames() {
        return usedSpeciesNames.getView();
    }

    public List<UsedSpeciesClassAssets> getUsedSpeciesPortraits() {
        return usedSpeciesPortraits.getView();
    }

    public Pops getPops() {
        return pops;
    }

    public GalacticObjects getGalacticObjects() {
        return galacticObjects;
    }

    public Starbases getStarbases() {
        return starbases;
    }

    public Planets getPlanets() {
        return planets;
    }

    public Countries getCountries() {
        return countries;
    }

    public Alliances getAlliances() {
        return alliances;
    }

    public Truces getTruces() {
        return truces;
    }

    public TradeDeals getTradeDeals() {
        return tradeDeals;
    }

    public Leaders getLeaders() {
        return leaders;
    }

    public Ships getShips() {
        return ships;
    }

    public Fleets getFleets() {
        return fleets;
    }

    public FleetTemplates getFleetTemplates() {
        return fleetTemplates;
    }

    public Armies getArmies() {
        return armies;
    }

    public GroundCombats getGroundCombats() {
        return groundCombats;
    }

    public Wars getWars() {
        return wars;
    }

    public DebrisMap getDebrisMap() {
        return debrisMap;
    }

    public Missiles getMissiles() {
        return missiles;
    }

    public StrikeCrafts getStrikeCrafts() {
        return strikeCrafts;
    }

    public AmbientObjects getAmbientObjects() {
        return ambientObjects;
    }

    public RandomNameDatabase getRandomNameDatabase() {
        return randomNameDatabase;
    }

    public NameList getNameList() {
        return nameList;
    }

    public Galaxy getGalaxy() {
        return galaxy;
    }

    public Flags getFlags() {
        return flags;
    }

    public ShipDesigns getShipDesigns() {
        return shipDesigns;
    }

    public PopFactions getPopFactions() {
        return popFactions;
    }

    public MegaStructures getMegaStructures() {
        return megaStructures;
    }

    public Bypasses getBypasses() {
        return bypasses;
    }

    public NaturalWormholes getNaturalWormholes() {
        return naturalWormholes;
    }
}
