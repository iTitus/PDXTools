package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class LeaderName {

    private final int regnalNumber;
    private final String firstName, secondName;

    public LeaderName(PdxScriptObject o) {
        this.firstName = o.getString("first_name");
        this.secondName = o.getString("second_name");
        this.regnalNumber = o.getInt("regnal_number", 1);
    }

    public LeaderName(int regnalNumber, String firstName, String secondName) {
        this.regnalNumber = regnalNumber;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public int getRegnalNumber() {
        return regnalNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }
}
