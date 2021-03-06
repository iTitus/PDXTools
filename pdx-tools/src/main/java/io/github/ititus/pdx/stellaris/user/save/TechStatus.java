package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.game.common.technology.Technology;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectIntMap;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TechStatus {

    public final Map<String, Integer> technologies;
    public final ImmutableList<TechQueueItem> physicsQueue;
    public final ImmutableList<TechQueueItem> societyQueue;
    public final ImmutableList<TechQueueItem> engineeringQueue;
    public final ImmutableObjectDoubleMap<String> storedTechpointsForTech;
    public final ImmutableDoubleList storedTechpoints;
    public final TechAlternatives alternatives;
    public final ImmutableObjectIntMap<String> potential;
    public final TechLeaders leaders;
    public final ImmutableList<String> alwaysAvailableTech;
    public final boolean auto_researching_physics;
    public final boolean auto_researching_society;
    public final boolean auto_researching_engineering;
    public final String lastIncreasedTech;

    public TechStatus(PdxScriptObject o) {
        this.technologies = getTechnologies(o);
        this.physicsQueue = o.getListAsEmptyOrList("physics_queue", TechQueueItem::new);
        this.societyQueue = o.getListAsEmptyOrList("society_queue", TechQueueItem::new);
        this.engineeringQueue = o.getListAsEmptyOrList("engineering_queue", TechQueueItem::new);
        this.storedTechpointsForTech = o.getObjectAsEmptyOrStringDoubleMap("stored_techpoints_for_tech");
        this.storedTechpoints = o.getListAsEmptyOrDoubleList("stored_techpoints");
        this.alternatives = o.getObjectAs("alternatives", TechAlternatives::new);
        this.potential = o.getObjectAsEmptyOrStringIntMap("potential");
        this.leaders = o.getObjectAs("leaders", TechLeaders::new, null);
        this.alwaysAvailableTech = o.getImplicitListAsStringList("always_available_tech");
        this.auto_researching_physics = o.getBoolean("auto_researching_physics");
        this.auto_researching_society = o.getBoolean("auto_researching_society");
        this.auto_researching_engineering = o.getBoolean("auto_researching_engineering");
        this.lastIncreasedTech = o.getString("last_increased_tech", null);
    }

    private static Map<String, Integer> getTechnologies(PdxScriptObject o) {
        ImmutableList<String> technology = o.getImplicitListAsStringList("technology");
        ImmutableIntList level = o.getImplicitListAsIntList("level");
        if (technology.size() != level.size()) {
            throw new IllegalArgumentException("size mismatch between technologies and levels");
        }

        Map<String, Integer> technologies = new LinkedHashMap<>();
        for (int i = 0; i < technology.size(); i++) {
            String techName = technology.get(i);
            int techLevel = level.get(i);
            technologies.put(techName, techLevel);
        }

        return Collections.unmodifiableMap(technologies);
    }

    public boolean hasTech(String name) {
        return getTechLevel(name) > 0;
    }

    public int getTechLevel(String name) {
        Integer level = technologies.get(name);
        return level != null ? level : 0;
    }

    public TechQueueItem getCurrentlyResearchedTech(Technology.Area area) {
        ImmutableList<TechQueueItem> queue = switch (area) {
            case PHYSICS -> physicsQueue;
            case SOCIETY -> societyQueue;
            case ENGINEERING -> engineeringQueue;
        };
        for (TechQueueItem item : queue) {
            if (item != null && item.technology != null) {
                return item;
            }
        }

        return null;
    }
}
