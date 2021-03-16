package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.map.ImmutableMap;

public class GalacticObject {

    public final int arm;
    public final int initParent;
    public final int starbase;
    public final double innerRadius;
    public final double outerRadius;
    public final String type;
    public final String name;
    public final String starClass;
    public final String initializer;
    public final ImmutableIntList planets;
    public final ImmutableIntList ambientObjects;
    public final ImmutableIntList megaStructures;
    public final ImmutableIntList naturalWormholes;
    public final ImmutableIntList bypasses;
    public final ImmutableIntList discoveries;
    public final ImmutableIntList fleetPresence;
    public final ImmutableIntList auraPresence;
    public final ImmutableIntList ftlInhibitorPresence;
    public final ImmutableList<Claim> claims;
    public final ImmutableList<Hyperlane> hyperlanes;
    public final ImmutableList<AsteroidBelt> asteroidBelts;
    public final Coordinate coordinate;
    public final ImmutableMap<String, FlagData> flags;

    public GalacticObject(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.type = o.getString("type");
        this.name = o.getString("name");
        this.planets = o.getImplicitListAsIntList("planet");
        this.ambientObjects = o.getListAsEmptyOrIntList("ambient_object");
        this.megaStructures = o.getListAsEmptyOrIntList("megastructures");
        this.claims = o.getListAsEmptyOrList("claims", Claim::new);
        this.starClass = o.getString("star_class");
        this.hyperlanes = o.getListAsEmptyOrList("hyperlane", Hyperlane::new);
        this.naturalWormholes = o.getListAsEmptyOrIntList("natural_wormholes");
        this.bypasses = o.getListAsEmptyOrIntList("bypasses");
        this.asteroidBelts = o.getListAsEmptyOrList("asteroid_belts", AsteroidBelt::new);
        this.discoveries = o.getListAsEmptyOrIntList("discovery");
        this.arm = o.getInt("arm", -1);
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.initializer = o.getString("initializer");
        this.initParent = o.getInt("init_parent", -1);
        this.fleetPresence = o.getListAsEmptyOrIntList("fleet_presence");
        this.auraPresence = o.getListAsEmptyOrIntList("aura_presence");
        this.ftlInhibitorPresence = o.getListAsEmptyOrIntList("ftl_inhibitor_presence");
        this.innerRadius = o.getDouble("inner_radius");
        this.outerRadius = o.getDouble("outer_radius");
        this.starbase = o.getUnsignedInt("starbase");
        // TODO: trade_hub, trade_collection, trade_piracy, sector
    }
}
