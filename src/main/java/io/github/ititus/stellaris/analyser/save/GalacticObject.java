package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptList;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptValue;
import io.github.ititus.stellaris.analyser.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GalacticObject {

    private final Coordinate coordinate;
    private final String type, name, starClass, initializer;
    private final List<Integer> planets, ambientObjects, megaStructures, naturalWormholes, bypasses, discoveries, fleetPresence, auraPresence, ftlInhibitorPresence;
    private final List<Claim> claims;
    private final List<Hyperlane> hyperlanes;
    private final List<AsteroidBelt> asteroidBelts;
    private final int arm, initParent, innerRadius, outerRadius;
    private final long starbase;
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
        } else if (s1 instanceof PdxScriptValue) {
            this.planets = CollectionUtil.listOf(o.getInt("planet"));
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
        this.innerRadius = o.getInt("inner_radius");
        this.outerRadius = o.getInt("outer_radius");
        this.starbase = o.getLong("starbase");
    }

    public GalacticObject(Coordinate coordinate, String type, String name, String starClass, String initializer, Collection<Integer> planets, Collection<Integer> ambientObjects, Collection<Integer> megaStructures, Collection<Integer> naturalWormholes, Collection<Integer> bypasses, Collection<Integer> discoveries, Collection<Integer> fleetPresence, Collection<Integer> auraPresence, Collection<Integer> ftlInhibitorPresence, Collection<Claim> claims, Collection<Hyperlane> hyperlanes, Collection<AsteroidBelt> asteroidBelts, int arm, int initParent, int innerRadius, int outerRadius, long starbase, Flags flags) {
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
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.starbase = starbase;
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
        return planets;
    }

    public List<Integer> getAmbientObjects() {
        return ambientObjects;
    }

    public List<Integer> getMegaStructures() {
        return megaStructures;
    }

    public List<Integer> getNaturalWormholes() {
        return naturalWormholes;
    }

    public List<Integer> getBypasses() {
        return bypasses;
    }

    public List<Integer> getDiscoveries() {
        return discoveries;
    }

    public List<Integer> getFleetPresence() {
        return fleetPresence;
    }

    public List<Integer> getAuraPresence() {
        return auraPresence;
    }

    public List<Integer> getFtlInhibitorPresence() {
        return ftlInhibitorPresence;
    }

    public List<Claim> getClaims() {
        return claims;
    }

    public List<Hyperlane> getHyperlanes() {
        return hyperlanes;
    }

    public List<AsteroidBelt> getAsteroidBelts() {
        return asteroidBelts;
    }

    public int getArm() {
        return arm;
    }

    public int getInitParent() {
        return initParent;
    }

    public int getInnerRadius() {
        return innerRadius;
    }

    public int getOuterRadius() {
        return outerRadius;
    }

    public long getStarbase() {
        return starbase;
    }

    public Flags getFlags() {
        return flags;
    }
}
