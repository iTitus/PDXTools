package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.util.Date;

public class Planet {

    public static final ImmutableSet<String> habitablePlanetClasses = Sets.immutable.of(
            "pc_desert",
            "pc_tropical",
            "pc_arid",
            "pc_ocean",
            "pc_tundra",
            "pc_arctic",
            "pc_continental",
            "pc_alpine",
            "pc_savannah",
            "pc_nuked",
            "pc_machine",
            "pc_gaia",
            "pc_tomb",
            "pc_ringworld_habitable",
            "pc_habitat"
    );

    private final boolean customName, planetClassChanged, isMoon, hasRing, hasOwnerPops, hasOwnedPops, explicitEntity, surveyed, preventAnomaly;
    private final int planetSize, owner, originalOwner, controller, moonOf, builtArmies, immigratingPop, migratingPop, migratingTarget, shipClassOrbitalStation, entity, surveyedBy, nextBuildItemId;
    private final long colonyTile, orbitalDepositTile;
    private final double orbit, bombardmentDamage, unrest, alienSlavery, ownSpeciesSlavery, migratingProgress;
    private final String name, planetClass, anomaly, entityName, picture;
    private final Date lastBombardment, colonizeDate;
    private final ImmutableIntList moons, pops, orbitals, armies;
    private final ImmutableList<String> planetModifiers;
    private final ImmutableList<BuildingConstructionQueueItem> buildingConstructionQueue;
    private final ImmutableList<BuildQueueItem> buildQueue;
    private final ImmutableList<TimedModifier> timedModifiers;
    private final ImmutableList<DelayedEvent> delayedEvents;
    private final ImmutableList<Edict> edicts;
    private final Coordinate coordinate;
    private final Pop colonizerPop;
    private final Flags flags;
    private final Variables variables;
    private final Tiles tiles;
    private final Spaceport spaceport;

