package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.ImmutableMap;

import java.time.LocalDate;

public class Relation {

    public final boolean contact, alliance, defensivePact, friendly, hostile, migrationAccess, neutral, borders,
            isRival, communications, closedBorders;
    public final int killedShips, owner, country, truce, borderRange;
    public final double trust;
    public final String preCommunicationsName;
    public final LocalDate rivalDate, forcedOpenBorders;
    public final ImmutableList<RelationModifier> modifiers;
    public final ImmutableMap<String, FlagData> flags;

    public Relation(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.killedShips = o.getInt("killed_ships", 0);
        this.owner = o.getInt("owner");
        this.country = o.getInt("country");
        this.truce = o.getInt("truce", -1);
        this.contact = o.getBoolean("contact", false);
        this.alliance = o.getBoolean("alliance", false);
        this.defensivePact = o.getBoolean("defensive_pact", false);
        this.friendly = o.getBoolean("friendly", false);
        this.hostile = o.getBoolean("hostile", false);
        this.migrationAccess = o.getBoolean("migration_access", false);
        this.neutral = o.getBoolean("neutral", false);
        this.borders = o.getBoolean("borders", false);
        this.isRival = o.getBoolean("is_rival", false);
        this.rivalDate = o.getDate("rival_date", null);
        this.forcedOpenBorders = o.getDate("forced_open_borders", null);
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.borderRange = o.getInt("border_range", -1);
        this.trust = o.getDouble("trust", 0);
        this.modifiers = o.getImplicitListAsList("modifier", RelationModifier::new);
        this.communications = o.getBoolean("communications", false);
        this.preCommunicationsName = o.getString("pre_communications_name", null);
        this.closedBorders = o.getBoolean("closed_borders", false);
    }
}
