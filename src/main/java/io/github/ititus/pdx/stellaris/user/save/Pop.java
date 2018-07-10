package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Pop {

    private final boolean buildablePop, enslaved, forceFactionEvaluation, aiRightsServitude;
    private final int speciesIndex, popFaction, daysEnslaved;
    private final long tile, payingSector;
    private final double growth;
    private final String growthState;
    private final List<TimedModifier> timedModifiers;
    private final Ethos ethos;
    private final Flags flags;
    private final PopResourceRequirement requiredGrowth;
    private final Resources resources;

    public Pop(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.speciesIndex = o.getInt("species_index");
        this.growthState = o.getString("growth_state");
        this.growth = o.getDouble("growth");
        PdxScriptObject o1 = o.getObject("ethos");
        if (o1 != null) {
            this.ethos = o1.getAs(Ethos::new);
        } else {
            this.ethos = null;
        }
        o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::new) : new Flags(Collections.emptyMap(), Collections.emptyMap());
        this.buildablePop = o.getBoolean("buildable_pop");
        this.enslaved = o.getBoolean("enslaved");
        this.timedModifiers = o.getImplicitList("timed_modifier").getAsList(TimedModifier::new);
        this.tile = o.getLong("tile", -1);
        this.forceFactionEvaluation = o.getBoolean("force_faction_evaluation");
        this.popFaction = o.getInt("pop_faction", -1);
        this.requiredGrowth = o.getObject("required_growth").getAs(PopResourceRequirement::new);
        this.payingSector = o.getLong("paying_sector", -1);
        o1 = o.getObject("resources");
        this.resources = o1 != null ? o1.getAs(Resources::new) : new Resources();
        this.aiRightsServitude = o.getBoolean("ai_rights_servitude");
        this.daysEnslaved = o.getInt("days_enslaved");
    }

    public Pop(boolean buildablePop, boolean enslaved, boolean forceFactionEvaluation, boolean aiRightsServitude, int speciesIndex, int popFaction, int daysEnslaved, long tile, long payingSector, double growth, String growthState, Collection<TimedModifier> timedModifiers, Ethos ethos, Flags flags, PopResourceRequirement requiredGrowth, Resources resources) {
        this.buildablePop = buildablePop;
        this.enslaved = enslaved;
        this.forceFactionEvaluation = forceFactionEvaluation;
        this.aiRightsServitude = aiRightsServitude;
        this.speciesIndex = speciesIndex;
        this.popFaction = popFaction;
        this.daysEnslaved = daysEnslaved;
        this.tile = tile;
        this.payingSector = payingSector;
        this.growth = growth;
        this.growthState = growthState;
        this.timedModifiers = new ArrayList<>(timedModifiers);
        this.ethos = ethos;
        this.flags = flags;
        this.requiredGrowth = requiredGrowth;
        this.resources = resources;
    }

    public boolean isBuildablePop() {
        return buildablePop;
    }

    public boolean isEnslaved() {
        return enslaved;
    }

    public boolean isForceFactionEvaluation() {
        return forceFactionEvaluation;
    }

    public boolean isAiRightsServitude() {
        return aiRightsServitude;
    }

    public int getSpeciesIndex() {
        return speciesIndex;
    }

    public int getPopFaction() {
        return popFaction;
    }

    public int getDaysEnslaved() {
        return daysEnslaved;
    }

    public long getTile() {
        return tile;
    }

    public long getPayingSector() {
        return payingSector;
    }

    public double getGrowth() {
        return growth;
    }

    public String getGrowthState() {
        return growthState;
    }

    public List<TimedModifier> getTimedModifiers() {
        return Collections.unmodifiableList(timedModifiers);
    }

    public Ethos getEthos() {
        return ethos;
    }

    public Flags getFlags() {
        return flags;
    }

    public PopResourceRequirement getRequiredGrowth() {
        return requiredGrowth;
    }

    public Resources getResources() {
        return resources;
    }
}
