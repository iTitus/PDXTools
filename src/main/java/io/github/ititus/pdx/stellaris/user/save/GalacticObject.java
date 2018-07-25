package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.ViewableArrayList;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GalacticObject {

    private final int arm, initParent, starbase;
    private final double innerRadius, outerRadius;
    private final String type, name, starClass, initializer;
    private final ViewableList<Integer> planets, ambientObjects, megaStructures, naturalWormholes, bypasses, discoveries, fleetPresence, auraPresence, ftlInhibitorPresence;
    private final ViewableList<Claim> claims;
    private final ViewableList<Hyperlane> hyperlanes;
    private final ViewableList<AsteroidBelt> asteroidBelts;
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
        this.planets = o.getImplicitList("planet").getAsIntegerList();
        PdxScriptList l = o.getList("ambient_object");
        this.ambientObjects = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
        l = o.getList("megastructures");
        this.megaStructures = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
        l = o.getList("claims");
        this.claims = l != null ? l.getAsList(Claim::new) : CollectionUtil.viewableListOf();
        this.starClass = o.getString("star_class");
        l = o.getList("hyperlane");
        this.hyperlanes = l != null ? l.getAsList(Hyperlane::new) : CollectionUtil.viewableListOf();
        l = o.getList("natural_wormholes");
        this.naturalWormholes = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
        l = o.getList("bypasses");
        this.bypasses = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
        l = o.getList("asteroid_belts");
        this.asteroidBelts = l != null ? l.getAsList(AsteroidBelt::new) : CollectionUtil.viewableListOf();
        l = o.getList("discovery");
        this.discoveries = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
        this.arm = o.getInt("arm");
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::new) : new Flags(Collections.emptyMap(), Collections.emptyMap());
        this.initializer = o.getString("initializer");
        this.initParent = o.getInt("init_parent", -1);
        l = o.getList("fleet_presence");
        this.fleetPresence = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
        l = o.getList("aura_presence");
        this.auraPresence = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
        l = o.getList("ftl_inhibitor_presence");
        this.ftlInhibitorPresence = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
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
        this.planets = new ViewableArrayList<>(planets);
        this.ambientObjects = new ViewableArrayList<>(ambientObjects);
        this.megaStructures = new ViewableArrayList<>(megaStructures);
        this.naturalWormholes = new ViewableArrayList<>(naturalWormholes);
        this.bypasses = new ViewableArrayList<>(bypasses);
        this.discoveries = new ViewableArrayList<>(discoveries);
        this.fleetPresence = new ViewableArrayList<>(fleetPresence);
        this.auraPresence = new ViewableArrayList<>(auraPresence);
        this.ftlInhibitorPresence = new ViewableArrayList<>(ftlInhibitorPresence);
        this.claims = new ViewableArrayList<>(claims);
        this.hyperlanes = new ViewableArrayList<>(hyperlanes);
        this.asteroidBelts = new ViewableArrayList<>(asteroidBelts);
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
        return planets.getView();
    }

    public List<Integer> getAmbientObjects() {
        return ambientObjects.getView();
    }

    public List<Integer> getMegaStructures() {
        return megaStructures.getView();
    }

    public List<Integer> getNaturalWormholes() {
        return naturalWormholes.getView();
    }

    public List<Integer> getBypasses() {
        return bypasses.getView();
    }

    public List<Integer> getDiscoveries() {
        return discoveries.getView();
    }

    public List<Integer> getFleetPresence() {
        return fleetPresence.getView();
    }

    public List<Integer> getAuraPresence() {
        return auraPresence.getView();
    }

    public List<Integer> getFtlInhibitorPresence() {
        return ftlInhibitorPresence.getView();
    }

    public List<Claim> getClaims() {
        return claims.getView();
    }

    public List<Hyperlane> getHyperlanes() {
        return hyperlanes.getView();
    }

    public List<AsteroidBelt> getAsteroidBelts() {
        return asteroidBelts.getView();
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
