package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.time.LocalDate;

public class Meta {

    public final String version;
    public final int versionControlRevision;
    public final String name;
    public final LocalDate date;
    public final ImmutableList<String> requiredDLCs;
    public final String playerPortrait;
    public final Flag flag;
    public final double fleets;
    public final int planets;

    public Meta(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.version = o.getString("version");
        this.versionControlRevision = o.getInt("version_control_revision");
        this.name = o.getString("name");
        this.date = o.getDate("date");
        this.requiredDLCs = o.getListAsStringList("required_dlcs");
        this.playerPortrait = o.getString("player_portrait");
        this.flag = o.getObjectAs("flag", Flag::new);
        this.fleets = o.getDouble("meta_fleets");
        this.planets = o.getInt("meta_planets");
    }
}
