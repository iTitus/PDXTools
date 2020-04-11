package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Pop {

    private final int speciesIndex, popFaction, planet;
    private final double crime, power, happiness;
    private final String job, category;
    private final ImmutableList<TimedModifier> timedModifiers;
    private final Ethos ethos;
    private final Flags flags;

    public Pop(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.speciesIndex = o.getInt("species_index");
        PdxScriptObject o1 = o.getObject("ethos");
        this.ethos = o1 != null ? o1.getAs(Ethos::of) : null;
        o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::of) : null;
        this.timedModifiers = o.getImplicitList("timed_modifier").getAsList(TimedModifier::new);
        this.popFaction = o.getInt("pop_faction", -1);
        this.job = o.getString("job");
        // TODO: former_job
        this.category = o.getString("category");
        // TODO: demotion, demotion_time, promotion_date
        this.planet = o.getInt("planet");
        this.crime = o.getDouble("crime");
        this.power = o.getDouble("power");
        this.happiness = o.getDouble("happiness");
        // TODO: can_migrate, amenities_usage, housing_usage
    }

    public Pop(int speciesIndex, int popFaction, int planet, double crime, double power, double happiness, String job
            , String category, ImmutableList<TimedModifier> timedModifiers, Ethos ethos, Flags flags) {
        this.speciesIndex = speciesIndex;
        this.popFaction = popFaction;
        this.planet = planet;
        this.crime = crime;
        this.power = power;
        this.happiness = happiness;
        this.job = job;
        this.category = category;
        this.timedModifiers = timedModifiers;
        this.ethos = ethos;
        this.flags = flags;
    }

    public int getSpeciesIndex() {
        return speciesIndex;
    }

    public int getPopFaction() {
        return popFaction;
    }

    public int getPlanet() {
        return planet;
    }

    public double getCrime() {
        return crime;
    }

    public double getPower() {
        return power;
    }

    public double getHappiness() {
        return happiness;
    }

    public String getJob() {
        return job;
    }

    public String getCategory() {
        return category;
    }

    public ImmutableList<TimedModifier> getTimedModifiers() {
        return timedModifiers;
    }

    public Ethos getEthos() {
        return ethos;
    }

    public Flags getFlags() {
        return flags;
    }
}
