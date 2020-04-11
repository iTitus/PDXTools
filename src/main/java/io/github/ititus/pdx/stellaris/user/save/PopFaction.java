package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class PopFaction {

    private final int country, leader;
    private final double support, factionApproval;
    private final String type, name;
    private final ImmutableIntList members;
    private final ImmutableList<Parameter> parameters;

    public PopFaction(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.country = o.getInt("country");
        this.type = o.getString("type");
        this.name = o.getString("name");
        this.leader = o.getInt("leader");
        this.parameters = o.getList("parameters").getAsList(Parameter::new);
        this.support = o.getDouble("support");
        this.factionApproval = o.getDouble("faction_approval");
        this.members = o.getList("members").getAsIntList();
    }

    public PopFaction(int country, int leader, double support, double factionApproval, String type, String name,
                      ImmutableIntList members, ImmutableList<Parameter> parameters) {
        this.country = country;
        this.leader = leader;
        this.support = support;
        this.factionApproval = factionApproval;
        this.type = type;
        this.name = name;
        this.members = members;
        this.parameters = parameters;
    }

    public int getCountry() {
        return country;
    }

    public int getLeader() {
        return leader;
    }

    public double getSupport() {
        return support;
    }

    public double getFactionApproval() {
        return factionApproval;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ImmutableIntList getMembers() {
        return members;
    }

    public ImmutableList<Parameter> getParameters() {
        return parameters;
    }
}
