package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.*;

public class GameState {

    private final PdxScriptObject o;

    private final int versionControlRevision, tick, randomLogDay, lastCreatedSpecies, lastCreatedPop, lastCreatedCountry, lastCreatedSystem, lastCreatedFleet, lastCreatedShip, lastCreatedLeader, lastCreatedArmy, lastCreatedDesign, lastCreatedAmbientObject, lastDiploAction, lastNotificationId, lastEventId, lastCreatedPopFaction, randomCount, randomSeed;
    private final long lastRefugeeCountry;
    private final double galaxyRadius;
    private final String version, name, lastKilledCountryName;
    private final Date date;
    private final List<Integer> firedEvents, rimGalacticObjects;
    private final List<Long> usedSymbols;
    private final List<String> requiredDLCs, usedColors;
    private final List<Player> players;
    private final List<Species> species;
    private final List<Nebula> nebulas;
    private final List<Message> messages;
    private final List<SavedEventTarget> savedEventTarget;
    private final List<GlobalShipDesign> globalShipDesigns;
    private final List<Cluster> clusters;
    private final List<AssetClass> usedSpeciesNames, usedSpeciesPortraits;
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
        this.o = (PdxScriptObject) s;

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
        this.starbases = o.getObject("starbases").getAs(Starbases::new); // 73_165
        this.planets = o.getObject("planet").getAs(Planets::new); // 78_673
        //...
        this.countries = o.getObject("country").getAs(Countries::new); // 829_851
        // from here on the line numbers are wrong => countries got new data in 2.1.1
        this.alliances = o.getObject("alliance").getAs(Alliances::new); // 1_054_159 | 1_055_913
        this.truces = o.getObject("truce").getAs(Truces::new); // 1_054_190
        this.tradeDeals = o.getObject("trade_deal").getAs(TradeDeals::new); // 1_054_198
        // {
        this.lastCreatedCountry = o.getInt("last_created_country", -1); // 1_054_286
        this.lastRefugeeCountry = o.getLong("last_refugee_country", -1); // 1_054_287
        this.lastCreatedSystem = o.getInt("last_created_system", -1); // 1_054_288
        // }
        this.leaders = o.getObject("leaders").getAs(Leaders::new); // 1_054_289
        this.ships = o.getObject("ships").getAs(Ships::new); // 1_079_050
        this.fleets = o.getObject("fleet").getAs(Fleets::new); // 1_326_245
        this.fleetTemplates = o.getObject("fleet_template").getAs(FleetTemplates::new); // 1_580_777
        // {
        this.lastCreatedFleet = o.getInt("last_created_fleet", -1); // 1_583_973
        this.lastCreatedShip = o.getInt("last_created_ship", -1); // 1_583_974
        this.lastCreatedLeader = o.getInt("last_created_leader", -1); // 1_583_975
        this.lastCreatedArmy = o.getInt("last_created_army", -1); // 1_583_976
        this.lastCreatedDesign = o.getInt("last_created_design", -1); // 1_583_977
        // }
        this.armies = o.getObject("army").getAs(Armies::new); // 1_583_978
        this.groundCombats = o.getObject("ground_combat").getAs(GroundCombats::new); // 1_593_197
        // {
        this.firedEvents = o.getList("fired_events").getAsIntegerList(); // 1_593_200
        // }
        this.wars = o.getObject("war").getAs(Wars::new); // 1_593_203
        this.debrisMap = o.getObject("debris").getAs(DebrisMap::new); // 1_593_207
        this.missiles = o.getObject("missile").getAs(Missiles::new); // 1_593_390
        this.strikeCrafts = o.getObject("strike_craft").getAs(StrikeCrafts::new); // 1_593_429
        this.ambientObjects = o.getObject("ambient_object").getAs(AmbientObjects::new); // 1_593_447
        // {
        this.lastCreatedAmbientObject = o.getInt("last_created_ambient_object", -1); // 1_600_029
        // }
        this.messages = o.getList("message").getAsList(Message::new); // 1_600_030
        // {
        this.lastDiploAction = o.getInt("last_diplo_action_id", -1); // 1_600_147
        this.lastNotificationId = o.getInt("last_notification_id", -1); // 1_600_148
        this.lastEventId = o.getInt("last_event_id", -1); // 1_600_149
        // }
        this.randomNameDatabase = o.getObject("random_name_database").getAs(RandomNameDatabase::new); // 1_600_150
        this.nameList = o.getObject("name_list").getAs(NameList::new); // 1_617_013
        this.galaxy = o.getObject("galaxy").getAs(Galaxy::new); // 1_617_015
        // {
        this.galaxyRadius = o.getDouble("galaxy_radius"); // 1_618_729
        // }
        this.flags = o.getObject("flags").getAs(Flags::new); // 1_618_730
        this.savedEventTarget = o.getList("saved_event_target").getAsList(SavedEventTarget::new); // 1_618_738
        this.shipDesigns = o.getObject("ship_design").getAs(ShipDesigns::new); // 1_618_998
        this.popFactions = o.getObject("pop_factions").getAs(PopFactions::new); // 1_737_170
        // {
        this.lastCreatedPopFaction = o.getInt("last_created_pop_faction", -1); // 1_737_964
        this.lastKilledCountryName = o.getString("last_killed_country_name"); // 1_737_965
        // }
        this.megaStructures = o.getObject("megastructures").getAs(MegaStructures::new); // 1_737_966
        this.bypasses = o.getObject("bypasses").getAs(Bypasses::new); // 1_738_179
        this.naturalWormholes = o.getObject("natural_wormholes").getAs(NaturalWormholes::new); // 1_738_442
        this.globalShipDesigns = o.getList("global_ship_design").getAsList(GlobalShipDesign::new); // 1_738_540
        this.clusters = o.getList("clusters").getAsList(Cluster::new); // clusters Cluster 1_738_935
        // {
        this.rimGalacticObjects = o.getList("rim_galactic_objects").getAsIntegerList(); // 1_739_146
        this.usedColors = o.getList("used_color").getAsStringList(); // 1_739_149
        this.usedSymbols = o.getList("used_symbols").getAsLongList(); // 1_739_165
        // }
        this.usedSpeciesNames = o.getList("used_species_names").getAsList(AssetClass::new); // 1_739_168
        this.usedSpeciesPortraits = o.getList("used_species_portrait").getAsList(AssetClass::new); // 1_739_252
        // {
        this.randomSeed = o.getInt("random_seed"); // 1_739_336
        this.randomCount = o.getInt("random_count"); // 1_739_337
        // }
    }

    public GameState(int versionControlRevision, int tick, int randomLogDay, int lastCreatedSpecies, int lastCreatedPop, int lastCreatedCountry, int lastCreatedSystem, int lastCreatedFleet, int lastCreatedShip, int lastCreatedLeader, int lastCreatedArmy, int lastCreatedDesign, int lastCreatedAmbientObject, int lastDiploAction, int lastNotificationId, int lastEventId, int lastCreatedPopFaction, String lastKilledCountryName, int randomCount, int randomSeed, long lastRefugeeCountry, double galaxyRadius, String version, String name, Date date, Collection<Integer> firedEvents, Collection<Integer> rimGalacticObjects, Collection<Long> usedSymbols, Collection<String> requiredDLCs, Collection<String> usedColors, Collection<Player> players, Collection<Species> species, Collection<Nebula> nebulas, Collection<Message> messages, Collection<SavedEventTarget> savedEventTarget, Collection<GlobalShipDesign> globalShipDesigns, Collection<Cluster> clusters, Collection<AssetClass> usedSpeciesNames, Collection<AssetClass> usedSpeciesPortraits, Pops pops, GalacticObjects galacticObjects, Starbases starbases, Planets planets, Countries countries, Alliances alliances, Truces truces, TradeDeals tradeDeals, Leaders leaders, Ships ships, Fleets fleets, FleetTemplates fleetTemplates, Armies armies, GroundCombats groundCombats, Wars wars, DebrisMap debrisMap, Missiles missiles, StrikeCrafts strikeCrafts, AmbientObjects ambientObjects, RandomNameDatabase randomNameDatabase, NameList nameList, Galaxy galaxy, Flags flags, ShipDesigns shipDesigns, PopFactions popFactions, MegaStructures megaStructures, Bypasses bypasses, NaturalWormholes naturalWormholes) {
        this.o = null;

        this.versionControlRevision = versionControlRevision;
        this.tick = tick;
        this.randomLogDay = randomLogDay;
        this.lastCreatedSpecies = lastCreatedSpecies;
        this.lastCreatedPop = lastCreatedPop;
        this.lastCreatedCountry = lastCreatedCountry;
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
        this.lastKilledCountryName = lastKilledCountryName;
        this.randomCount = randomCount;
        this.randomSeed = randomSeed;
        this.lastRefugeeCountry = lastRefugeeCountry;
        this.galaxyRadius = galaxyRadius;
        this.version = version;
        this.name = name;
        this.date = new Date(date.getTime());
        this.firedEvents = new ArrayList<>(firedEvents);
        this.rimGalacticObjects = new ArrayList<>(rimGalacticObjects);
        this.usedSymbols = new ArrayList<>(usedSymbols);
        this.requiredDLCs = new ArrayList<>(requiredDLCs);
        this.usedColors = new ArrayList<>(usedColors);
        this.players = new ArrayList<>(players);
        this.species = new ArrayList<>(species);
        this.nebulas = new ArrayList<>(nebulas);
        this.messages = new ArrayList<>(messages);
        this.savedEventTarget = new ArrayList<>(savedEventTarget);
        this.globalShipDesigns = new ArrayList<>(globalShipDesigns);
        this.clusters = new ArrayList<>(clusters);
        this.usedSpeciesNames = new ArrayList<>(usedSpeciesNames);
        this.usedSpeciesPortraits = new ArrayList<>(usedSpeciesPortraits);
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

    public String getLastKilledCountryName() {
        return lastKilledCountryName;
    }

    public int getRandomCount() {
        return randomCount;
    }

    public int getRandomSeed() {
        return randomSeed;
    }

    public long getLastRefugeeCountry() {
        return lastRefugeeCountry;
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

    public Date getDate() {
        return new Date(date.getTime());
    }

    public List<Integer> getFiredEvents() {
        return Collections.unmodifiableList(firedEvents);
    }

    public List<Integer> getRimGalacticObjects() {
        return Collections.unmodifiableList(rimGalacticObjects);
    }

    public List<Long> getUsedSymbols() {
        return Collections.unmodifiableList(usedSymbols);
    }

    public List<String> getRequiredDLCs() {
        return Collections.unmodifiableList(requiredDLCs);
    }

    public List<String> getUsedColors() {
        return Collections.unmodifiableList(usedColors);
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public List<Species> getSpecies() {
        return Collections.unmodifiableList(species);
    }

    public List<Nebula> getNebulas() {
        return Collections.unmodifiableList(nebulas);
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public List<SavedEventTarget> getSavedEventTarget() {
        return Collections.unmodifiableList(savedEventTarget);
    }

    public List<GlobalShipDesign> getGlobalShipDesigns() {
        return Collections.unmodifiableList(globalShipDesigns);
    }

    public List<Cluster> getClusters() {
        return Collections.unmodifiableList(clusters);
    }

    public List<AssetClass> getUsedSpeciesNames() {
        return Collections.unmodifiableList(usedSpeciesNames);
    }

    public List<AssetClass> getUsedSpeciesPortraits() {
        return Collections.unmodifiableList(usedSpeciesPortraits);
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

    public Map<String, Set<String>> getErrors() {
        Map<String, Set<String>> errors = new HashMap<>();
        o.getErrors().forEach((k, v) -> errors.computeIfAbsent(k, k_ -> new HashSet<>()).addAll(v));
        return errors;
    }
}
