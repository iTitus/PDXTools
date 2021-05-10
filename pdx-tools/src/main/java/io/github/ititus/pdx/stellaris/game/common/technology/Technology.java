package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.localisation.ExternalLocalisable;
import io.github.ititus.pdx.shared.localisation.Localisable;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.common.technology.tier.TechnologyTier;
import io.github.ititus.pdx.stellaris.game.scope.CountryScope;
import io.github.ititus.pdx.stellaris.shared.Modifier;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;

import java.util.stream.Collectors;

import static io.github.ititus.pdx.pdxscript.PdxConstants.INDENT;

public record Technology(
        StellarisGame game,
        String name,
        int cost,
        Area area,
        int tier,
        String category,
        int levels,
        int costPerLevel,
        ImmutableList<String> prerequisites,
        double weight,
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
) implements Localisable {

    public static Technology of(StellarisGame game, String name, IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        int cost = o.getInt("cost", 0);
        Area area = o.getEnum("area", Area::of);
        int tier = o.getInt("tier");
        String category = o.getListAsStringList("category").getOnly();
        int levels = o.getInt("levels", 1);
        int costPerLevel = o.getInt("cost_per_level", 0);
        ImmutableList<String> prerequisites = o.getListAsEmptyOrStringList("prerequisites");
        double weight = o.getDouble("weight", 0);
        String gateway = o.getString("gateway", null);
        String aiUpdateType = o.getString("ai_update_type", null);
        boolean isRare = o.getBoolean("is_rare", false);
        boolean isDangerous = o.getBoolean("is_dangerous", false);
        boolean isReverseEngineerable = o.getBoolean("is_reverse_engineerable", true);
        boolean startTech = o.getBoolean("start_tech", false);
        Modifier modifier = o.getObjectAsNullOr("modifier", o_ -> new Modifier(game, o_));
        ImmutableList<String> featureFlags = o.getListAsEmptyOrStringList("feature_flags");
        TechnologyPreReqForDesc prereqforDesc = o.getObjectAsNullOr("prereqfor_desc", TechnologyPreReqForDesc::new);
        ImmutableList<Trigger> potential = o.getObjectAs("potential", game.triggers::create, Lists.immutable.empty());
        TechnologyWeightModifiers weightModifier = o.getObjectAsNullOr("weight_modifier", o_ -> TechnologyWeightModifiers.of(game, o_));
        TechnologyWeightModifiers aiWeight = o.getObjectAsNullOr("ai_weight", o_ -> TechnologyWeightModifiers.of(game, o_));
        ImmutableList<String> weightGroups = o.getListAsEmptyOrStringList("weight_groups");
        ImmutableObjectDoubleMap<String> modWeightIfGroupPicked = o.getObjectAsEmptyOrStringDoubleMap("mod_weight_if_group_picked");
        return new Technology(game, name, cost, area, tier, category, levels, costPerLevel, prerequisites, weight, gateway, aiUpdateType, isRare, isDangerous, isReverseEngineerable, startTech, modifier, featureFlags, prereqforDesc, potential, weightModifier, aiWeight, weightGroups, modWeightIfGroupPicked);
    }

    private static void localiseWeightModifiers(MutableList<String> list, String language, TechnologyWeightModifiers weightModifiers) {
        if (weightModifiers.factor() != 1) {
            list.add(" - Factor: " + weightModifiers.factor());
        }

        for (TechnologyWeightModifier m : weightModifiers.modifiers()) {
            if (m.factor() == 1 && m.add() == 0) {
                return;
            }

            list.add(" - Modifier:");
            if (m.factor() != 1) {
                list.add(INDENT + " - Factor: " + m.factor());
            }
            if (m.add() != 0) {
                list.add(INDENT + " - Add: " + m.add());
            }
            list.add(INDENT + " - Triggers:");
            list.addAllIterable(Trigger.localise(language, 2, m.triggers()));
        }
    }

    public boolean isRepeatable() {
        return levels != 1;
    }

    public boolean isInfinite() {
        return levels == -1;
    }

    public boolean hasPotential(CountryScope scope) {
        return Trigger.evaluateAnd(scope, potential);
    }

    public boolean hasAllPrerequisites(CountryScope cs) {
        return prerequisites.allSatisfy(cs.getCountry().techStatus::hasTech);
    }

    public boolean hasTierCondition(CountryScope cs) {
        TechnologyTier tier = cs.getGame().common.technologyTiers.tiers.get(this.tier);
        int required = tier.previouslyUnlocked;
        if (required <= 0) {
            return true;
        }

        int n = 0;
        for (String name : cs.getCountry().techStatus.technologies.keySet()) {
            Technology t = cs.getGame().common.technologies.get(name);
            if (t.area == area && t.tier == this.tier - 1 && ++n >= required) {
                return true;
            }
        }

        return false;
    }

    public double getBaseWeight() {
        return getWeight(null);
    }

    public double getWeight(CountryScope scope) {
        if (this.weight != 0 && weightModifier != null) {
            return weightModifier.getModifiedWeight(scope, this.weight);
        }

        return this.weight;
    }

    public int cost(int currentLevel) {
        return cost + currentLevel * costPerLevel;
    }


    public int cost(CountryScope cs) {
        return cost(cs.getCountry().techStatus.getTechLevel(name));
    }

    @Override
    public ImmutableList<String> localise(String language) {
        MutableList<String> list = Lists.mutable.of("Technology " + game.localisation.translate(language, name) + " (" + name + ")");
        list.add("Description: " + game.localisation.translate(language, name + "_desc"));
        list.add("Cost: " + cost);
        list.add("Area: " + area.localise(game.localisation, language).getOnly());
        list.add("Tier: " + tier);
        list.add("Category: " + category);
        if (isRepeatable()) {
            list.add("Repeatable: " + (isInfinite() ? "infinite" : levels));
            list.add("Cost per Level: " + costPerLevel);
        }
        if (prerequisites.notEmpty()) {
            list.add("Prerequisites: " + prerequisites.stream()
                    .map(tech -> game.localisation.translate(language, tech) + " (" + tech + ")")
                    .collect(Collectors.joining(", "))
            );
        }
        list.add("Weight: " + weight + (weightModifier != null ? " (Modified: " + getBaseWeight() + ")" : ""));
        if (gateway != null) {
            list.add("Gateway: " + gateway);
        }
        if (aiUpdateType != null) {
            list.add("AI Update Type: " + aiUpdateType);
        }
        MutableList<String> properties = Lists.mutable.empty();
        if (isRare) {
            properties.add("rare");
        }
        if (isDangerous) {
            properties.add("dangerous");
        }
        if (isReverseEngineerable) {
            properties.add("not reverse engineerable");
        }
        if (startTech) {
            properties.add("start tech");
        }
        if (properties.notEmpty()) {
            list.add("Properties: " + String.join(", ", properties));
        }
        if (modifier != null) {
            list.add("Modifiers:");
            if (modifier.description != null) {
                list.add(" - Description: " + game.localisation.translate(language, modifier.description.description, modifier.description.descriptionParameters));
            }
            if (modifier.customTooltip != null) {
                list.add(" - Custom Tooltip: " + game.localisation.translate(language, modifier.customTooltip));
            }
            modifier.modifiers.forEachKeyValue((k, v) -> list.add(" - " + game.localisation.translate(language, "mod_" + k) + ": " + v));
        }
        if (featureFlags.notEmpty()) {
            list.add("Feature Flags: " + String.join(", ", featureFlags));
        }
        if (prereqforDesc != null) {
            if (prereqforDesc.ship != null) {
                list.add("Prerequisite for ship: " + game.localisation.translate(language, prereqforDesc.ship.title));
            }
            if (prereqforDesc.component != null) {
                list.add("Prerequisite for component: " + game.localisation.translate(language, prereqforDesc.component.title));
            }
            if (prereqforDesc.custom.notEmpty()) {
                list.add("Prerequisite for: " + prereqforDesc.custom.stream().map(p -> game.localisation.translate(language, p.title)).collect(Collectors.joining(", ")));
            }
        }
        if (potential.notEmpty()) {
            list.add("Potential:");
            list.addAllIterable(Trigger.localise(language, potential));
        }
        if (weightModifier != null) {
            list.add("Weight Modifiers:");
            localiseWeightModifiers(list, language, weightModifier);
        }
        if (aiWeight != null) {
            list.add("AI Weight Modifiers:");
            localiseWeightModifiers(list, language, aiWeight);
        }
        if (weightGroups.notEmpty()) {
            list.add("Weight Groups: " + String.join(", ", weightGroups));
        }
        if (modWeightIfGroupPicked.notEmpty()) {
            list.add("Modifiers for Weight Groups:");
            modWeightIfGroupPicked.forEachKeyValue((k, v) -> list.add(" - " + k + "=" + v));
        }

        return list.toImmutable();
    }

    public enum Area implements ExternalLocalisable {

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

        @Override
        public ImmutableList<String> localise(PdxLocalisation localisation, String language) {
            return Lists.immutable.of(localisation.translate(name));
        }
    }
}
