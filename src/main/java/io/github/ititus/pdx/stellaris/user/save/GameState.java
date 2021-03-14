package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.ImmutableLongList;

import java.time.LocalDate;

public class GameState {

    public final boolean endGameCrisis;
    public final int versionControlRevision, tick, randomLogDay, lastCreatedSpecies, lastCreatedPop,
            lastCreatedCountry, lastRefugeeCountry, lastCreatedSystem, lastCreatedFleet, lastCreatedShip,
            lastCreatedLeader, lastCreatedArmy, lastCreatedDesign, lastCreatedAmbientObject, lastDiploAction,
            lastNotificationId, lastEventId, lastCreatedPopFaction, randomCount, randomSeed;
    public final double galaxyRadius;
    public final String version, name, lastKilledCountryName;
    public final LocalDate date;
    public final ImmutableIntList rimGalacticObjects;
    public final ImmutableLongList usedSymbols;
    public final ImmutableList<String> requiredDLCs, firedEventIds, usedColors;
    public final ImmutableList<Player> players;
    public final ImmutableList<Species> species;
    public final ImmutableList<Nebula> nebulas;
    public final ImmutableList<Message> messages;
    public final ImmutableList<SavedEventTarget> savedEventTargets;
    public final ImmutableList<GlobalShipDesign> globalShipDesigns;
    public final ImmutableList<Cluster> clusters;
    public final ImmutableList<UsedSpeciesClassAssets> usedSpeciesNames, usedSpeciesPortraits;
    public final Pops pops;
    public final GalacticObjects galacticObjects;
    public final StarbaseManager starbaseManager;
    public final Planets planets;
    public final Countries countries;
    public final Federations federations;
    public final Truces truces;
    public final TradeDeals tradeDeals;
    public final Leaders leaders;
    public final Ships ships;
    public final Fleets fleets;
    public final FleetTemplates fleetTemplates;
    public final Armies armies;
    public final Deposits deposits;
    public final GroundCombats groundCombats;
    public final Wars wars;
    public final DebrisMap debrisMap;
    public final Missiles missiles;
    public final StrikeCrafts strikeCrafts;
    public final AmbientObjects ambientObjects;
    public final RandomNameDatabase randomNameDatabase;
    public final Galaxy galaxy;
    public final Flags flags;
    public final ShipDesigns shipDesigns;
    public final PopFactions popFactions;
    public final MegaStructures megaStructures;
    public final Bypasses bypasses;
    public final NaturalWormholes naturalWormholes;
    public final TradeRoutes tradeRoutes;
    public final Sectors sectors;
    public final Buildings buildings;
    public final ArchaeologicalSites archaeologicalSites;

