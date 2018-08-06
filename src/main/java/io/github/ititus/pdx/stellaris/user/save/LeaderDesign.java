package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Objects;

public class LeaderDesign {

    private final int texture, hair, clothes;
    private final String gender, name, portrait, rulerTitle, rulerTitleFemale;

    public LeaderDesign(PdxScriptObject o) {
        this.gender = o.getString("gender");
        this.name = o.getString("name");
        this.portrait = o.getString("portrait");
        this.texture = o.getInt("texture");
        this.hair = o.getInt("hair");
        this.clothes = o.getInt("clothes");
        this.rulerTitle = o.getString("ruler_title");
        this.rulerTitleFemale = o.getString("ruler_title_female");
    }

    public LeaderDesign(int texture, int hair, int clothes, String gender, String name, String portrait, String rulerTitle, String rulerTitleFemale) {
        this.texture = texture;
        this.hair = hair;
        this.clothes = clothes;
        this.gender = gender;
        this.name = name;
        this.portrait = portrait;
        this.rulerTitle = rulerTitle;
        this.rulerTitleFemale = rulerTitleFemale;
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

    public String getRulerTitleFemale() {
        return rulerTitleFemale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaderDesign)) {
            return false;
        }
        LeaderDesign that = (LeaderDesign) o;
        return texture == that.texture && hair == that.hair && clothes == that.clothes && Objects.equals(gender, that.gender) && Objects.equals(name, that.name) && Objects.equals(portrait, that.portrait) && Objects.equals(rulerTitle, that.rulerTitle) && Objects.equals(rulerTitleFemale, that.rulerTitleFemale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texture, hair, clothes, gender, name, portrait, rulerTitle, rulerTitleFemale);
    }
}
