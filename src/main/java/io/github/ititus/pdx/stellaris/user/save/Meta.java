package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.*;

public class Meta {

    private final PdxScriptObject o;

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
        this.o = o;

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

    public Meta(String version, int versionControlRevision, String name, Date date, Collection<String> requiredDLCs, String playerPortrait, Flag flag, double fleets, int planets) {
        this.o = null;

        this.version = version;
        this.versionControlRevision = versionControlRevision;
        this.name = name;
        this.date = new Date(date.getTime());
        this.requiredDLCs = new ArrayList<>(requiredDLCs);
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

    public List<String> getRequiredDLCs() {
        return Collections.unmodifiableList(requiredDLCs);
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

    public Map<String, Set<String>> getErrors() {
        Map<String, Set<String>> errors = new HashMap<>();
        o.getErrors().forEach((k, v) -> errors.computeIfAbsent(k, k_ -> new HashSet<>()).addAll(v));
        return errors;
    }
}
