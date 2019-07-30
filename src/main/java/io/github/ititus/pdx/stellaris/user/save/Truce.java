package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class Truce {

    private final String name, truceType;
    private final LocalDate startDate;

    public Truce(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.startDate = o.getDate("start_date");
        this.truceType = o.getString("truce_type");
    }

    public Truce(String name, String truceType, LocalDate startDate) {
        this.name = name;
        this.truceType = truceType;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public String getTruceType() {
        return truceType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
