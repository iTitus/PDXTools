package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Date;

public class Relation {

    private final boolean contact, alliance, defensivePact, friendly, hostile, migrationAccess, neutral, borders, isRival, communications, closedBorders;
    private final int killedShips, owner, country, truce, borderRange;
    private final double trust;
    private final String preCommunicationsName;
    private final Date rivalDate, forcedOpenBorders;
    private final ImmutableList<RelationModifier> modifiers;
    private final Flags flags;

    public Relation(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.killedShips = o.getInt("killed_ships");
        this.owner = o.getInt("owner");
        this.country = o.getInt("country");
        this.truce = o.getInt("truce", -1);
        this.contact = o.getBoolean("contact");
        this.alliance = o.getBoolean("alliance");
        this.defensivePact = o.getBoolean("defensive_pact");
        this.friendly = o.getBoolean("friendly");
        this.hostile = o.getBoolean("hostile");
        this.migrationAccess = o.getBoolean("migration_access");
        this.neutral = o.getBoolean("neutral");
        this.borders = o.getBoolean("borders");
        this.isRival = o.getBoolean("is_rival");
        this.rivalDate = o.getDate("rival_date");
        this.forcedOpenBorders = o.getDate("forced_open_borders");
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::of) : null;
        this.borderRange = o.getInt("border_range", -1);
        this.trust = o.getDouble("trust");
        this.modifiers = o.getImplicitList("modifier").getAsList(RelationModifier::new);
        this.communications = o.getBoolean("communications");
        this.preCommunicationsName = o.getString("pre_communications_name");
        this.closedBorders = o.getBoolean("closed_borders");
    }

    public Relation(boolean contact, boolean alliance, boolean defensivePact, boolean friendly, boolean hostile, boolean migrationAccess, boolean neutral, boolean borders, boolean isRival, boolean communications, boolean closedBorders, int killedShips, int owner, int country, int truce, int borderRange, double trust, String preCommunicationsName, Date rivalDate, Date forcedOpenBorders, ImmutableList<RelationModifier> modifiers, Flags flags) {
        this.contact = contact;
        this.alliance = alliance;
        this.defensivePact = defensivePact;
        this.friendly = friendly;
        this.hostile = hostile;
        this.migrationAccess = migrationAccess;
        this.neutral = neutral;
        this.borders = borders;
        this.isRival = isRival;
        this.communications = communications;
        this.closedBorders = closedBorders;
        this.killedShips = killedShips;
        this.owner = owner;
        this.country = country;
        this.truce = truce;
        this.borderRange = borderRange;
        this.trust = trust;
        this.preCommunicationsName = preCommunicationsName;
        this.rivalDate = rivalDate;
        this.forcedOpenBorders = forcedOpenBorders;
        this.modifiers = modifiers;
        this.flags = flags;
    }

    public boolean isContact() {
        return contact;
    }

    public boolean isAlliance() {
        return alliance;
    }

    public boolean isDefensivePact() {
        return defensivePact;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public boolean isHostile() {
        return hostile;
    }

    public boolean isMigrationAccess() {
        return migrationAccess;
    }

    public boolean isNeutral() {
        return neutral;
    }

    public boolean isBorders() {
        return borders;
    }

    public boolean isRival() {
        return isRival;
    }

    public boolean isCommunications() {
        return communications;
    }

    public boolean isClosedBorders() {
        return closedBorders;
    }

    public int getKilledShips() {
        return killedShips;
    }

    public int getOwner() {
        return owner;
    }

    public int getCountry() {
        return country;
    }

    public int getTruce() {
        return truce;
    }

    public int getBorderRange() {
        return borderRange;
    }

    public double getTrust() {
        return trust;
    }

    public String getPreCommunicationsName() {
        return preCommunicationsName;
    }

    public Date getRivalDate() {
        return rivalDate;
    }

    public Date getForcedOpenBorders() {
        return forcedOpenBorders;
    }

    public ImmutableList<RelationModifier> getModifiers() {
        return modifiers;
    }

    public Flags getFlags() {
        return flags;
    }
}
