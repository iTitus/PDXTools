package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptList;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptValue;
import io.github.ititus.stellaris.analyser.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Planet {

    private final String name;
    private final boolean customName;
    private final String planetClass;
    private final Coordinate coordinate;
    private final double orbit;
    private final int planetSize;
    private final double bombardmentDamage;
    private final Date lastBombardment;
    private final List<Integer> moons;
    private final boolean planetClassChanged;
    private final int owner;
    private final int originalOwner;
    private final int controller;
    private final int moonOf;
    private final boolean isMoon;
    private final List<Integer> pops;
    private final Date colonizeDate;
    private final List<BuildingConstructionQueueItem> buildingConstructionQueue;
    private final List<Integer> orbitals;
    private final List<Integer> armies;
    private final int builtArmies;
    private final List<TimedModifier> timedModifiers;
    private final int shipClassOrbitalStation;
    private final Flags flags;
    private final int entity;
    private final List<String> planetModifiers;
    private final boolean hasOwnerPops;
    private final String entityName;
    private final boolean explicitEntity;
    private final Tiles tiles;
    private final Spaceport spaceport;
    private final int surveyedBy;
    private final boolean surveyed;
    private final boolean preventAnomaly;
    private final long orbitalDepositTile;

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
}
