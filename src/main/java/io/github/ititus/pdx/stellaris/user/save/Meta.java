package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.time.LocalDate;

public class Meta {

    private final int versionControlRevision, planets;
    private final double fleets;
    private final String version, name, playerPortrait;
    private final LocalDate date;
    private final ImmutableList<String> requiredDLCs;
    private final Flag flag;

    public Meta(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
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

    public Meta(int versionControlRevision, int planets, double fleets, String version, String name, String playerPortrait, LocalDate date, ImmutableList<String> requiredDLCs, Flag flag) {
        this.versionControlRevision = versionControlRevision;
        this.planets = planets;
        this.fleets = fleets;
        this.version = version;
        this.name = name;
        this.playerPortrait = playerPortrait;
        this.date = date;
        this.requiredDLCs = requiredDLCs;
        this.flag = flag;
    }

    public int getVersionControlRevision() {
        return versionControlRevision;
    }

    public int getPlanets() {
        return planets;
    }

    public double getFleets() {
        return fleets;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getPlayerPortrait() {
        return playerPortrait;
    }

    public LocalDate getDate() {
        return date;
    }

    public ImmutableList<String> getRequiredDLCs() {
        return requiredDLCs;
    }

    public Flag getFlag() {
        return flag;
    }
}
