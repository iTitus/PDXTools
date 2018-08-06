package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Objects;

public class FleetCombatEnemyLeader {

    private final int skill, skillCap;
    private final double experience;
    private final String name, trait, leaderClass;
    private final LeaderDesign design;

    public FleetCombatEnemyLeader(PdxScriptObject o) {
        this.name = o.getString("name");
        this.experience = o.getDouble("experience");
        this.skill = o.getInt("skill");
        this.skillCap = o.getInt("skill_cap");
        this.leaderClass = o.getString("class");
        this.design = o.getObject("design").getAs(LeaderDesign::new);
        this.trait = o.getString("trait");
    }

    public FleetCombatEnemyLeader(int skill, int skillCap, double experience, String name, String trait, String leaderClass, LeaderDesign design) {
        this.skill = skill;
        this.skillCap = skillCap;
        this.experience = experience;
        this.name = name;
        this.trait = trait;
        this.leaderClass = leaderClass;
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

    public String getTrait() {
        return trait;
    }

    public String getLeaderClass() {
        return leaderClass;
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
        return skill == that.skill && skillCap == that.skillCap && Double.compare(that.experience, experience) == 0 && Objects.equals(name, that.name) && Objects.equals(trait, that.trait) && Objects.equals(leaderClass, that.leaderClass) && Objects.equals(design, that.design);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill, skillCap, experience, name, trait, leaderClass, design);
    }
}
