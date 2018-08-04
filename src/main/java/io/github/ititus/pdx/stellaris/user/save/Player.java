package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Player {

    private final int country;
    private final String name;

    public Player(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.name = o.getString("name");
        this.country = o.getInt("country");
    }

    public Player(int country, String name) {
        this.country = country;
        this.name = name;
    }

    public int getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }
}
