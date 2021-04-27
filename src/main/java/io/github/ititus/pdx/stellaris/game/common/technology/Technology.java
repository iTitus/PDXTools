package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.shared.Modifier;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;

public record Technology(
        StellarisGame game,
        int cost,
        Area area,
        int tier,
        String category,
        int levels,
        int costPerLevel,
        ImmutableList<String> prerequisites,
        int weight,
        String gateway,
        String aiUpdateType,
        boolean isRare,
        boolean isDangerous,
        boolean isReverseEngineerable,
        boolean startTech,
        Modifier modifier,
        ImmutableList<String> featureFlags,
        TechnologyPreReqForDesc prereqforDesc,
        ImmutableList<Trigger> potential,
        TechnologyWeightModifiers weightModifier,
        TechnologyWeightModifiers aiWeight,
        ImmutableList<String> weightGroups,
        ImmutableObjectDoubleMap<String> modWeightIfGroupPicked
) {

    public static Technology of(StellarisGame game, IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        int cost = o.getInt("cost", 0);
        Area area = o.getEnum("area", Area::of);
        int tier = o.getInt("tier");
        String category = o.getListAsStringList("category").getOnly();
        int levels = o.getInt("levels", 1);
        int costPerLevel = o.getInt("cost_per_level", 0);
        ImmutableList<String> prerequisites = o.getListAsEmptyOrStringList("prerequisites");
        int weight = o.getInt("weight", 0);
        String gateway = o.getString("gateway", null);
        String aiUpdateType = o.getString("ai_update_type", null);
        boolean isRare = o.getBoolean("is_rare", false);
        boolean isDangerous = o.getBoolean("is_dangerous", false);
        boolean isReverseEngineerable = o.getBoolean("is_reverse_engineerable", true);
        boolean startTech = o.getBoolean("start_tech", false);
        Modifier modifier = o.getObjectAsNullOr("modifier", Modifier::new);
        ImmutableList<String> featureFlags = o.getListAsEmptyOrStringList("feature_flags");
        TechnologyPreReqForDesc prereqforDesc = o.getObjectAsNullOr("prereqfor_desc", TechnologyPreReqForDesc::new);
        ImmutableList<Trigger> potential = o.getObjectAs("potential", game.triggers::create, Lists.immutable.empty());
        TechnologyWeightModifiers weightModifier = o.getObjectAsNullOr("weight_modifier", o_ -> new TechnologyWeightModifiers(game, o_));
        TechnologyWeightModifiers aiWeight = o.getObjectAsNullOr("ai_weight", o_ -> new TechnologyWeightModifiers(game, o_));
        ImmutableList<String> weightGroups = o.getListAsEmptyOrStringList("weight_groups");
        ImmutableObjectDoubleMap<String> modWeightIfGroupPicked = o.getObjectAsEmptyOrStringDoubleMap("mod_weight_if_group_picked");
        return new Technology(game, cost, area, tier, category, levels, costPerLevel, prerequisites, weight, gateway, aiUpdateType, isRare, isDangerous, isReverseEngineerable, startTech, modifier, featureFlags, prereqforDesc, potential, weightModifier, aiWeight, weightGroups, modWeightIfGroupPicked);
    }

    public boolean hasPotential(Scope scope) {
        return Trigger.evaluateAnd(scope, potential);
    }

    public double getBaseWeight() {
        return getWeight(null);
    }

    public double getWeight(Scope scope) {
        double weight = this.weight;
        if (weightModifier != null) {
            weight *= weightModifier.factor;

            if (scope != null) {
                for (TechnologyWeightModifier m : weightModifier.modifiers) {
                    if (Trigger.evaluateAnd(scope, m.triggers)) {
                        weight *= m.factor;
                        weight += m.add * this.weight;
                    }
                }
            }
        }

        return weight;
    }

    public enum Area {

        PHYSICS("physics"),
        SOCIETY("society"),
        ENGINEERING("engineering");

        private final String name;

        Area(String name) {
            this.name = name;
        }

        public static Area of(String name) {
            return switch (name) {
                case "physics" -> PHYSICS;
                case "society" -> SOCIETY;
                case "engineering" -> ENGINEERING;
                default -> throw new IllegalArgumentException("unknown area name " + name);
            };
        }

        public String getName() {
            return name;
        }
    }
}
