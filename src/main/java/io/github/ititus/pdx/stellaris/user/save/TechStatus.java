package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectIntMap;
import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;
import org.eclipse.collections.impl.factory.primitive.ObjectIntMaps;

public class TechStatus {

    public final ImmutableObjectIntMap<String> technologies;
    public final ImmutableList<TechQueueItem> physicsQueue;
    public final ImmutableList<TechQueueItem> societyQueue;
    public final ImmutableList<TechQueueItem> engineeringQueue;
    public final ImmutableObjectDoubleMap<String> storedTechpointsForTech;
    public final ImmutableDoubleList storedTechpoints;
    public final TechAlternatives alternatives;
    public final ImmutableObjectIntMap<String> potential;
    public final TechLeaders leaders;
    public final ImmutableList<String> alwaysAvailableTech;
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
        this.lastIncreasedTech = o.getString("last_increased_tech", null);
    }

    private static ImmutableObjectIntMap<String> getTechnologies(PdxScriptObject o) {
        ImmutableList<String> technology = o.getImplicitListAsStringList("technology");
        ImmutableIntList level = o.getImplicitListAsIntList("level");
        if (technology.size() != level.size()) {
            throw new IllegalArgumentException("size mismatch between technologies and levels");
        }

        MutableObjectIntMap<String> technologies = ObjectIntMaps.mutable.empty();
        for (int i = 0; i < technology.size(); i++) {
            String techName = technology.get(i);
            int techLevel = level.get(i);
            technologies.put(techName, techLevel);
        }

        return technologies.toImmutable();
    }
}
