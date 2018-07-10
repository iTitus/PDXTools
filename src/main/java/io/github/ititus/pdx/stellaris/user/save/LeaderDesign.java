package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class LeaderDesign {

    private final int texture, hair, clothes;
    private final String gender, name, portrait, rulerTitle;

    public LeaderDesign(PdxScriptObject o) {
        this.gender = o.getString("gender");
        this.name = o.getString("name");
        this.portrait = o.getString("portrait");
        this.texture = o.getInt("texture");
        this.hair = o.getInt("hair");
        this.clothes = o.getInt("clothes");
        this.rulerTitle = o.getString("ruler_title");
    }

    public LeaderDesign(int texture, int hair, int clothes, String gender, String name, String portrait, String rulerTitle) {
        this.texture = texture;
        this.hair = hair;
        this.clothes = clothes;
        this.gender = gender;
        this.name = name;
        this.portrait = portrait;
        this.rulerTitle = rulerTitle;
    }

    public int getTexture() {
        return texture;
    }

    public int getHair() {
        return hair;
    }

    public int getClothes() {
        return clothes;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getRulerTitle() {
        return rulerTitle;
    }
}
