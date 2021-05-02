package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.map.ImmutableMap;

import java.time.LocalDate;

public class Pop {

    public final int species;
    public final Ethos ethos;
    public final ImmutableMap<String, FlagData> flags;
    public final ImmutableList<TimedModifier> timedModifiers;
    public final boolean forceFactionEvaluation;
    public final int popFaction;
    public final boolean enslaved;
    public final String job;
    public final String formerJob;
    public final String category;
    public final boolean demotion;
    public final LocalDate demotionTime;
    public final LocalDate promotionDate;
    public final int planet;
    public final double crime;
    public final double power;
    public final double diplomaticWeight;
    public final double happiness;
    public final boolean canMigrate;
    public final double amenitiesUsage;
    public final double housingUsage;
    public final String approvalModifier;
    public final ImmutableIntList spawnedArmies;

    public Pop(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.species = o.getInt("species");
        this.ethos = o.getObjectAsNullOr("ethos", Ethos::new);
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.timedModifiers = o.getImplicitListAsList("timed_modifier", TimedModifier::new);
        this.forceFactionEvaluation = o.getBoolean("force_faction_evaluation", false);
        this.popFaction = o.getInt("pop_faction", -1);
        this.enslaved = o.getBoolean("enslaved", false);
        this.job = o.getString("job", null);
        this.formerJob = o.getString("former_job", null);
        this.category = o.getString("category");
        this.demotion = o.getBoolean("demotion", false);
        this.demotionTime = o.getDate("demotion_time", null);
        this.promotionDate = o.getDate("promotion_date", null);
        this.planet = o.getInt("planet");
        this.crime = o.getDouble("crime", 0);
        this.power = o.getDouble("power", 0);
        this.diplomaticWeight = o.getDouble("diplomatic_weight", 0);
        this.happiness = o.getDouble("happiness", 0);
        this.canMigrate = o.getBoolean("can_migrate");
        this.amenitiesUsage = o.getDouble("amenities_usage");
        this.housingUsage = o.getDouble("housing_usage");
        this.approvalModifier = o.getString("approval_modifier", null);
        this.spawnedArmies = o.getListAsEmptyOrIntList("spawned_armies");
    }

    public boolean hasModifier(String name) {
        return timedModifiers.anySatisfy(m -> name.equals(m.modifier));
    }
}
