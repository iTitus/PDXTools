package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class SpecialProject {

    public final int id;
    public final int daysLeft;
    public final int ambientObject;
    public final String specialProject;
    public final Scope scope;
    public final Property coordinate;
    public final int planet;
    public final LocalDate aiResearchDate;

    public SpecialProject(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.id = o.getInt("id");
        this.daysLeft = o.getInt("days_left", -1);
        this.ambientObject = o.getInt("ambient_object", -1);
        this.specialProject = o.getString("special_project", null);
        this.scope = o.getObjectAsNullOr("scope", Scope::new);
        this.coordinate = o.getObjectAs("coordinate", Property::new);
        this.planet = o.getInt("planet", -1);
        this.aiResearchDate = o.getDate("ai_research_date", null);
    }
}
