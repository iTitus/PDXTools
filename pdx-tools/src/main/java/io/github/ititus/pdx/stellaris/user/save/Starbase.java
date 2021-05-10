package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntIntMap;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Starbase {

    public final String level;
    public final ImmutableIntObjectMap<String> modules;
    public final ImmutableIntObjectMap<String> buildings;
    public final int buildQueue;
    public final int shipyardBuildQueue;
    public final int shipDesign;
    public final int station;
    public final int owner;
    public final ImmutableIntIntMap orbitals;

    public Starbase(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.level = o.getString("level");
        this.modules = o.getObjectAsEmptyOrIntStringMap("modules");
        this.buildings = o.getObjectAsEmptyOrIntStringMap("buildings");
        this.buildQueue = o.getInt("build_queue");
        this.shipyardBuildQueue = o.getUnsignedInt("shipyard_build_queue");
        this.shipDesign = o.getInt("ship_design");
        this.station = o.getUnsignedInt("station");
        this.owner = o.getInt("owner");
        this.orbitals = o.getObjectAsEmptyOrIntUnsignedIntMap("orbitals");
    }
}
