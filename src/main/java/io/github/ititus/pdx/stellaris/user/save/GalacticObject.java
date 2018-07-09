package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.*;
import io.github.ititus.pdx.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GalacticObject {

    private final int arm, initParent, starbase;
    private final double innerRadius, outerRadius;
    private final String type, name, starClass, initializer;
    private final List<Integer> planets, ambientObjects, megaStructures, naturalWormholes, bypasses, discoveries, fleetPresence, auraPresence, ftlInhibitorPresence;
    private final List<Claim> claims;
    private final List<Hyperlane> hyperlanes;
    private final List<AsteroidBelt> asteroidBelts;
    private final Coordinate coordinate;
    private final Flags flags;

    public GalacticObject(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::new);
        this.type = o.getString("type");
        this.name = o.getString("name");
        IPdxScript s1 = o.get("planet");
        if (s1 instanceof PdxScriptList) {
            this.planets = ((PdxScriptList) s1).getAsIntegerList();
            o.use("planet", PdxConstants.LIST);
        } else if (s1 instanceof PdxScriptValue) {
            this.planets = CollectionUtil.listOf((int) ((PdxScriptValue) s1).getValue());
            o.use("planet", PdxConstants.INT);
        } else {
            this.planets = CollectionUtil.listOf();
        }
        PdxScriptList l = o.getList("ambient_object");
        this.ambientObjects = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        l = o.getList("megastructures");
        this.megaStructures = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        l = o.getList("claims");
        this.claims = l != null ? l.getAsList(Claim::new) : CollectionUtil.listOf();
        this.starClass = o.getString("star_class");
        l = o.getList("hyperlane");
        this.hyperlanes = l != null ? l.getAsList(Hyperlane::new) : CollectionUtil.listOf();
        l = o.getList("natural_wormholes");
        this.naturalWormholes = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        l = o.getList("bypasses");
        this.bypasses = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        l = o.getList("asteroid_belts");
        this.asteroidBelts = l != null ? l.getAsList(AsteroidBelt::new) : CollectionUtil.listOf();
        l = o.getList("discovery");
        this.discoveries = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        this.arm = o.getInt("arm");
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::new) : new Flags(Collections.emptyMap(), Collections.emptyMap());
        this.initializer = o.getString("initializer");
        this.initParent = o.getInt("init_parent", -1);
        l = o.getList("fleet_presence");
        this.fleetPresence = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        l = o.getList("aura_presence");
        this.auraPresence = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        l = o.getList("ftl_inhibitor_presence");
        this.ftlInhibitorPresence = l != null ? l.getAsIntegerList() : CollectionUtil.listOf();
        this.innerRadius = o.getDouble("inner_radius");
        this.outerRadius = o.getDouble("outer_radius");
        this.starbase = o.getUnsignedInt("starbase");
    }

    public GalacticObject(Coordinate coordinate, String type, String name, String starClass, String initializer, Collection<Integer> planets, Collection<Integer> ambientObjects, Collection<Integer> megaStructures, Collection<Integer> naturalWormholes, Collection<Integer> bypasses, Collection<Integer> discoveries, Collection<Integer> fleetPresence, Collection<Integer> auraPresence, Collection<Integer> ftlInhibitorPresence, Collection<Claim> claims, Collection<Hyperlane> hyperlanes, Collection<AsteroidBelt> asteroidBelts, int arm, int initParent, int starbase, double innerRadius, double outerRadius, Flags flags) {
        this.coordinate = coordinate;
        this.type = type;
        this.name = name;
        this.starClass = starClass;
        this.initializer = initializer;
        this.planets = new ArrayList<>(planets);
        this.ambientObjects = new ArrayList<>(ambientObjects);
        this.megaStructures = new ArrayList<>(megaStructures);
        this.naturalWormholes = new ArrayList<>(naturalWormholes);
        this.bypasses = new ArrayList<>(bypasses);
        this.discoveries = new ArrayList<>(discoveries);
        this.fleetPresence = new ArrayList<>(fleetPresence);
        this.auraPresence = new ArrayList<>(auraPresence);
        this.ftlInhibitorPresence = new ArrayList<>(ftlInhibitorPresence);
        this.claims = new ArrayList<>(claims);
        this.hyperlanes = new ArrayList<>(hyperlanes);
        this.asteroidBelts = new ArrayList<>(asteroidBelts);
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

    public List<Integer> getPlanets() {
        return Collections.unmodifiableList(planets);
    }

    public List<Integer> getAmbientObjects() {
        return Collections.unmodifiableList(ambientObjects);
    }

    public List<Integer> getMegaStructures() {
        return Collections.unmodifiableList(megaStructures);
    }

    public List<Integer> getNaturalWormholes() {
        return Collections.unmodifiableList(naturalWormholes);
    }

    public List<Integer> getBypasses() {
        return Collections.unmodifiableList(bypasses);
    }

    public List<Integer> getDiscoveries() {
        return Collections.unmodifiableList(discoveries);
    }

    public List<Integer> getFleetPresence() {
        return Collections.unmodifiableList(fleetPresence);
    }

    public List<Integer> getAuraPresence() {
        return Collections.unmodifiableList(auraPresence);
    }

    public List<Integer> getFtlInhibitorPresence() {
        return Collections.unmodifiableList(ftlInhibitorPresence);
    }

    public List<Claim> getClaims() {
        return Collections.unmodifiableList(claims);
    }

    public List<Hyperlane> getHyperlanes() {
        return Collections.unmodifiableList(hyperlanes);
    }

    public List<AsteroidBelt> getAsteroidBelts() {
        return Collections.unmodifiableList(asteroidBelts);
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
