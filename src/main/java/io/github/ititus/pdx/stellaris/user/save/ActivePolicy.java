package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class ActivePolicy {

    private final String policy, selected;
    private final LocalDate date;

    public ActivePolicy(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.policy = o.getString("policy");
        this.selected = o.getString("selected");
        this.date = o.getDate("date");
    }

    public ActivePolicy(String policy, String selected, LocalDate date) {
        this.policy = policy;
        this.selected = selected;
        this.date = date;
    }

    public String getPolicy() {
        return policy;
    }

    public String getSelected() {
        return selected;
    }

    public LocalDate getDate() {
        return date;
    }
}
