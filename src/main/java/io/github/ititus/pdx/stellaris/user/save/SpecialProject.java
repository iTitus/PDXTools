package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class SpecialProject {

    private final int id, daysLeft, debris, planet;
    private final String specialProject;
    private final LocalDate aiResearchDate;
    private final Scope scope;
    private final Property coordinate;

    public SpecialProject(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.id = o.getInt("id");
        this.daysLeft = o.getInt("days_left", -1);
        this.debris = o.getInt("debris", -1);
        this.specialProject = o.getString("special_project");
        PdxScriptObject o1 = o.getObject("scope");
        this.scope = o1 != null ? o1.getAs(Scope::new) : null;
        this.coordinate = o.getObject("coordinate").getAs(Property::new);
        this.planet = o.getInt("planet", -1);
        this.aiResearchDate = o.getDate("ai_research_date");
    }

    public SpecialProject(int id, int daysLeft, int debris, int planet, String specialProject, LocalDate aiResearchDate, Scope scope, Property coordinate) {
        this.id = id;
        this.daysLeft = daysLeft;
        this.debris = debris;
        this.planet = planet;
        this.specialProject = specialProject;
        this.aiResearchDate = aiResearchDate;
        this.scope = scope;
        this.coordinate = coordinate;
    }

    public int getId() {
        return id;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public int getDebris() {
        return debris;
    }

    public int getPlanet() {
        return planet;
    }

    public String getSpecialProject() {
        return specialProject;
    }

    public LocalDate getAiResearchDate() {
        return aiResearchDate;
    }

    public Scope getScope() {
        return scope;
    }

    public Property getCoordinate() {
        return coordinate;
    }
}
