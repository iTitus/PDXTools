package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.Date;
import java.util.List;

public class GameState {

    private final String version, name;
    private final int versionControlRevision;
    private final int tick;
    private final int randomLogDay;
    private final int lastCreatedSpecies;
    private final int lastCreatedPop;
    private final int lastCreatedCountry;
    private final long lastRefugeeCountry;
    private final int lastCreatedSystem;
    private final int lastCreatedFleet;
    private final int lastCreatedShip;
    private final int lastCreatedLeader;
    private final int lastCreatedArmy;
    private final int lastCreatedDesign;
    private final int lastCreatedAmbientObject;
    private final int lastDiploAction;
    private final int lastdNotificationId;
    private final int lastEventId;
    private final int lastCreatedPopFaction;
    private final int lastKilledCountryName;
    private final int randomCount;
    private final int randomSeed;
    private final Date date;
    private final List<String> requiredDLCs, usedColors;
    private final List<Player> players;
    private final List<Species> species;
    private final List<Nebula> nebulas;
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
    private final List<Integer> firedEvents, rimGalacticObjects;
    private final List<Long> usedSymbols;
    private final Wars wars;
    private final DebrisMap debrisMap;
    private final Missiles missiles;
    private final StrikeCrafts strikeCrafts;
    private final AmbientObjects ambientObjects;
    private final List<Message> messages;
    private final RandomNameDatabase randomNameDatabase;
    private final NameList nameList;
    private final Galaxy galaxy;
    private final double galaxyRadius;
    private final Flags flags;
    private final List<SavedEventTarget> savedEventTarget;
    private final ShipDesigns shipDesigns;
    private final PopFactions popFactions;
    private final MegaStructures megaStructures;
    private final Bypasses bypasses;
    private final NaturalWormholes naturalWormholes;
    private final List<GlobalShipDesign> globalShipDesigns;
    private final List<Cluster> clusters;
    private final List<AssetClass> usedSpeciesNames, usedSpeciesPortraits;

