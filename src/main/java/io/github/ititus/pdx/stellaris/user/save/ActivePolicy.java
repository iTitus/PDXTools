package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Date;

public class ActivePolicy {

    private final String policy, selected;
    private final Date date;

    public ActivePolicy(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.policy = o.getString("policy");
        this.selected = o.getString("selected");
        this.date = o.getDate("date");
    }

    public ActivePolicy(String policy, String selected, Date date) {
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

    public Date getDate() {
        return date;
    }
}
