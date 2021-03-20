package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;

import java.time.LocalDate;

public class Planet {

    public final boolean customName;
    public final boolean planetClassChanged;
    public final boolean isMoon;
    public final boolean hasRing;
    public final boolean surveyed;
    public final boolean preventAnomaly;
    public final int planetSize;
    public final int owner;
    public final int originalOwner;
    public final int controller;
    public final int moonOf;
    public final int shipClassOrbitalStation;
    public final int entity;
    public final int surveyedBy;
    public final int buildQueue;
    public final double orbit;
    public final double bombardmentDamage;
    public final String name;
    public final String planetClass;
    public final String anomaly;
    public final String entityName;
    public final LocalDate lastBombardment, colonizeDate, killPop;
    public final ImmutableIntList moons;
    public final ImmutableIntList pops;
    public final ImmutableIntList armies;
    public final ImmutableList<String> planetModifiers;
    public final ImmutableList<TimedModifier> timedModifiers;
    public final Coordinate coordinate;
    public final ImmutableMap<String, FlagData> flags;
    public final ImmutableObjectDoubleMap<String> variables;

    public Planet(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.customName = o.getBoolean("custom_name", false);
        this.planetClass = o.getString("planet_class");
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.orbit = o.getDouble("orbit");
        this.planetSize = o.getInt("planet_size");
        this.bombardmentDamage = o.getDouble("bombardment_damage");
        this.lastBombardment = o.getDate("last_bombardment");
        this.moons = o.getListAsEmptyOrIntList("moons");
        this.planetClassChanged = o.getBoolean("planet_class_changed", false);
        this.owner = o.getInt("owner", -1);
        this.originalOwner = o.getInt("original_owner", -1);
        this.controller = o.getInt("controller", -1);
        this.moonOf = o.getInt("moon_of", -1);
        this.isMoon = o.getBoolean("is_moon", false);
        this.hasRing = o.getBoolean("has_ring", false);
        this.anomaly = o.getString("anomaly", null);
        this.pops = o.getListAsEmptyOrIntList("pop");
        this.colonizeDate = o.getDate("colonize_date", null);
        this.killPop = o.getDate("kill_pop");
        this.buildQueue = o.getInt("build_queue");
        this.armies = o.getListAsEmptyOrIntList("army");
        this.timedModifiers = o.getImplicitListAsList("timed_modifier", TimedModifier::new);
        this.shipClassOrbitalStation = o.getInt("shipclass_orbital_station", -1);
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.variables = o.getObjectAsEmptyOrStringDoubleMap("variables");
        this.entity = o.getInt("entity", -1);
        this.planetModifiers = o.getImplicitListAsStringList("planet_modifier");
        this.entityName = o.getString("entity_name", null);
        this.surveyedBy = o.getInt("surveyed_by", -1);
        this.surveyed = o.getBoolean("surveyed", false);
        this.preventAnomaly = o.getBoolean("prevent_anomaly", false);
    }
}
