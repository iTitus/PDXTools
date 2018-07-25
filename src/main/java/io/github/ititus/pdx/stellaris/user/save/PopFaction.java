package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableArrayList;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.Collection;
import java.util.List;

public class PopFaction {

    private final boolean modifierDirty;
    private final int country, leader;
    private final double support, happiness;
    private final String type, name;
    private final ViewableList<Integer> members;
    private final ViewableList<Parameter> parameters;
    private final ViewableList<TimedModifier> timedModifiers;

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
        this.members = o.getList("members").getAsIntegerList();
        this.modifierDirty = o.getBoolean("modifier_dirty");
    }

    public PopFaction(boolean modifierDirty, int country, int leader, double support, double happiness, String type, String name, Collection<Integer> members, Collection<Parameter> parameters, Collection<TimedModifier> timedModifiers) {
        this.modifierDirty = modifierDirty;
        this.country = country;
        this.leader = leader;
        this.support = support;
        this.happiness = happiness;
        this.type = type;
        this.name = name;
        this.members = new ViewableArrayList<>(members);
        this.parameters = new ViewableArrayList<>(parameters);
        this.timedModifiers = new ViewableArrayList<>(timedModifiers);
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

    public List<Integer> getMembers() {
        return members.getView();
    }

    public List<Parameter> getParameters() {
        return parameters.getView();
    }

    public List<TimedModifier> getTimedModifiers() {
        return timedModifiers.getView();
    }
}
