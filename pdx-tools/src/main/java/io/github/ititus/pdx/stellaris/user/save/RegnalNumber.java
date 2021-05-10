package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class RegnalNumber {

    public final int regnalNumber;
    public final String regnalName;

    public RegnalNumber(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.regnalName = o.getString("regnal_name");
        this.regnalNumber = o.getInt("regnal_number");
    }
}
