package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
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
    public final PdxScriptObject modifier;
    public final ImmutableList<String> featureFlags;
    public final PdxScriptObject prereqforDesc;
    public final PdxScriptObject potential;
    public final PdxScriptObject weightModifier;
    public final PdxScriptObject aiWeight;
    public final ImmutableList<String> weightGroups;
    public final ImmutableObjectDoubleMap<String> modWeightIfGroupPicked;

    public Technology(IPdxScript s) {
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
        this.modifier = o.getNullOrObject("modifier");
        this.featureFlags = o.getListAsEmptyOrStringList("feature_flags");
        this.prereqforDesc = o.getNullOrObject("prereqfor_desc");
        this.potential = o.getNullOrObject("potential");
        this.weightModifier = o.getNullOrObject("weight_modifier");
        this.aiWeight = o.getNullOrObject("ai_weight");
        this.weightGroups = o.getListAsEmptyOrStringList("weight_groups");
        this.modWeightIfGroupPicked = o.getObjectAsEmptyOrStringDoubleMap("mod_weight_if_group_picked");
    }

    public enum Area {

        PHYSICS, SOCIETY, ENGINEERING;

        public static Area of(String name) {
            return switch (name) {
                case "physics" -> PHYSICS;
                case "society" -> SOCIETY;
                case "engineering" -> ENGINEERING;
                default -> throw new IllegalArgumentException("unknown area name " + name);
            };
        }
    }
}
