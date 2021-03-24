package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class DiploAction {

    public final int country;
    public final String action;
    public final LocalDate creationDate;

    public DiploAction(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.country = o.getInt("country");
        this.action = o.getString("action");
        this.creationDate = o.getDate("creation_date", null);
    }
}