    public Planet(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.customName = o.getBoolean("custom_name");
        this.planetClass = o.getString("planet_class");
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.orbit = o.getDouble("orbit");
        this.planetSize = o.getInt("planet_size");
        this.bombardmentDamage = o.getDouble("bombardment_damage");
        this.lastBombardment = o.getDate("last_bombardment");
        this.unrest = o.getDouble("unrest");
        PdxScriptList l = o.getList("moons");
        this.moons = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.planetClassChanged = o.getBoolean("planet_class_changed");
        this.owner = o.getInt("owner", -1);
        this.originalOwner = o.getInt("original_owner", -1);
        this.controller = o.getInt("controller", -1);
        this.moonOf = o.getInt("moon_of", -1);
        this.isMoon = o.getBoolean("is_moon");
        this.hasRing = o.getBoolean("has_ring");
        PdxScriptObject o1 = o.getObject("colonizer_pop");
        this.colonizerPop = o1 != null ? o1.getAs(Pop::new) : null;
        this.anomaly = o.getString("anomaly");
        this.colonyTile = o.getLong("colony_tile", -1);
        l = o.getList("pop");
        this.pops = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.colonizeDate = o.getDate("colonize_date");
        l = o.getList("building_construction_queue");
        this.buildingConstructionQueue = l != null ? l.getAsList(BuildingConstructionQueueItem::new) : Lists.immutable.empty();
        this.buildQueue = o.getImplicitList("build_queue_item").getAsList(BuildQueueItem::new);
        l = o.getList("orbitals");
        this.orbitals = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("army");
        this.armies = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.builtArmies = o.getInt("built_armies");
        this.alienSlavery = o.getDouble("alien_slavery");
        this.ownSpeciesSlavery = o.getDouble("own_species_slavery");
        this.immigratingPop = o.getInt("immigrating_pop", -1);
        this.migratingPop = o.getInt("migrating_pop", -1);
        this.migratingTarget = o.getInt("migrating_target", -1);
        this.migratingProgress = o.getDouble("migrating_progress");
        this.timedModifiers = o.getImplicitList("timed_modifier").getAsList(TimedModifier::new);
        this.shipClassOrbitalStation = o.getInt("shipclass_orbital_station", -1);
        o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::of) : null;
        o1 = o.getObject("variables");
        this.variables = o1 != null ? o1.getAs(Variables::new) : null;
        this.delayedEvents = o.getImplicitList("delayed_event").getAsList(DelayedEvent::new);
        this.entity = o.getInt("entity", -1);
        this.planetModifiers = o.getImplicitList("planet_modifier").getAsStringList();
        this.hasOwnerPops = o.getBoolean("has_owner_pops");
        this.hasOwnedPops = o.getBoolean("has_owned_pops");
        this.entityName = o.getString("entity_name");
        this.explicitEntity = o.getBoolean("explicit_entity");
        this.picture = o.getString("picture");
        this.tiles = o.getObject("tiles").getAs(Tiles::new);
        this.spaceport = o.getObject("spaceport").getAs(Spaceport::of);
        l = o.getList("edicts");
        this.edicts = l != null ? l.getAsList(Edict::new) : Lists.immutable.empty();
        this.surveyedBy = o.getInt("surveyed_by", -1);
        this.surveyed = o.getBoolean("surveyed");
        this.preventAnomaly = o.getBoolean("prevent_anomaly");
        this.nextBuildItemId = o.getInt("next_build_item_id");
        this.orbitalDepositTile = o.getLong("orbital_deposit_tile");
    }

    public Planet(boolean customName, boolean planetClassChanged, boolean isMoon, boolean hasRing, boolean hasOwnerPops, boolean hasOwnedPops, boolean explicitEntity, boolean surveyed, boolean preventAnomaly, int planetSize, int owner, int originalOwner, int controller, int moonOf, int builtArmies, int immigratingPop, int migratingPop, int migratingTarget, int shipClassOrbitalStation, int entity, int surveyedBy, int nextBuildItemId, long colonyTile, long orbitalDepositTile, double orbit, double bombardmentDamage, double unrest, double alienSlavery, double ownSpeciesSlavery, double migratingProgress, String name, String planetClass, String anomaly, String entityName, String picture, Date lastBombardment, Date colonizeDate, ImmutableIntList moons, ImmutableIntList pops, ImmutableIntList orbitals, ImmutableIntList armies, ImmutableList<String> planetModifiers, ImmutableList<BuildingConstructionQueueItem> buildingConstructionQueue, ImmutableList<BuildQueueItem> buildQueue, ImmutableList<TimedModifier> timedModifiers, ImmutableList<DelayedEvent> delayedEvents, ImmutableList<Edict> edicts, Coordinate coordinate, Pop colonizerPop, Flags flags, Variables variables, Tiles tiles, Spaceport spaceport) {
        this.customName = customName;
        this.planetClassChanged = planetClassChanged;
        this.isMoon = isMoon;
        this.hasRing = hasRing;
        this.hasOwnerPops = hasOwnerPops;
        this.hasOwnedPops = hasOwnedPops;
        this.explicitEntity = explicitEntity;
        this.surveyed = surveyed;
        this.preventAnomaly = preventAnomaly;
        this.planetSize = planetSize;
        this.owner = owner;
        this.originalOwner = originalOwner;
        this.controller = controller;
        this.moonOf = moonOf;
        this.builtArmies = builtArmies;
        this.immigratingPop = immigratingPop;
        this.migratingPop = migratingPop;
        this.migratingTarget = migratingTarget;
        this.shipClassOrbitalStation = shipClassOrbitalStation;
        this.entity = entity;
        this.surveyedBy = surveyedBy;
        this.nextBuildItemId = nextBuildItemId;
        this.colonyTile = colonyTile;
        this.orbitalDepositTile = orbitalDepositTile;
        this.orbit = orbit;
        this.bombardmentDamage = bombardmentDamage;
        this.unrest = unrest;
        this.alienSlavery = alienSlavery;
        this.ownSpeciesSlavery = ownSpeciesSlavery;
        this.migratingProgress = migratingProgress;
        this.name = name;
        this.planetClass = planetClass;
        this.anomaly = anomaly;
        this.entityName = entityName;
        this.picture = picture;
        this.lastBombardment = lastBombardment;
        this.colonizeDate = colonizeDate;
        this.moons = moons;
        this.pops = pops;
        this.orbitals = orbitals;
        this.armies = armies;
        this.planetModifiers = planetModifiers;
        this.buildingConstructionQueue = buildingConstructionQueue;
        this.buildQueue = buildQueue;
        this.timedModifiers = timedModifiers;
        this.delayedEvents = delayedEvents;
        this.edicts = edicts;
        this.coordinate = coordinate;
        this.colonizerPop = colonizerPop;
        this.flags = flags;
        this.variables = variables;
        this.tiles = tiles;
        this.spaceport = spaceport;
    }

    public boolean isCustomName() {
        return customName;
    }

    public boolean isPlanetClassChanged() {
        return planetClassChanged;
    }

    public boolean isMoon() {
        return isMoon;
    }

    public boolean isHasRing() {
        return hasRing;
    }

    public boolean isHasOwnerPops() {
        return hasOwnerPops;
    }

    public boolean isHasOwnedPops() {
        return hasOwnedPops;
    }

    public boolean isExplicitEntity() {
        return explicitEntity;
    }

    public boolean isSurveyed() {
        return surveyed;
    }

    public boolean isPreventAnomaly() {
        return preventAnomaly;
    }

    public int getPlanetSize() {
        return planetSize;
    }

    public int getOwner() {
        return owner;
    }

    public int getOriginalOwner() {
        return originalOwner;
    }

    public int getController() {
        return controller;
    }

    public int getMoonOf() {
        return moonOf;
    }

    public int getBuiltArmies() {
        return builtArmies;
    }

    public int getImmigratingPop() {
        return immigratingPop;
    }

    public int getMigratingPop() {
        return migratingPop;
    }

    public int getMigratingTarget() {
        return migratingTarget;
    }

    public int getShipClassOrbitalStation() {
        return shipClassOrbitalStation;
    }

    public int getEntity() {
        return entity;
    }

    public int getSurveyedBy() {
        return surveyedBy;
    }

    public int getNextBuildItemId() {
        return nextBuildItemId;
    }

    public long getColonyTile() {
        return colonyTile;
    }

    public long getOrbitalDepositTile() {
        return orbitalDepositTile;
    }

    public double getOrbit() {
        return orbit;
    }

    public double getBombardmentDamage() {
        return bombardmentDamage;
    }

    public double getUnrest() {
        return unrest;
    }

    public double getAlienSlavery() {
        return alienSlavery;
    }

    public double getOwnSpeciesSlavery() {
        return ownSpeciesSlavery;
    }

    public double getMigratingProgress() {
        return migratingProgress;
    }

    public String getName() {
        return name;
    }

    public String getPlanetClass() {
        return planetClass;
    }

    public String getAnomaly() {
        return anomaly;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getPicture() {
        return picture;
    }

    public Date getLastBombardment() {
        return lastBombardment;
    }

    public Date getColonizeDate() {
        return colonizeDate;
    }

    public ImmutableIntList getMoons() {
        return moons;
    }

    public ImmutableIntList getPops() {
        return pops;
    }

    public ImmutableIntList getOrbitals() {
        return orbitals;
    }

    public ImmutableIntList getArmies() {
        return armies;
    }

    public ImmutableList<String> getPlanetModifiers() {
        return planetModifiers;
    }

    public ImmutableList<BuildingConstructionQueueItem> getBuildingConstructionQueue() {
        return buildingConstructionQueue;
    }

    public ImmutableList<BuildQueueItem> getBuildQueue() {
        return buildQueue;
    }

    public ImmutableList<TimedModifier> getTimedModifiers() {
        return timedModifiers;
    }

    public ImmutableList<DelayedEvent> getDelayedEvents() {
        return delayedEvents;
    }

    public ImmutableList<Edict> getEdicts() {
        return edicts;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Pop getColonizerPop() {
        return colonizerPop;
    }

    public Flags getFlags() {
        return flags;
    }

    public Variables getVariables() {
        return variables;
    }

    public Tiles getTiles() {
        return tiles;
    }

    public Spaceport getSpaceport() {
        return spaceport;
    }
}
