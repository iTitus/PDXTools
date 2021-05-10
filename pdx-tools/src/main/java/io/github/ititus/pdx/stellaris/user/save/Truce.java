package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class Truce {

    public final String name;
    public final LocalDate startDate;
    public final String truceType;

    public Truce(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.startDate = o.getDate("start_date");
        this.truceType = o.getString("truce_type");
    }
}
