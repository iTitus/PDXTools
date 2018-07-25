package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.ViewableArrayList;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Ship {

    private final boolean isBeingRepaired, createdThisUpdate, disabled, disabled_by_event, upgradable;
    private final int fleet, reserve, shipDesign, designUpgrade, army, nextWeaponIndex, leader, combatAction;
    private final double speed, experience, postMoveAngle, hitpoints, shieldHitpoints, armorHitpoints, maxHitpoints, maxShieldHitpoints, maxArmorHitpoints, rotation, forwardX, forwardY, upgradeProgress, disableAtHealth, enableAtHealth, targeting;
    private final String name, key, graphicalCulture;
    private final Date lastDamage;
    private final ViewableList<Aura> auras;
    private final ViewableList<ShipSection> sections;
    private final ViewableList<TimedModifier> timedModifiers;
    private final Coordinate coordinate, targetCoordinate;
    private final Flags flags;
    private final Homepop homepop;
    private final ShipFormationPos formationPos;
    private final Variables auraModifier;

    public Ship(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        PdxScriptList l = o.getList("auras");
        this.auras = l != null ? l.getAsList(Aura::new) : CollectionUtil.viewableListOf();
        this.isBeingRepaired = o.getBoolean("is_being_repaired");
        this.fleet = o.getInt("fleet");
        this.name = o.getString("name");
        this.key = o.getString("key");
        this.reserve = o.getInt("reserve", -1);
        this.shipDesign = o.getInt("ship_design");
        this.designUpgrade = o.getInt("design_upgrade", -1);
        this.graphicalCulture = o.getString("graphical_culture");
        this.sections = o.getImplicitList("section").getAsList(ShipSection::new);
        this.speed = o.getDouble("speed");
        this.experience = o.getDouble("experience");
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::new);
        this.targetCoordinate = o.getObject("target_coordinate").getAs(Coordinate::new);
        this.postMoveAngle = o.getDouble("post_move_angle");
        this.hitpoints = o.getDouble("hitpoints");
        this.shieldHitpoints = o.getDouble("shield_hitpoints");
        this.armorHitpoints = o.getDouble("armor_hitpoints");
        this.maxHitpoints = o.getDouble("max_hitpoints");
        this.maxShieldHitpoints = o.getDouble("max_shield_hitpoints");
        this.maxArmorHitpoints = o.getDouble("max_armor_hitpoints");
        this.rotation = o.getDouble("rotation");
        this.forwardX = o.getDouble("forward_x");
        this.forwardY = o.getDouble("forward_y");
        this.upgradeProgress = o.getDouble("upgrade_progress");
        this.army = o.getInt("army", -1);
        this.nextWeaponIndex = o.getInt("next_weapon_index");
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::new) : new Flags(Collections.emptyMap(), Collections.emptyMap());
        o1 = o.getObject("homepop");
        this.homepop = o1 != null ? o1.getAs(Homepop::new) : null;
        this.createdThisUpdate = o.getBoolean("created_this_update");
        this.leader = o.getInt("leader", -1);
        this.lastDamage = o.getDate("last_damage");
        this.timedModifiers = o.getImplicitList("timed_modifier").getAsList(TimedModifier::new);
        o1 = o.getObject("formation_pos");
        this.formationPos = o1 != null ? o1.getAs(ShipFormationPos::new) : null;
        this.combatAction = o.getInt("combat_action", -1);
        this.disabled = o.getBoolean("disabled");
        this.disabled_by_event = o.getBoolean("disabled_by_event");
        this.disableAtHealth = o.getDouble("disable_at_health", -1);
        this.enableAtHealth = o.getDouble("enable_at_health", -1);
        o1 = o.getObject("aura_modifier");
        this.auraModifier = o1 != null ? o1.getAs(Variables::new) : null;
        this.targeting = o.getDouble("targeting");
        this.upgradable = o.getBoolean("upgradable", true);
    }

    public Ship(boolean isBeingRepaired, boolean createdThisUpdate, boolean disabled, boolean disabled_by_event, boolean upgradable, int fleet, int reserve, int shipDesign, int designUpgrade, int army, int nextWeaponIndex, int leader, int combatAction, double speed, double experience, double postMoveAngle, double hitpoints, double shieldHitpoints, double armorHitpoints, double maxHitpoints, double maxShieldHitpoints, double maxArmorHitpoints, double rotation, double forwardX, double forwardY, double upgradeProgress, double disableAtHealth, double enableAtHealth, double targeting, String name, String key, String graphicalCulture, Date lastDamage, Collection<Aura> auras, Collection<ShipSection> sections, Collection<TimedModifier> timedModifiers, Coordinate coordinate, Coordinate targetCoordinate, Flags flags, Homepop homepop, ShipFormationPos formationPos, Variables auraModifier) {
        this.isBeingRepaired = isBeingRepaired;
        this.createdThisUpdate = createdThisUpdate;
        this.disabled = disabled;
        this.disabled_by_event = disabled_by_event;
        this.upgradable = upgradable;
        this.fleet = fleet;
        this.reserve = reserve;
        this.shipDesign = shipDesign;
        this.designUpgrade = designUpgrade;
        this.army = army;
        this.nextWeaponIndex = nextWeaponIndex;
        this.leader = leader;
        this.combatAction = combatAction;
        this.speed = speed;
        this.experience = experience;
        this.postMoveAngle = postMoveAngle;
        this.hitpoints = hitpoints;
        this.shieldHitpoints = shieldHitpoints;
        this.armorHitpoints = armorHitpoints;
        this.maxHitpoints = maxHitpoints;
        this.maxShieldHitpoints = maxShieldHitpoints;
        this.maxArmorHitpoints = maxArmorHitpoints;
        this.rotation = rotation;
        this.forwardX = forwardX;
        this.forwardY = forwardY;
        this.upgradeProgress = upgradeProgress;
        this.disableAtHealth = disableAtHealth;
        this.enableAtHealth = enableAtHealth;
        this.targeting = targeting;
        this.name = name;
        this.key = key;
        this.graphicalCulture = graphicalCulture;
        this.lastDamage = lastDamage;
        this.auras = new ViewableArrayList<>(auras);
        this.sections = new ViewableArrayList<>(sections);
        this.timedModifiers = new ViewableArrayList<>(timedModifiers);
        this.coordinate = coordinate;
        this.targetCoordinate = targetCoordinate;
        this.flags = flags;
        this.homepop = homepop;
        this.formationPos = formationPos;
        this.auraModifier = auraModifier;
    }

    public boolean isBeingRepaired() {
        return isBeingRepaired;
    }

    public boolean isCreatedThisUpdate() {
        return createdThisUpdate;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isDisabled_by_event() {
        return disabled_by_event;
    }

    public boolean isUpgradable() {
        return upgradable;
    }

    public int getFleet() {
        return fleet;
    }

    public int getReserve() {
        return reserve;
    }

    public int getShipDesign() {
        return shipDesign;
    }

    public int getDesignUpgrade() {
        return designUpgrade;
    }

    public int getArmy() {
        return army;
    }

    public int getNextWeaponIndex() {
        return nextWeaponIndex;
    }

    public int getLeader() {
        return leader;
    }

    public int getCombatAction() {
        return combatAction;
    }

    public double getSpeed() {
        return speed;
    }

    public double getExperience() {
        return experience;
    }

    public double getPostMoveAngle() {
        return postMoveAngle;
    }

    public double getHitpoints() {
        return hitpoints;
    }

    public double getShieldHitpoints() {
        return shieldHitpoints;
    }

    public double getArmorHitpoints() {
        return armorHitpoints;
    }

    public double getMaxHitpoints() {
        return maxHitpoints;
    }

    public double getMaxShieldHitpoints() {
        return maxShieldHitpoints;
    }

    public double getMaxArmorHitpoints() {
        return maxArmorHitpoints;
    }

    public double getRotation() {
        return rotation;
    }

    public double getForwardX() {
        return forwardX;
    }

    public double getForwardY() {
        return forwardY;
    }

    public double getUpgradeProgress() {
        return upgradeProgress;
    }

    public double getDisableAtHealth() {
        return disableAtHealth;
    }

    public double getEnableAtHealth() {
        return enableAtHealth;
    }

    public double getTargeting() {
        return targeting;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getGraphicalCulture() {
        return graphicalCulture;
    }

    public Date getLastDamage() {
        return lastDamage;
    }

    public List<Aura> getAuras() {
        return auras.getView();
    }

    public List<ShipSection> getSections() {
        return sections.getView();
    }

    public List<TimedModifier> getTimedModifiers() {
        return timedModifiers.getView();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Coordinate getTargetCoordinate() {
        return targetCoordinate;
    }

    public Flags getFlags() {
        return flags;
    }

    public Homepop getHomepop() {
        return homepop;
    }

    public ShipFormationPos getFormationPos() {
        return formationPos;
    }

    public Variables getAuraModifier() {
        return auraModifier;
    }
}
