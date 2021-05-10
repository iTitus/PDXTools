package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class LeaderName {

    public final int regnalNumber;
    public final String firstName, secondName;

    public LeaderName(PdxScriptObject o) {
        this.firstName = o.getString("first_name");
        this.secondName = o.getString("second_name", null);
        this.regnalNumber = o.getInt("regnal_number", 1);
    }
}
