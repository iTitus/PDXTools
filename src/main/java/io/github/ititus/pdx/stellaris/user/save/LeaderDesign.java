package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class LeaderDesign {

    public final String gender;
    public final String name;
    public final String portrait;
    public final int texture;
    public final int hair;
    public final int clothes;
    public final String rulerTitle;
    public final String rulerTitleFemale;
    public final String leaderClass;

    public LeaderDesign(PdxScriptObject o) {
        this.gender = o.getString("gender");
        this.name = o.getString("name");
        this.portrait = o.getString("portrait");
        this.texture = o.getInt("texture", -1);
        this.hair = o.getInt("hair", -1);
        this.clothes = o.getInt("clothes", -1);
        this.rulerTitle = o.getString("ruler_title", null);
        this.rulerTitleFemale = o.getString("ruler_title_female", null);
        this.leaderClass = o.getString("leader_class");
    }
}
