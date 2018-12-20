package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class War {

    public War(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        // TODO: name, start_date, attackers: Belligerent, defenders: Belligerent, attacker_war_goal: Type, defender_war_goal: Type, have_defender_war_goal, attacker_war_exhaustion, defender_war_exhaustion, defender_force_peace, defender_force_peace_date
    }
}
