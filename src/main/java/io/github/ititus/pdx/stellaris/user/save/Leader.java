package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Date;

public class Leader {

    private final int speciesIndex, country, creator, level, leaderTerms, age, popFaction;
    private final double experience;
    private final String portrait, gender, leaderClass, preRulerLeaderClass, agenda;
    private final Date start, end, dateAdded, date;
    private final LeaderName name;
    private final Location location, preRulerLocation, targetCoordinate;
    private final Flags flags;
    private final Type mandate;
    private final LeaderDesign design;
    private final LeaderRoles roles;

    public Leader(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getObject("name").getAs(LeaderName::new);
        this.speciesIndex = o.getInt("species_index");
        this.portrait = o.getString("portrait");
        this.gender = o.getString("gender");
        this.country = o.getInt("country");
        this.creator = o.getInt("creator");
        this.leaderClass = o.getString("class");
        this.preRulerLeaderClass = o.getString("pre_ruler_class");
        this.experience = o.getDouble("experience");
        this.level = o.getInt("level");
        PdxScriptObject o1 = o.getObject("location");
        this.location = o1 != null ? o1.getAs(Location::of) : null;
        o1 = o.getObject("pre_ruler_location");
        this.preRulerLocation = o1 != null ? o1.getAs(Location::of) : null;
        o1 = o.getObject("target_coordinate");
        this.targetCoordinate = o1 != null ? o1.getAs(Location::of) : null;
        this.start = o.getDate("start");
        this.end = o.getDate("end");
        this.leaderTerms = o.getInt("leader_terms", 1);
        this.dateAdded = o.getDate("date_added");
        this.date = o.getDate("date");
        this.age = o.getInt("age");
        this.popFaction = o.getInt("pop_faction", -1);
        o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::of) : null;
        this.agenda = o.getString("agenda");
        o1 = o.getObject("mandate");
        this.mandate = o1 != null ? o1.getAs(Type::new) : null;
        o1 = o.getObject("design");
        this.design = o1 != null ? o1.getAs(LeaderDesign::new) : null;
        this.roles = o.getObject("roles").getAs(LeaderRoles::new);
    }

    public Leader(int speciesIndex, int country, int creator, int level, int leaderTerms, int age, int popFaction, double experience, String portrait, String gender, String leaderClass, String preRulerLeaderClass, String agenda, Date start, Date end, Date dateAdded, Date date, LeaderName name, Location location, Location preRulerLocation, Location targetCoordinate, Flags flags, Type mandate, LeaderDesign design, LeaderRoles roles) {
        this.speciesIndex = speciesIndex;
        this.country = country;
        this.creator = creator;
        this.level = level;
        this.leaderTerms = leaderTerms;
        this.age = age;
        this.popFaction = popFaction;
        this.experience = experience;
        this.portrait = portrait;
        this.gender = gender;
        this.leaderClass = leaderClass;
        this.preRulerLeaderClass = preRulerLeaderClass;
        this.agenda = agenda;
        this.start = start;
        this.end = end;
        this.dateAdded = dateAdded;
        this.date = date;
        this.name = name;
        this.location = location;
        this.preRulerLocation = preRulerLocation;
        this.targetCoordinate = targetCoordinate;
        this.flags = flags;
        this.mandate = mandate;
        this.design = design;
        this.roles = roles;
    }

    public int getSpeciesIndex() {
        return speciesIndex;
    }

    public int getCountry() {
        return country;
    }

    public int getCreator() {
        return creator;
    }

    public int getLevel() {
        return level;
    }

    public int getLeaderTerms() {
        return leaderTerms;
    }

    public int getAge() {
        return age;
    }

    public int getPopFaction() {
        return popFaction;
    }

    public double getExperience() {
        return experience;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getGender() {
        return gender;
    }

    public String getLeaderClass() {
        return leaderClass;
    }

    public String getPreRulerLeaderClass() {
        return preRulerLeaderClass;
    }

    public String getAgenda() {
        return agenda;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public Date getDate() {
        return date;
    }

    public LeaderName getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Location getPreRulerLocation() {
        return preRulerLocation;
    }

    public Location getTargetCoordinate() {
        return targetCoordinate;
    }

    public Flags getFlags() {
        return flags;
    }

    public Type getMandate() {
        return mandate;
    }

    public LeaderDesign getDesign() {
        return design;
    }

    public LeaderRoles getRoles() {
        return roles;
    }
}
