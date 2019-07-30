package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class Claim {

    private final int owner, claims;
    private final LocalDate date;

    public Claim(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.owner = o.getInt("owner");
        this.date = o.getDate("date");
        this.claims = o.getInt("claims");
    }

    public Claim(int owner, int claims, LocalDate date) {
        this.owner = owner;
        this.claims = claims;
        this.date = date;
    }

    public int getOwner() {
        return owner;
    }

    public int getClaims() {
        return claims;
    }

    public LocalDate getDate() {
        return date;
    }
}
