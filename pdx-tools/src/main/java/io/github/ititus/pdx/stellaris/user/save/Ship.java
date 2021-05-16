package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;

import java.time.LocalDate;

public class Ship {

    public final long id;
    public final boolean isBeingRepaired;
    public final boolean createdThisUpdate;
    public final boolean killed;
    public final boolean upgradable;
    public final int fleet;
    public final int reserve;
    public final int shipDesign;
    public final int designUpgrade;
    public final int army;
    public final int nextWeaponIndex;
    public final int leader;
    public final int combatAction;
    public final double speed;
    public final double experience;
    public final double postMoveAngle;
    public final double hitpoints;
    public final double shieldHitpoints;
    public final double armorHitpoints;
    public final double maxHitpoints;
    public final double maxShieldHitpoints;
    public final double maxArmorHitpoints;
    public final double rotation;
    public final double forwardX;
    public final double forwardY;
    public final double upgradeProgress;
    public final double disableAtHealth;
    public final double enableAtHealth;
    public final double targeting;
    public final String name;
    public final String key;
    public final String graphicalCulture;
    public final LocalDate lastDamage;
    public final ImmutableList<Aura> auras;
    public final ImmutableList<ShipSection> sections;
    public final ImmutableList<TimedModifier> timedModifiers;
    public final Coordinate coordinate, targetCoordinate;
    public final ImmutableMap<String, FlagData> flags;
    public final Homepop homepop;
    public final FormationPos formationPos;
    public final ImmutableObjectDoubleMap<String> auraModifier;

    public Ship(long id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
        this.auras = o.getListAsEmptyOrList("auras", Aura::new);
        this.isBeingRepaired = o.getBoolean("is_being_repaired", false);
        this.fleet = o.getUnsignedInt("fleet");
        this.name = o.getString("name", null);
        this.key = o.getString("key", null);
        this.reserve = o.getInt("reserve", -1);
        this.shipDesign = o.getInt("ship_design");
        this.designUpgrade = o.getInt("design_upgrade", -1);
        this.graphicalCulture = o.getString("graphical_culture");
        this.sections = o.getImplicitListAsList("section", ShipSection::new);
        this.speed = o.getDouble("speed", 0);
        this.experience = o.getDouble("experience", 0);
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.targetCoordinate = o.getObjectAs("target_coordinate", Coordinate::new);
        this.postMoveAngle = o.getDouble("post_move_angle");
        this.hitpoints = o.getDouble("hitpoints");
        this.shieldHitpoints = o.getDouble("shield_hitpoints", 0);
        this.armorHitpoints = o.getDouble("armor_hitpoints", 0);
        this.maxHitpoints = o.getDouble("max_hitpoints");
        this.maxShieldHitpoints = o.getDouble("max_shield_hitpoints", 0);
        this.maxArmorHitpoints = o.getDouble("max_armor_hitpoints", 0);
        this.rotation = o.getDouble("rotation");
        this.forwardX = o.getDouble("forward_x");
        this.forwardY = o.getDouble("forward_y");
        this.upgradeProgress = o.getDouble("upgrade_progress");
        this.army = o.getInt("army", -1);
        this.nextWeaponIndex = o.getInt("next_weapon_index", -1);
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.homepop = o.getObjectAsNullOr("homepop", Homepop::new);
        this.createdThisUpdate = o.getBoolean("created_this_update", true);
        this.killed = o.getBoolean("killed", false);
        this.leader = o.getInt("leader", -1);
        this.lastDamage = o.getDate("last_damage", null);
        this.timedModifiers = o.getImplicitListAsList("timed_modifier", TimedModifier::new);
        this.formationPos = o.getObjectAsNullOr("formation_pos", FormationPos::new);
        this.combatAction = o.getInt("combat_action", -1);
        this.disableAtHealth = o.getDouble("disable_at_health", -1);
        this.enableAtHealth = o.getDouble("enable_at_health", -1);
        this.auraModifier = o.getObjectAsEmptyOrStringDoubleMap("aura_modifier");
        this.targeting = o.getDouble("targeting", 0);
        this.upgradable = o.getBoolean("upgradable", true);
    }

    public boolean hasModifier(String name) {
        return timedModifiers.anySatisfy(m -> name.equals(m.modifier));
    }
}
