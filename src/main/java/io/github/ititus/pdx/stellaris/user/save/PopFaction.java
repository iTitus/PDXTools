package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class PopFaction {

    private final boolean modifierDirty;
    private final int country, leader;
    private final double support, happiness;
    private final String type, name;
    private final ImmutableIntList members;
    private final ImmutableList<Parameter> parameters;
    private final ImmutableList<TimedModifier> timedModifiers;

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
        this.happiness = o.getDouble("happiness");
        this.timedModifiers = o.getImplicitList("timed_modifier").getAsList(TimedModifier::new);
        this.members = o.getList("members").getAsIntList();
        this.modifierDirty = o.getBoolean("modifier_dirty");
    }

    public PopFaction(boolean modifierDirty, int country, int leader, double support, double happiness, String type, String name, ImmutableIntList members, ImmutableList<Parameter> parameters, ImmutableList<TimedModifier> timedModifiers) {
        this.modifierDirty = modifierDirty;
        this.country = country;
        this.leader = leader;
        this.support = support;
        this.happiness = happiness;
        this.type = type;
        this.name = name;
        this.members = members;
        this.parameters = parameters;
        this.timedModifiers = timedModifiers;
    }

    public boolean isModifierDirty() {
        return modifierDirty;
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

    public double getHappiness() {
        return happiness;
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

    public ImmutableList<TimedModifier> getTimedModifiers() {
        return timedModifiers;
    }
}
