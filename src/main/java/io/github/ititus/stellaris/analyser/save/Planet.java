package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptList;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptValue;
import io.github.ititus.stellaris.analyser.util.CollectionUtil;

import java.util.*;

public class Planet {

    public static final Set<String> habitablePlanetClasses = CollectionUtil.setOf(
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

    private final boolean customName, planetClassChanged, isMoon, hasOwnerPops, explicitEntity, surveyed, preventAnomaly;
    private final int planetSize, owner, originalOwner, controller, moonOf, builtArmies, shipClassOrbitalStation, entity, surveyedBy;
    private final long orbitalDepositTile;
    private final double orbit, bombardmentDamage;
    private final String name, planetClass, entityName;
    private final Date lastBombardment, colonizeDate;
    private final List<Integer> moons, pops, orbitals, armies;
    private final List<String> planetModifiers;
    private final List<BuildingConstructionQueueItem> buildingConstructionQueue;
    private final List<TimedModifier> timedModifiers;
    private final Coordinate coordinate;
    private final Flags flags;

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
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::new);
        this.orbit = o.getDouble("orbit");
        this.planetSize = o.getInt("planet_size");
        this.bombardmentDamage = o.getDouble("bombardment_damage");
        this.lastBombardment = o.getDate("last_bombardment");
        PdxScriptList l = o.getList("moons");
        this.moons = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        this.planetClassChanged = o.getBoolean("planet_class_changed");
        this.owner = o.getInt("owner", -1);
        this.originalOwner = o.getInt("original_owner", -1);
        this.controller = o.getInt("controller", -1);
        this.moonOf = o.getInt("moon_of", -1);
        this.isMoon = o.getBoolean("is_moon");
        l = o.getList("pop");
        this.pops = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        this.colonizeDate = o.getDate("colonize_date");
        l = o.getList("building_construction_queue");
        this.buildingConstructionQueue = l != null ? l.getAsList(BuildingConstructionQueueItem::new) : CollectionUtil.listOf();
        l = o.getList("orbitals");
        this.orbitals = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        l = o.getList("army");
        this.armies = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        this.builtArmies = o.getInt("built_armies");
        IPdxScript s1 = o.get("timed_modifier");
        if (s1 instanceof PdxScriptList) {
            this.timedModifiers = ((PdxScriptList) s1).getAsList(TimedModifier::new);
        } else if (s1 instanceof PdxScriptObject) {
            this.timedModifiers = new ArrayList<>(Collections.singleton(new TimedModifier(s1)));
        } else {
            this.timedModifiers = new ArrayList<>();
        }
        this.shipClassOrbitalStation = o.getInt("shipclass_orbital_station", -1);
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::new) : new Flags(Collections.emptyMap(), Collections.emptyMap());
        this.entity = o.getInt("entity", -1);
        //this.planetModifiers =
        s1 = o.get("planet_modifier");
        if (s1 instanceof PdxScriptList) {
            this.planetModifiers = ((PdxScriptList) s1).getAsStringList();
        } else if (s1 instanceof PdxScriptValue) {
            this.planetModifiers = new ArrayList<>(Collections.singleton((String) ((PdxScriptValue) s1).getValue()));
        } else {
            this.planetModifiers = new ArrayList<>();
        }
        this.hasOwnerPops = o.getBoolean("has_owner_pops");
        this.entityName = o.getString("entity_name");
        this.explicitEntity = o.getBoolean("explicit_entity");
        this.tiles = o.getObject("tiles").getAs(Tiles::new);
        this.spaceport = o.getObject("spaceport").getAs(Spaceport::new);
        this.surveyedBy = o.getInt("surveyed_by", -1);
        this.surveyed = o.getBoolean("surveyed");
        this.preventAnomaly = o.getBoolean("prevent_anomaly");
        this.orbitalDepositTile = o.getLong("orbital_deposit_tile");
    }

    public Planet(boolean customName, boolean planetClassChanged, boolean isMoon, boolean hasOwnerPops, boolean explicitEntity, boolean surveyed, boolean preventAnomaly, int planetSize, int owner, int originalOwner, int controller, int moonOf, int builtArmies, int shipClassOrbitalStation, int entity, int surveyedBy, long orbitalDepositTile, double orbit, double bombardmentDamage, String name, String planetClass, String entityName, Date lastBombardment, Date colonizeDate, Collection<Integer> moons, Collection<Integer> pops, Collection<Integer> orbitals, Collection<Integer> armies, Collection<String> planetModifiers, Collection<BuildingConstructionQueueItem> buildingConstructionQueue, Collection<TimedModifier> timedModifiers, Coordinate coordinate, Flags flags, Tiles tiles, Spaceport spaceport) {
        this.customName = customName;
        this.planetClassChanged = planetClassChanged;
        this.isMoon = isMoon;
        this.hasOwnerPops = hasOwnerPops;
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
        this.orbitalDepositTile = orbitalDepositTile;
        this.orbit = orbit;
        this.bombardmentDamage = bombardmentDamage;
        this.name = name;
        this.planetClass = planetClass;
        this.entityName = entityName;
        this.lastBombardment = new Date(lastBombardment.getTime());
        this.colonizeDate = new Date(colonizeDate.getTime());
        this.moons = new ArrayList<>(moons);
        this.pops = new ArrayList<>(pops);
        this.orbitals = new ArrayList<>(orbitals);
        this.armies = new ArrayList<>(armies);
        this.planetModifiers = new ArrayList<>(planetModifiers);
        this.buildingConstructionQueue = new ArrayList<>(buildingConstructionQueue);
        this.timedModifiers = new ArrayList<>(timedModifiers);
        this.coordinate = coordinate;
        this.flags = flags;
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

    public boolean isHasOwnerPops() {
        return hasOwnerPops;
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

    public long getOrbitalDepositTile() {
        return orbitalDepositTile;
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

    public String getEntityName() {
        return entityName;
    }

    public Date getLastBombardment() {
        return new Date(lastBombardment.getTime());
    }

    public Date getColonizeDate() {
        return new Date(colonizeDate.getTime());
    }

    public List<Integer> getMoons() {
        return Collections.unmodifiableList(moons);
    }

    public List<Integer> getPops() {
        return Collections.unmodifiableList(pops);
    }

    public List<Integer> getOrbitals() {
        return Collections.unmodifiableList(orbitals);
    }

    public List<Integer> getArmies() {
        return Collections.unmodifiableList(armies);
    }

    public List<String> getPlanetModifiers() {
        return Collections.unmodifiableList(planetModifiers);
    }

    public List<BuildingConstructionQueueItem> getBuildingConstructionQueue() {
        return Collections.unmodifiableList(buildingConstructionQueue);
    }

    public List<TimedModifier> getTimedModifiers() {
        return Collections.unmodifiableList(timedModifiers);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Flags getFlags() {
        return flags;
    }

    public Tiles getTiles() {
        return tiles;
    }

    public Spaceport getSpaceport() {
        return spaceport;
    }
}
