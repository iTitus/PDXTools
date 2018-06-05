package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.Date;
import java.util.List;

public class Meta {

    private final String version;
    private final int versionControlRevision;
    private final String name;
    private final Date date;
    private final List<String> requiredDLCs;
    private final String playerPortrait;
    private final Flag flag;
    private final double fleets;
    private final int planets;

    Meta(PdxScriptObject o) {
        this.version = o.getString("version");
        this.versionControlRevision = o.getInt("version_control_revision");
        this.name = o.getString("name");
        this.date = o.getDate("date");
        this.requiredDLCs = o.getList("required_dlcs").getAsStringList();
        this.playerPortrait = o.getString("player_portrait");
        this.flag = o.getObject("flag").getAs(Flag::new);
        this.fleets = o.getDouble("meta_fleets");
        this.planets = o.getInt("meta_planets");
    }

    public String getVersion() {
        return version;
    }

    public int getVersionControlRevision() {
        return versionControlRevision;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getRequiredDLCs() {
        return requiredDLCs;
    }

    public String getPlayerPortrait() {
        return playerPortrait;
    }

    public Flag getFlag() {
        return flag;
    }

    public double getFleets() {
        return fleets;
    }

    public int getPlanets() {
        return planets;
    }
}
