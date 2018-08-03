package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.primitive.IntLists;

public class GalacticObject {

    private final int arm, initParent, starbase;
    private final double innerRadius, outerRadius;
    private final String type, name, starClass, initializer;
    private final ImmutableIntList planets, ambientObjects, megaStructures, naturalWormholes, bypasses, discoveries, fleetPresence, auraPresence, ftlInhibitorPresence;
    private final ImmutableList<Claim> claims;
    private final ImmutableList<Hyperlane> hyperlanes;
    private final ImmutableList<AsteroidBelt> asteroidBelts;
    private final Coordinate coordinate;
    private final Flags flags;

    public GalacticObject(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.type = o.getString("type");
        this.name = o.getString("name");
        this.planets = o.getImplicitList("planet").getAsIntList();
        PdxScriptList l = o.getList("ambient_object");
        this.ambientObjects = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("megastructures");
        this.megaStructures = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("claims");
        this.claims = l != null ? l.getAsList(Claim::new) : Lists.immutable.empty();
        this.starClass = o.getString("star_class");
        l = o.getList("hyperlane");
        this.hyperlanes = l != null ? l.getAsList(Hyperlane::new) : Lists.immutable.empty();
        l = o.getList("natural_wormholes");
        this.naturalWormholes = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("bypasses");
        this.bypasses = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("asteroid_belts");
        this.asteroidBelts = l != null ? l.getAsList(AsteroidBelt::new) : Lists.immutable.empty();
        l = o.getList("discovery");
        this.discoveries = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.arm = o.getInt("arm");
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::of) : null;
        this.initializer = o.getString("initializer");
        this.initParent = o.getInt("init_parent", -1);
        l = o.getList("fleet_presence");
        this.fleetPresence = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("aura_presence");
        this.auraPresence = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("ftl_inhibitor_presence");
        this.ftlInhibitorPresence = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.innerRadius = o.getDouble("inner_radius");
        this.outerRadius = o.getDouble("outer_radius");
        this.starbase = o.getUnsignedInt("starbase");
    }

    public GalacticObject(Coordinate coordinate, String type, String name, String starClass, String initializer, ImmutableIntList planets, ImmutableIntList ambientObjects, ImmutableIntList megaStructures, ImmutableIntList naturalWormholes, ImmutableIntList bypasses, ImmutableIntList discoveries, ImmutableIntList fleetPresence, ImmutableIntList auraPresence, ImmutableIntList ftlInhibitorPresence, ImmutableList<Claim> claims, ImmutableList<Hyperlane> hyperlanes, ImmutableList<AsteroidBelt> asteroidBelts, int arm, int initParent, int starbase, double innerRadius, double outerRadius, Flags flags) {
        this.coordinate = coordinate;
        this.type = type;
        this.name = name;
        this.starClass = starClass;
        this.initializer = initializer;
        this.planets = planets;
        this.ambientObjects = ambientObjects;
        this.megaStructures = megaStructures;
        this.naturalWormholes = naturalWormholes;
        this.bypasses = bypasses;
        this.discoveries = discoveries;
        this.fleetPresence = fleetPresence;
        this.auraPresence = auraPresence;
        this.ftlInhibitorPresence = ftlInhibitorPresence;
        this.claims = claims;
        this.hyperlanes = hyperlanes;
        this.asteroidBelts = asteroidBelts;
        this.arm = arm;
        this.initParent = initParent;
        this.starbase = starbase;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.flags = flags;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getStarClass() {
        return starClass;
    }

    public String getInitializer() {
        return initializer;
    }

    public ImmutableIntList getPlanets() {
        return planets;
    }

    public ImmutableIntList getAmbientObjects() {
        return ambientObjects;
    }

    public ImmutableIntList getMegaStructures() {
        return megaStructures;
    }

    public ImmutableIntList getNaturalWormholes() {
        return naturalWormholes;
    }

    public ImmutableIntList getBypasses() {
        return bypasses;
    }

    public ImmutableIntList getDiscoveries() {
        return discoveries;
    }

    public ImmutableIntList getFleetPresence() {
        return fleetPresence;
    }

    public ImmutableIntList getAuraPresence() {
        return auraPresence;
    }

    public ImmutableIntList getFtlInhibitorPresence() {
        return ftlInhibitorPresence;
    }

    public ImmutableList<Claim> getClaims() {
        return claims;
    }

    public ImmutableList<Hyperlane> getHyperlanes() {
        return hyperlanes;
    }

    public ImmutableList<AsteroidBelt> getAsteroidBelts() {
        return asteroidBelts;
    }

    public int getArm() {
        return arm;
    }

    public int getInitParent() {
        return initParent;
    }

    public int getStarbase() {
        return starbase;
    }

    public double getInnerRadius() {
        return innerRadius;
    }

    public double getOuterRadius() {
        return outerRadius;
    }

    public Flags getFlags() {
        return flags;
    }
}
