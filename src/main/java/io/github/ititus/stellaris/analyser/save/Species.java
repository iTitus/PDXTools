package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

public class Species {

    private final int base, homePlanet;
    private final String nameList, name, plural, adjective, class_, portrait, nameData, popEthics;
    private final Traits traits;
    private final boolean immortal, popsCanReproduce;
    private final double popMaintenance, popsAutoGrowth;
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

    public Species(int base, int homePlanet, String nameList, String name, String plural, String adjective, String class_, String portrait, String nameData, String popEthics, Traits traits, boolean immortal, boolean popsCanReproduce, double popMaintenance, double popsAutoGrowth, PopResourceRequirement newPopResourceRequirement) {
        this.base = base;
        this.homePlanet = homePlanet;
        this.nameList = nameList;
        this.name = name;
        this.plural = plural;
        this.adjective = adjective;
        this.class_ = class_;
        this.portrait = portrait;
        this.nameData = nameData;
        this.popEthics = popEthics;
        this.traits = traits;
        this.immortal = immortal;
        this.popsCanReproduce = popsCanReproduce;
        this.popMaintenance = popMaintenance;
        this.popsAutoGrowth = popsAutoGrowth;
        this.newPopResourceRequirement = newPopResourceRequirement;
    }

}