    public GameState(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
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
        this.starbaseManager = o.getObject("starbase_mgr").getAs(StarbaseManager::new);
        this.planets = o.getObject("planets").getAs(Planets::new);
        this.countries = o.getObject("country").getAs(Countries::new);
        // TODO: construction
        this.federations = o.getObject("federation").getAs(Federations::new);
        this.truces = o.getObject("truce").getAs(Truces::new);
        this.tradeDeals = o.getObject("trade_deal").getAs(TradeDeals::new);
        this.lastCreatedCountry = o.getInt("last_created_country", -1);
        this.lastRefugeeCountry = o.getUnsignedInt("last_refugee_country");
        this.lastCreatedSystem = o.getInt("last_created_system", -1);
        this.leaders = o.getObject("leaders").getAs(Leaders::new);
        // TODO: saved_leaders
        this.ships = o.getObject("ships").getAs(Ships::new);
        this.fleets = o.getObject("fleet").getAs(Fleets::new);
        this.fleetTemplates = o.getObject("fleet_template").getAs(FleetTemplates::new);
        this.lastCreatedFleet = o.getInt("last_created_fleet", -1);
        this.lastCreatedShip = o.getInt("last_created_ship", -1);
        this.lastCreatedLeader = o.getInt("last_created_leader", -1);
        this.lastCreatedArmy = o.getInt("last_created_army", -1);
        this.lastCreatedDesign = o.getInt("last_created_design", -1);
        this.armies = o.getObject("army").getAs(Armies::new);
        this.deposits = o.getObject("deposit").getAs(Deposits::new);
        this.groundCombats = o.getObject("ground_combat").getAs(GroundCombats::new);
        this.firedEventIds = o.getList("fired_event_ids").getAsStringList();
        this.wars = o.getObject("war").getAs(Wars::new);
        this.debrisMap = o.getObject("debris").getAs(DebrisMap::new);
        this.missiles = o.getObject("missile").getAs(Missiles::new);
        this.strikeCrafts = o.getObject("strike_craft").getAs(StrikeCrafts::new);
        this.ambientObjects = o.getObject("ambient_object").getAs(AmbientObjects::new);
        this.lastCreatedAmbientObject = o.getInt("last_created_ambient_object", -1);
        this.messages = o.getImplicitList("message").getAsList(Message::new);
        this.lastDiploAction = o.getInt("last_diplo_action_id", -1);
        this.lastNotificationId = o.getInt("last_notification_id", -1);
        this.lastEventId = o.getInt("last_event_id", -1);
        this.randomNameDatabase = o.getObject("random_name_database").getAs(RandomNameDatabase::new);
        PdxScriptObject o1 = o.getObject("name_list");
        if (o1 == null || o1.size() > 0) {
            throw new RuntimeException("Unexpected content '" + o1 + "'for name_list");
        }
        this.galaxy = o.getObject("galaxy").getAs(Galaxy::new);
        this.galaxyRadius = o.getDouble("galaxy_radius");
        this.flags = o.getObject("flags").getAs(Flags::of);
        this.savedEventTargets = o.getImplicitList("saved_event_target").getAsList(SavedEventTarget::new);
        this.shipDesigns = o.getObject("ship_design").getAs(ShipDesigns::new);
        this.popFactions = o.getObject("pop_factions").getAs(PopFactions::new);
        this.lastCreatedPopFaction = o.getInt("last_created_pop_faction", -1);
        this.lastKilledCountryName = o.getString("last_killed_country_name");
        this.megaStructures = o.getObject("megastructures").getAs(MegaStructures::new);
        this.bypasses = o.getObject("bypasses").getAs(Bypasses::new);
        this.naturalWormholes = o.getObject("natural_wormholes").getAs(NaturalWormholes::new);
        this.tradeRoutes = o.getObject("trade_routes").getAs(TradeRoutes::new);
        this.sectors = o.getObject("sectors").getAs(Sectors::new);
        this.buildings = o.getObject("buildings").getAs(Buildings::new);
        // TODO: resolution
        this.archaeologicalSites = o.getObject("archaeological_sites").getAs(ArchaeologicalSites::new);
        this.globalShipDesigns = o.getList("global_ship_design").getAsList(GlobalShipDesign::new);
        this.clusters = o.getList("clusters").getAsList(Cluster::new);
        this.rimGalacticObjects = o.getList("rim_galactic_objects").getAsIntList();
        this.endGameCrisis = o.getBoolean("end_game_crisis");
        this.usedColors = o.getImplicitList("used_color").getAsStringList();
        this.usedSymbols = o.getList("used_symbols").getAsLongList();
        this.usedSpeciesNames = o.getImplicitList("used_species_names").getAsList(UsedSpeciesClassAssets::new);
        this.usedSpeciesPortraits = o.getImplicitList("used_species_portrait").getAsList(UsedSpeciesClassAssets::new);
        this.randomSeed = o.getUnsignedInt("random_seed");
        this.randomCount = o.getInt("random_count");
        // TODO: market
        // TODO: trade_routes_manager
        // TODO: slave_market_manager
        // TODO: galactic_community
    }
}