    GameState(PdxScriptObject o) {
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
        this.nebulas = o.getList("nebula").getAsList(Nebula::new);
        this.pops = o.getObject("pop").getAs(Pops::new);
        this.lastCreatedPop = o.getInt("last_created_pop", -1);
        this.galacticObjects = o.getObject("galactic_object").getAs(GalacticObjects::new);
        //...
        this.starbases = o.getObject("starbases").getAs(Starbases::new); // 73_165
        this.planets = o.getObject("planet").getAs(Planets::new); // 78_673
        this.countries = o.getObject("country").getAs(Countries::new); // 829_851
        this.alliances = o.getObject("alliance").getAs(Alliances::new); // 1_054_159
        this.truces = o.getObject("truce").getAs(Truces::new); // 1_054_190
        this.tradeDeals = o.getObject("trade_deal").getAs(TradeDeals::new); // 1_054_198
        this.lastCreatedCountry = o.getInt("last_created_country", -1); // 1_054_286
        this.lastRefugeeCountry = o.getLong("last_refugee_country", -1); // 1_054_287
        this.lastCreatedSystem = o.getInt("last_created_system", -1); // 1_054_288
        this.leaders = o.getObject("leaders").getAs(Leaders::new); // 1_054_289
        this.ships = o.getObject("ships").getAs(Ships::new); // 1_079_050
        this.fleets = o.getObject("fleet").getAs(Fleets::new); // 1_326_245
        this.fleetTemplates = o.getObject("fleet_template").getAs(FleetTemplates::new); // 1_580_777
        this.lastCreatedFleet = o.getInt("last_created_fleet", -1); // 1_583_973
        this.lastCreatedShip = o.getInt("last_created_ship", -1); // 1_583_974
        this.lastCreatedLeader = o.getInt("last_created_leader", -1); // 1_583_975
        this.lastCreatedArmy = o.getInt("last_created_army", -1); // 1_583_976
        this.lastCreatedDesign = o.getInt("last_created_design", -1); // 1_583_977
        this.armies = o.getObject("army").getAs(Armies::new); // 1_583_978
        this.groundCombats = o.getObject("ground_combat").getAs(GroundCombats::new); // 1_593_197
        this.firedEvents = o.getList("fired_events").getAsIntegerList(); // 1_593_200
        this.wars = o.getObject("war").getAs(Wars::new); // 1_593_203
        this.debrisMap = o.getObject("debris").getAs(DebrisMap::new); // 1_593_207
        this.missiles = o.getObject("missile").getAs(Missiles::new); // 1_593_390
        this.strikeCrafts = o.getObject("strike_craft").getAs(StrikeCrafts::new); // 1_593_429
        this.ambientObjects = o.getObject("ambient_object").getAs(AmbientObjects::new); // 1_593_447
        this.lastCreatedAmbientObject = o.getInt("last_created_ambient_object", -1); // 1_600_029
        this.messages = o.getList("message").getAsList(Message::new); // 1_600_030
        this.lastDiploAction = o.getInt("last_diplo_action_id", -1); // 1_600_147
        this.lastdNotificationId = o.getInt("last_notification_id", -1); // 1_600_148
        this.lastEventId = o.getInt("last_event_id", -1); // 1_600_149
        this.randomNameDatabase = o.getObject("random_name_database").getAs(RandomNameDatabase::new); // 1_600_150
        this.nameList = o.getObject("name_list").getAs(NameList::new); // 1_617_013
        this.galaxy = o.getObject("galaxy").getAs(Galaxy::new); // 1_617_015
        this.galaxyRadius = o.getDouble("galaxy_radius"); // 1_618_729
        this.flags = o.getObject("flags").getAs(Flags::new); // 1_618_730
        this.savedEventTarget = o.getList("saved_event_target").getAsList(SavedEventTarget::new); // 1_618_738
        this.shipDesigns = o.getObject("ship_design").getAs(ShipDesigns::new); // 1_618_998
        this.popFactions = o.getObject("pop_factions").getAs(PopFactions::new); // 1_737_170
        this.lastCreatedPopFaction = o.getInt("last_created_pop_faction", -1); // 1_737_964
        this.lastKilledCountryName = o.getInt("last_killed_country_name", -1); // 1_737_965
        this.megaStructures = o.getObject("megastructures").getAs(MegaStructures::new); // 1_737_966
        this.bypasses = o.getObject("bypasses").getAs(Bypasses::new); // 1_738_179
        this.naturalWormholes = o.getObject("natural_wormholes").getAs(NaturalWormholes::new); // 1_738_442
        this.globalShipDesigns = o.getList("global_ship_design").getAsList(GlobalShipDesign::new); // 1_738_540
        this.clusters = o.getList("clusters").getAsList(Cluster::new); // clusters Cluster 1_738_935
        this.rimGalacticObjects = o.getList("rim_galactic_objects").getAsIntegerList(); // 1_739_146
        this.usedColors = o.getList("used_color").getAsStringList(); // 1_739_149
        this.usedSymbols = o.getList("used_symbols").getAsLongList(); // 1_739_165
        this.usedSpeciesNames = o.getList("used_species_names").getAsList(AssetClass::new); // 1_739_168
        this.usedSpeciesPortraits = o.getList("used_species_portrait").getAsList(AssetClass::new); // 1_739_252
        this.randomSeed = o.getInt("random_seed"); // 1_739_336
        this.randomCount = o.getInt("random_count"); // 1_739_337
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
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

    public long getLastRefugeeCountry() {
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

    public int getLastdNotificationId() {
        return lastdNotificationId;
    }

    public int getLastEventId() {
        return lastEventId;
    }

    public int getLastCreatedPopFaction() {
        return lastCreatedPopFaction;
    }

    public int getLastKilledCountryName() {
        return lastKilledCountryName;
    }

    public int getRandomCount() {
        return randomCount;
    }

    public int getRandomSeed() {
        return randomSeed;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getRequiredDLCs() {
        return requiredDLCs;
    }

    public List<String> getUsedColors() {
        return usedColors;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Species> getSpecies() {
        return species;
    }

    public List<Nebula> getNebulas() {
        return nebulas;
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

    public List<Integer> getFiredEvents() {
        return firedEvents;
    }

    public List<Integer> getRimGalacticObjects() {
        return rimGalacticObjects;
    }

    public List<Long> getUsedSymbols() {
        return usedSymbols;
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

    public List<Message> getMessages() {
        return messages;
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

    public double getGalaxyRadius() {
        return galaxyRadius;
    }

    public Flags getFlags() {
        return flags;
    }

    public List<SavedEventTarget> getSavedEventTarget() {
        return savedEventTarget;
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

    public List<GlobalShipDesign> getGlobalShipDesigns() {
        return globalShipDesigns;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<AssetClass> getUsedSpeciesNames() {
        return usedSpeciesNames;
    }

    public List<AssetClass> getUsedSpeciesPortraits() {
        return usedSpeciesPortraits;
    }
}
