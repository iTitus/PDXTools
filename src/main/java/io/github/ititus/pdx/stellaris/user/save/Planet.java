package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.time.LocalDate;

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
            "pc_city",
            "pc_gaia",
            "pc_tomb",
            "pc_ringworld_habitable",
            "pc_habitat"
    );

    private final boolean customName, planetClassChanged, isMoon, hasRing, explicitEntity, surveyed, preventAnomaly;
    private final int planetSize, owner, originalOwner, controller, moonOf, builtArmies, shipClassOrbitalStation, entity, surveyedBy, nextBuildItemId;
    private final double orbit, bombardmentDamage;
    private final String name, planetClass, anomaly, entityName;
    private final LocalDate lastBombardment, colonizeDate, killPop;
    private final ImmutableIntList moons, pops, orbitals, armies;
    private final ImmutableList<String> planetModifiers;
    private final ImmutableList<BuildQueueItem> buildQueue;
    private final ImmutableList<TimedModifier> timedModifiers;
    private final Coordinate coordinate;
    private final Flags flags;
    private final Variables variables;

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
        PdxScriptList l = o.getList("moons");
        this.moons = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.planetClassChanged = o.getBoolean("planet_class_changed");
        this.owner = o.getInt("owner", -1);
        this.originalOwner = o.getInt("original_owner", -1);
        this.controller = o.getInt("controller", -1);
        this.moonOf = o.getInt("moon_of", -1);
        this.isMoon = o.getBoolean("is_moon");
        this.hasRing = o.getBoolean("has_ring");
        this.anomaly = o.getString("anomaly");
        l = o.getList("pop");
        this.pops = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        // TODO: building, ruined, disabled, district, last_building_changed, last_district_changed
        this.colonizeDate = o.getDate("colonize_date");
        this.killPop = o.getDate("kill_pop");
        this.buildQueue = o.getImplicitList("build_queue_item").getAsList(BuildQueueItem::new);
        l = o.getList("orbitals");
        this.orbitals = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("army");
        this.armies = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.builtArmies = o.getInt("built_armies");
        this.timedModifiers = o.getImplicitList("timed_modifier").getAsList(TimedModifier::new);
        this.shipClassOrbitalStation = o.getInt("shipclass_orbital_station", -1);
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::of) : null;
        o1 = o.getObject("variables");
        this.variables = o1 != null ? o1.getAs(Variables::new) : null;
        this.entity = o.getInt("entity", -1);
        this.planetModifiers = o.getImplicitList("planet_modifier").getAsStringList();
        this.entityName = o.getString("entity_name");
        this.explicitEntity = o.getBoolean("explicit_entity");
        this.surveyedBy = o.getInt("surveyed_by", -1);
        this.surveyed = o.getBoolean("surveyed");
        this.preventAnomaly = o.getBoolean("prevent_anomaly");
        this.nextBuildItemId = o.getInt("next_build_item_id");
        // TODO: deposits, growth, growth_species, job_priority, stability, migration, crime, amenities, amenities_usage, free_amenities, free_housing
    }

    public Planet(boolean customName, boolean planetClassChanged, boolean isMoon, boolean hasRing, boolean explicitEntity, boolean surveyed, boolean preventAnomaly, int planetSize, int owner, int originalOwner, int controller, int moonOf, int builtArmies, int shipClassOrbitalStation, int entity, int surveyedBy, int nextBuildItemId, double orbit, double bombardmentDamage, String name, String planetClass, String anomaly, String entityName, LocalDate lastBombardment, LocalDate colonizeDate, LocalDate killPop, ImmutableIntList moons, ImmutableIntList pops, ImmutableIntList orbitals, ImmutableIntList armies, ImmutableList<String> planetModifiers, ImmutableList<BuildQueueItem> buildQueue, ImmutableList<TimedModifier> timedModifiers, Coordinate coordinate, Flags flags, Variables variables) {
        this.customName = customName;
        this.planetClassChanged = planetClassChanged;
        this.isMoon = isMoon;
        this.hasRing = hasRing;
        this.explicitEntity = explicitEntity;
        this.surveyed = surveyed;
        this.preventAnomaly = preventAnomaly;
        this.planetSize = planetSize;
        this.owner = owner;
        this.originalOwner = originalOwner;
        this.controller = controller;
        this.moonOf = moonOf;
        this.builtArmies = builtArmies;
        this.shipClassOrbitalStation = shipClassOrbitalStation;
        this.entity = entity;
        this.surveyedBy = surveyedBy;
        this.nextBuildItemId = nextBuildItemId;
        this.orbit = orbit;
        this.bombardmentDamage = bombardmentDamage;
        this.name = name;
        this.planetClass = planetClass;
        this.anomaly = anomaly;
        this.entityName = entityName;
        this.lastBombardment = lastBombardment;
        this.colonizeDate = colonizeDate;
        this.killPop = killPop;
        this.moons = moons;
        this.pops = pops;
        this.orbitals = orbitals;
        this.armies = armies;
        this.planetModifiers = planetModifiers;
        this.buildQueue = buildQueue;
        this.timedModifiers = timedModifiers;
        this.coordinate = coordinate;
        this.flags = flags;
        this.variables = variables;
    }

    public static ImmutableSet<String> getHabitablePlanetClasses() {
        return habitablePlanetClasses;
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

    public double getOrbit() {
        return orbit;
    }

    public double getBombardmentDamage() {
        return bombardmentDamage;
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

    public LocalDate getLastBombardment() {
        return lastBombardment;
    }

    public LocalDate getColonizeDate() {
        return colonizeDate;
    }

    public LocalDate getKillPop() {
        return killPop;
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

    public ImmutableList<BuildQueueItem> getBuildQueue() {
        return buildQueue;
    }

    public ImmutableList<TimedModifier> getTimedModifiers() {
        return timedModifiers;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Flags getFlags() {
        return flags;
    }

    public Variables getVariables() {
        return variables;
    }
}
