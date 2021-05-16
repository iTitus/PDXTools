package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class War {

    public final int id;

    public War(int id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
        // TODO: name, start_date, attackers: Belligerent, defenders: Belligerent, attacker_war_goal: Type,
        //  defender_war_goal: Type, have_defender_war_goal, attacker_war_exhaustion, defender_war_exhaustion,
        //  defender_force_peace, defender_force_peace_date
    }
}
