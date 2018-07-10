package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class LeaderRoles {

    private final Traits admiral, general, scientist, governor, ruler;

    public LeaderRoles(PdxScriptObject o) {
        this.admiral = get(o, "admiral");
        this.general = get(o, "general");
        this.scientist = get(o, "scientist");
        this.governor = get(o, "governor");
        this.ruler = get(o, "ruler");
    }

    public LeaderRoles(Traits admiral, Traits general, Traits scientist, Traits governor, Traits ruler) {
        this.admiral = admiral;
        this.general = general;
        this.scientist = scientist;
        this.governor = governor;
        this.ruler = ruler;
    }

    private static Traits get(PdxScriptObject o, String key) {
        PdxScriptObject o1 = o.getObject(key);
        return o1 != null ? o1.getAs(Traits::new) : null;
    }

    public Traits getAdmiral() {
        return admiral;
    }

    public Traits getGeneral() {
        return general;
    }

    public Traits getScientist() {
        return scientist;
    }

    public Traits getGovernor() {
        return governor;
    }

    public Traits getRuler() {
        return ruler;
    }
}
