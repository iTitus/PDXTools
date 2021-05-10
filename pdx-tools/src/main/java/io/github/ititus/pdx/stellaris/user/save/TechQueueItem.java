package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class TechQueueItem {

    public final double progress;
    public final String technology;
    public final int specialProject;
    public final LocalDate date;

    public TechQueueItem(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.progress = o.getDouble("progress", 0);
        this.technology = o.getString("technology", null);
        this.specialProject = o.getInt("special_project", -1);
        this.date = o.getDate("date");
    }
}
