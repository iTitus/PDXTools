package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;

public class Species {

    private final boolean immortal, popsCanBeColonizers, popsCanMigrate, popsCanReproduce, popsCanJoinFactions, canGenerateLeaders, popsCanBeSlaves, popsHaveHappiness, consumerGoods;
    private final int base, homePlanet;
    private final double popMaintenance, popsAutoGrowth;
    private final String nameList, name, plural, adjective, class_, portrait, nameData, popsAutoUpgradeTo, buildablePop, popEthics;
    private final Traits traits;
    private final PopResourceRequirement newPopResourceRequirement;
    private final Flags flags;

    public Species(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.base = o.getInt("base", -1);
        this.nameList = o.getString("name_list");
        this.name = o.getString("name");
        this.plural = o.getString("plural");
        this.adjective = o.getString("adjective");
        this.class_ = o.getString("class");
        this.portrait = o.getString("portrait");
        this.traits = o.getObject("traits").getAs(Traits::of);
        this.immortal = o.getBoolean("immortal");
        this.homePlanet = o.getInt("home_planet", -1);
        this.nameData = o.getString("name_data");
        this.popMaintenance = o.getDouble("pop_maintenance");
        this.popsCanBeColonizers = o.getBoolean("pops_can_be_colonizers", true);
        this.popsCanMigrate = o.getBoolean("pops_can_migrate", true);
        this.popsCanReproduce = o.getBoolean("pops_can_reproduce", true);
        this.popsCanJoinFactions = o.getBoolean("pops_can_join_factions", true);
        this.canGenerateLeaders = o.getBoolean("can_generate_leaders", true);
        this.popsCanBeSlaves = o.getBoolean("pops_can_be_slaves", true);
        this.popsHaveHappiness = o.getBoolean("pops_have_happiness", true);
        this.consumerGoods = o.getBoolean("consumer_goods", true);
        this.newPopResourceRequirement = o.getObject("new_pop_resource_requirement").getAs(PopResourceRequirement::of);
        this.popsAutoGrowth = o.getDouble("pops_auto_growth");
        this.popsAutoUpgradeTo = o.getString("pops_auto_upgrade_to");
        this.buildablePop = o.getString("buildable_pop");
        IPdxScript s1 = o.get("pop_ethics");
        if (s1 instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s1).getValue();
            if (v instanceof String) {
                this.popEthics = (String) v;
                if (!"random".equals(this.popEthics)) {
                    throw new RuntimeException("Unexpected value for pop_ethics: " + this.popEthics);
                }
                o.use("pop_ethics", PdxConstants.STRING);
            } else if (v instanceof Boolean) {
                this.popEthics = (boolean) v ? PdxConstants.YES : PdxConstants.NO;
                o.use("pop_ethics", PdxConstants.BOOLEAN);
            } else {
                throw new RuntimeException("Unexpected value '" + v + "'for pop_ethics");
            }
        } else {
            throw new RuntimeException("Unexpected value '" + s1 + "' for pop_ethics");
        }
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::of) : null;
    }

    public Species(boolean immortal, boolean popsCanBeColonizers, boolean popsCanMigrate, boolean popsCanReproduce, boolean popsCanJoinFactions, boolean canGenerateLeaders, boolean popsCanBeSlaves, boolean popsHaveHappiness, boolean consumerGoods, int base, int homePlanet, double popMaintenance, double popsAutoGrowth, String nameList, String name, String plural, String adjective, String class_, String portrait, String nameData, String popsAutoUpgradeTo, String buildablePop, String popEthics, Traits traits, PopResourceRequirement newPopResourceRequirement, Flags flags) {
        this.immortal = immortal;
        this.popsCanBeColonizers = popsCanBeColonizers;
        this.popsCanMigrate = popsCanMigrate;
        this.popsCanReproduce = popsCanReproduce;
        this.popsCanJoinFactions = popsCanJoinFactions;
        this.canGenerateLeaders = canGenerateLeaders;
        this.popsCanBeSlaves = popsCanBeSlaves;
        this.popsHaveHappiness = popsHaveHappiness;
        this.consumerGoods = consumerGoods;
        this.base = base;
        this.homePlanet = homePlanet;
        this.popMaintenance = popMaintenance;
        this.popsAutoGrowth = popsAutoGrowth;
        this.nameList = nameList;
        this.name = name;
        this.plural = plural;
        this.adjective = adjective;
        this.class_ = class_;
        this.portrait = portrait;
        this.nameData = nameData;
        this.popsAutoUpgradeTo = popsAutoUpgradeTo;
        this.buildablePop = buildablePop;
        this.popEthics = popEthics;
        this.traits = traits;
        this.newPopResourceRequirement = newPopResourceRequirement;
        this.flags = flags;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public boolean isPopsCanBeColonizers() {
        return popsCanBeColonizers;
    }

    public boolean isPopsCanMigrate() {
        return popsCanMigrate;
    }

    public boolean isPopsCanReproduce() {
        return popsCanReproduce;
    }

    public boolean isPopsCanJoinFactions() {
        return popsCanJoinFactions;
    }

    public boolean isCanGenerateLeaders() {
        return canGenerateLeaders;
    }

    public boolean isPopsCanBeSlaves() {
        return popsCanBeSlaves;
    }

    public boolean isPopsHaveHappiness() {
        return popsHaveHappiness;
    }

    public boolean isConsumerGoods() {
        return consumerGoods;
    }

    public int getBase() {
        return base;
    }

    public int getHomePlanet() {
        return homePlanet;
    }

    public double getPopMaintenance() {
        return popMaintenance;
    }

    public double getPopsAutoGrowth() {
        return popsAutoGrowth;
    }

    public String getNameList() {
        return nameList;
    }

    public String getName() {
        return name;
    }

    public String getPlural() {
        return plural;
    }

    public String getAdjective() {
        return adjective;
    }

    public String getClass_() {
        return class_;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getNameData() {
        return nameData;
    }

    public String getPopsAutoUpgradeTo() {
        return popsAutoUpgradeTo;
    }

    public String getBuildablePop() {
        return buildablePop;
    }

    public String getPopEthics() {
        return popEthics;
    }

    public Traits getTraits() {
        return traits;
    }

    public PopResourceRequirement getNewPopResourceRequirement() {
        return newPopResourceRequirement;
    }

    public Flags getFlags() {
        return flags;
    }
}
