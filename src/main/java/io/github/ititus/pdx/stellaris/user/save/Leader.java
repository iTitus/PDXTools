package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.ImmutableMap;

import java.time.LocalDate;

public class Leader {

    public final int species;
    public final int country;
    public final int creator;
    public final int level;
    public final int leaderTerms;
    public final int age;
    public final int popFaction;
    public final double experience;
    public final String portrait;
    public final String gender;
    public final String leaderClass;
    public final String preRulerLeaderClass;
    public final String agenda;
    public final LocalDate dateAdded;
    public final LocalDate date;
    public final LocalDate deathDate;
    public final LeaderName name;
    public final Location location;
    public final Location preRulerLocation;
    public final Location targetCoordinate;
    public final ImmutableMap<String, FlagData> flags;
    public final Type mandate;
    public final LeaderDesign design;
    public final boolean eventLeader;
    public final boolean immortal;
    public final int cooldown;
    public final LeaderRoles roles;

    public Leader(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getObjectAsNullOr("name", LeaderName::new);
        this.species = o.getInt("species");
        this.portrait = o.getString("portrait");
        this.gender = o.getString("gender", null);
        this.country = o.getInt("country", -1);
        this.creator = o.getInt("creator");
        this.leaderClass = o.getString("class");
        this.preRulerLeaderClass = o.getString("pre_ruler_class", null);
        this.experience = o.getDouble("experience", 0);
        this.level = o.getInt("level");
        this.location = o.getObjectAsNullOr("location", Location::new);
        this.preRulerLocation = o.getObjectAsNullOr("pre_ruler_location", Location::new);
        this.targetCoordinate = o.getObjectAsNullOr("target_coordinate", Location::new);
        this.leaderTerms = o.getInt("leader_terms", 1);
        this.dateAdded = o.getDate("date_added", null);
        this.date = o.getDate("date");
        this.deathDate = o.getDate("death_date", null);
        this.age = o.getInt("age");
        this.popFaction = o.getInt("pop_faction", -1);
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.agenda = o.getString("agenda");
        this.mandate = o.getObjectAsNullOr("mandate", Type::new);
        this.design = o.getObjectAsNullOr("design", LeaderDesign::new);
        this.eventLeader = o.getBoolean("event_leader", false);
        this.immortal = o.getBoolean("immortal", false);
        this.cooldown = o.getInt("cooldown", 0);
        this.roles = o.getObjectAs("roles", LeaderRoles::new);
    }

    public boolean hasTrait(String trait) {
        return roles.hasTrait(trait);
    }
}
