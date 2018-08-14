package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

public class Sector {

    private final int capital, leader;
    private final String type, name, mineralShare, energyShare;
    private final ImmutableIntList galacticObjects, fleets;
    private final SectorSettings settings;

    public Sector(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.galacticObjects = o.getList("galactic_object").getAsIntList();
        this.capital = o.getInt("capital");
        this.type = o.getString("type");
        this.name = o.getString("name");
        this.leader = o.getInt("leader", -1);
        this.mineralShare = o.getString("minerals_share");
        this.energyShare = o.getString("energy_share");
        PdxScriptList l = o.getList("fleets");
        this.fleets = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.settings = o.getObject("settings").getAs(SectorSettings::new);
    }

    public Sector(int capital, int leader, String type, String name, String mineralShare, String energyShare, ImmutableIntList galacticObjects, ImmutableIntList fleets, SectorSettings settings) {
        this.capital = capital;
        this.leader = leader;
        this.type = type;
        this.name = name;
        this.mineralShare = mineralShare;
        this.energyShare = energyShare;
        this.galacticObjects = galacticObjects;
        this.fleets = fleets;
        this.settings = settings;
    }

    public int getCapital() {
        return capital;
    }

    public int getLeader() {
        return leader;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getMineralShare() {
        return mineralShare;
    }

    public String getEnergyShare() {
        return energyShare;
    }

    public ImmutableIntList getGalacticObjects() {
        return galacticObjects;
    }

    public ImmutableIntList getFleets() {
        return fleets;
    }

    public SectorSettings getSettings() {
        return settings;
    }
}
