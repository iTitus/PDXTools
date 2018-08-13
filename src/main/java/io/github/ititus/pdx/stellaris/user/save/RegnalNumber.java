package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class RegnalNumber {

    private final int regnalNumber;
    private final String regnalName;

    public RegnalNumber(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.regnalName = o.getString("regnal_name");
        this.regnalNumber = o.getInt("regnal_number");
    }

    public RegnalNumber(int regnalNumber, String regnalName) {
        this.regnalNumber = regnalNumber;
        this.regnalName = regnalName;
    }

    public int getRegnalNumber() {
        return regnalNumber;
    }

    public String getRegnalName() {
        return regnalName;
    }
}
