package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Date;

public class Meta {

    private final String version;
    private final int versionControlRevision;
    private final String name;
    private final Date date;
    private final ImmutableList<String> requiredDLCs;
    private final String playerPortrait;
    private final Flag flag;
    private final double fleets;
    private final int planets;

    public Meta(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException();
        }
        PdxScriptObject o = (PdxScriptObject) s;

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

    public Meta(String version, int versionControlRevision, String name, Date date, ImmutableList<String> requiredDLCs, String playerPortrait, Flag flag, double fleets, int planets) {
        this.version = version;
        this.versionControlRevision = versionControlRevision;
        this.name = name;
        this.date = new Date(date.getTime());
        this.requiredDLCs = requiredDLCs;
        this.playerPortrait = playerPortrait;
        this.flag = flag;
        this.fleets = fleets;
        this.planets = planets;
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
        return new Date(date.getTime());
    }

    public ImmutableList<String> getRequiredDLCs() {
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
