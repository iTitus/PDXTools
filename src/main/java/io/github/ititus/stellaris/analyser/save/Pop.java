package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptList;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Pop {

    private final int speciesIndex, popFaction, payingSector, daysEnslaved;
    private final String growthState;
    private final double growth;
    private final long tile;
    private final boolean buildablePop, enslaved, aiRightsServitude;
    private final Ethos ethos;
    private final Flags flags;
    private final PopResourceRequirement requiredGrowth;
    private final Resources resources;
    private final List<TimedModifier> timedModifiers;


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
        if (o1 != null) {
            this.flags = o1.getAs(Flags::new);
        } else {
            this.flags = null;
        }
        this.buildablePop = o.getBoolean("buildable_pop");
        this.enslaved = o.getBoolean("enslaved");
        IPdxScript s1 = o.get("timed_modifier");
        if (s1 instanceof PdxScriptList) {
            this.timedModifiers = ((PdxScriptList) s1).getAsList(TimedModifier::new);
        } else if (s1 instanceof PdxScriptObject) {
            this.timedModifiers = new ArrayList<>(Collections.singleton(new TimedModifier(s1)));
        } else {
            this.timedModifiers = new ArrayList<>();
        }
        this.tile = o.getLong("tile");
        this.popFaction = o.getInt("pop_faction", -1);
        this.requiredGrowth = o.getObject("required_growth").getAs(PopResourceRequirement::new);
        this.payingSector = o.getInt("paying_sector");
        o1 = o.getObject("resources");
        if (o1 != null) {
            this.resources = o1.getAs(Resources::new);
        } else {
            this.resources = null;
        }
        this.aiRightsServitude = o.getBoolean("ai_rights_servitude");
        this.daysEnslaved = o.getInt("days_enslaved");
    }

    public Pop(int speciesIndex, long tile, int popFaction, int payingSector, int daysEnslaved, String growthState, double growth, boolean buildablePop, boolean enslaved, boolean aiRightsServitude, Ethos ethos, Flags flags, PopResourceRequirement requiredGrowth, Resources resources, Collection<TimedModifier> timedModifiers) {
        this.speciesIndex = speciesIndex;
        this.tile = tile;
        this.popFaction = popFaction;
        this.payingSector = payingSector;
        this.daysEnslaved = daysEnslaved;
        this.growthState = growthState;
        this.growth = growth;
        this.buildablePop = buildablePop;
        this.enslaved = enslaved;
        this.aiRightsServitude = aiRightsServitude;
        this.ethos = ethos;
        this.flags = flags;
        this.requiredGrowth = requiredGrowth;
        this.resources = resources;
        this.timedModifiers = new ArrayList<>(timedModifiers);
    }
}
