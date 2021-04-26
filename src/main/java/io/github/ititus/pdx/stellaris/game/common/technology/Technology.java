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

public class Technology {

    public final int cost;
    public final Area area;
    public final int tier;
    public final String category;
    public final int levels;
    public final int costPerLevel;
    public final ImmutableList<String> prerequisites;
    public final int weight;
    public final String gateway;
    public final String aiUpdateType;
    public final boolean isRare;
    public final boolean isDangerous;
    public final boolean isReverseEngineerable;
    public final boolean startTech;
    public final Modifier modifier;
    public final ImmutableList<String> featureFlags;
    public final TechnologyPreReqForDesc prereqforDesc;
    public final ImmutableList<Trigger> potential;
    public final TechnologyWeightModifiers weightModifier;
    public final TechnologyWeightModifiers aiWeight;
    public final ImmutableList<String> weightGroups;
    public final ImmutableObjectDoubleMap<String> modWeightIfGroupPicked;

    private final StellarisGame game;

    public Technology(StellarisGame game, IPdxScript s) {
        this.game = game;
        PdxScriptObject o = s.expectObject();
        this.cost = o.getInt("cost", 0);
        this.area = o.getEnum("area", Area::of);
        this.tier = o.getInt("tier");
        this.category = o.getListAsStringList("category").getOnly();
        this.levels = o.getInt("levels", 1);
        this.costPerLevel = o.getInt("cost_per_level", 0);
        this.prerequisites = o.getListAsEmptyOrStringList("prerequisites");
        this.weight = o.getInt("weight", 0);
        this.gateway = o.getString("gateway", null);
        this.aiUpdateType = o.getString("ai_update_type", null);
        this.isRare = o.getBoolean("is_rare", false);
        this.isDangerous = o.getBoolean("is_dangerous", false);
        this.isReverseEngineerable = o.getBoolean("is_reverse_engineerable", true);
        this.startTech = o.getBoolean("start_tech", false);
        this.modifier = o.getObjectAsNullOr("modifier", Modifier::new);
        this.featureFlags = o.getListAsEmptyOrStringList("feature_flags");
        this.prereqforDesc = o.getObjectAsNullOr("prereqfor_desc", TechnologyPreReqForDesc::new);
        this.potential = o.getObjectAs("potential", game.triggers::create, Lists.immutable.empty());
        this.weightModifier = o.getObjectAsNullOr("weight_modifier", o_ -> new TechnologyWeightModifiers(game, o_));
        this.aiWeight = o.getObjectAsNullOr("ai_weight", o_ -> new TechnologyWeightModifiers(game, o_));
        this.weightGroups = o.getListAsEmptyOrStringList("weight_groups");
        this.modWeightIfGroupPicked = o.getObjectAsEmptyOrStringDoubleMap("mod_weight_if_group_picked");
    }

    public boolean hasPotential(Scope scope) {
        return Trigger.evaluateAnd(scope, potential);
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
