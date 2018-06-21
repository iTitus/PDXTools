package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

public class Species {

    private final boolean immortal, popsCanReproduce;
    private final int base, homePlanet;
    private final double popMaintenance, popsAutoGrowth;
    private final String nameList, name, plural, adjective, class_, portrait, nameData, popEthics;
    private final Traits traits;
    private final PopResourceRequirement newPopResourceRequirement;

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
        this.traits = o.getObject("traits").getAs(Traits::new);
        this.immortal = o.getBoolean("immortal");
        this.homePlanet = o.getInt("home_planet", -1);
        this.nameData = o.getString("name_data");
        this.popMaintenance = o.getDouble("pop_maintenance");
        this.popsCanReproduce = o.getBoolean("pops_can_reproduce");
        this.newPopResourceRequirement = o.getObject("new_pop_resource_requirement").getAs(PopResourceRequirement::new);
        this.popsAutoGrowth = o.getDouble("pops_auto_growth");
        this.popEthics = o.getString("pop_ethics");
    }

    public Species(boolean immortal, boolean popsCanReproduce, int base, int homePlanet, double popMaintenance, double popsAutoGrowth, String nameList, String name, String plural, String adjective, String class_, String portrait, String nameData, String popEthics, Traits traits, PopResourceRequirement newPopResourceRequirement) {
        this.immortal = immortal;
        this.popsCanReproduce = popsCanReproduce;
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
        this.popEthics = popEthics;
        this.traits = traits;
        this.newPopResourceRequirement = newPopResourceRequirement;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public boolean isPopsCanReproduce() {
        return popsCanReproduce;
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

    public String getPopEthics() {
        return popEthics;
    }

    public Traits getTraits() {
        return traits;
    }

    public PopResourceRequirement getNewPopResourceRequirement() {
        return newPopResourceRequirement;
    }
}
