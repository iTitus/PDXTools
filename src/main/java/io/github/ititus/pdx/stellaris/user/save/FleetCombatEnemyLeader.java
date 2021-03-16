package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class FleetCombatEnemyLeader {

    public final int skill, skillCap;
    public final double experience;
    public final String name, leaderClass;
    public final ImmutableList<String> traits;
    public final LeaderDesign design;

    public FleetCombatEnemyLeader(PdxScriptObject o) {
        this.name = o.getString("name");
        this.experience = o.getDouble("experience");
        this.skill = o.getInt("skill");
        this.skillCap = o.getInt("skill_cap");
        this.leaderClass = o.getString("class");
        this.design = o.getObjectAs("design", LeaderDesign::new);
        this.traits = o.getImplicitListAsStringList("trait");
    }
}
