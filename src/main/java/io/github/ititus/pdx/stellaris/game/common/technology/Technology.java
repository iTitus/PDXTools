package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Technology {

    public final Area area;
    public final int cost;
    public final int costPerLevel;
    public final int level;
    public final int tier;
    public final int weight;
    public final boolean isRare;
    public final boolean isDangerous;
    public final boolean isReverseEngineerable;
    public final boolean startTech;
    public final String category;
    public final ImmutableList<String> prerequisites;
    public final String gateway;
    public final String aiUpdateType;

    public Technology(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.area = o.getEnum("area", Area::of);
        this.cost = o.getInt("cost", 0);
        this.costPerLevel = o.getInt("cost_per_level", 0);
        this.level = o.getInt("level", 1);
        this.isRare = o.getBoolean("is_rare", false);
        this.isDangerous = o.getBoolean("is_dangerous", false);
        this.isReverseEngineerable = o.getBoolean("is_reverse_engineerable", true);
        this.startTech = o.getBoolean("start_tech", false);
        this.tier = o.getInt("tier");
        this.weight = o.getInt("weight", 0);
        this.category = o.getListAsStringList("category").getOnly();
        this.prerequisites = o.getListAsEmptyOrStringList("prerequisites");
        this.gateway = o.getString("gateway", null);
        this.aiUpdateType = o.getString("ai_update_type", null);
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
