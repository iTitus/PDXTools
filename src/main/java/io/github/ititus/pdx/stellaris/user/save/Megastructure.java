package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableIntIntMap;

public class Megastructure {

    public final MegastructureUpgrade upgrade;
    public final String type;
    public final Coordinate coordinate;
    public final int owner;
    public final ImmutableMap<String, FlagData> flags;
    public final String graphicalCulture;
    public final int planet;
    public final int buildQueue;
    public final ImmutableIntIntMap orbitals;
    public final int bypass;

    public Megastructure(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.upgrade = o.getObjectAsNullOr("upgrade", MegastructureUpgrade::new);
        this.type = o.getString("type");
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.owner = o.getInt("owner", -1);
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.graphicalCulture = o.getString("graphical_culture", null);
        this.planet = o.getUnsignedInt("planet");
        this.orbitals = o.getObjectAsIntUnsignedIntMap("orbitals");
        this.buildQueue = o.getUnsignedInt("build_queue");
        this.bypass = o.getInt("bypass", -1);
    }
}
