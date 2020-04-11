package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Objects;

public class FleetCombatEnemyLeader {

    private final int skill, skillCap;
    private final double experience;
    private final String name, leaderClass;
    private final ImmutableList<String> traits;
    private final LeaderDesign design;

    public FleetCombatEnemyLeader(PdxScriptObject o) {
        this.name = o.getString("name");
        this.experience = o.getDouble("experience");
        this.skill = o.getInt("skill");
        this.skillCap = o.getInt("skill_cap");
        this.leaderClass = o.getString("class");
        this.design = o.getObject("design").getAs(LeaderDesign::new);
        this.traits = o.getImplicitList("trait").getAsStringList();
    }

    public FleetCombatEnemyLeader(int skill, int skillCap, double experience, String name, String leaderClass,
                                  ImmutableList<String> traits, LeaderDesign design) {
        this.skill = skill;
        this.skillCap = skillCap;
        this.experience = experience;
        this.name = name;
        this.leaderClass = leaderClass;
        this.traits = traits;
        this.design = design;
    }

    public int getSkill() {
        return skill;
    }

    public int getSkillCap() {
        return skillCap;
    }

    public double getExperience() {
        return experience;
    }

    public String getName() {
        return name;
    }

    public String getLeaderClass() {
        return leaderClass;
    }

    public ImmutableList<String> getTraits() {
        return traits;
    }

    public LeaderDesign getDesign() {
        return design;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FleetCombatEnemyLeader)) {
            return false;
        }
        FleetCombatEnemyLeader that = (FleetCombatEnemyLeader) o;
        return skill == that.skill && skillCap == that.skillCap && Double.compare(that.experience, experience) == 0 && Objects.equals(name, that.name) && Objects.equals(leaderClass, that.leaderClass) && Objects.equals(traits, that.traits) && Objects.equals(design, that.design);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill, skillCap, experience, name, leaderClass, traits, design);
    }
}
