package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Date;

public class DemocraticElection {

    private final Date end;

    public DemocraticElection(PdxScriptObject o) {
        this.end = o.getDate("end");
    }

    public DemocraticElection(Date end) {
        this.end = end;
    }

    public Date getEnd() {
        return end;
    }
}
