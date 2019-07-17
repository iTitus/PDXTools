package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Sectors {

    private final ImmutableIntObjectMap<Sector> sectors;

    public Sectors(PdxScriptObject o) {
        this.sectors = o.getAsIntObjectMap(Sector::new);
    }

    public Sectors(ImmutableIntObjectMap<Sector> sectors) {
        this.sectors = sectors;
    }

    public ImmutableIntObjectMap<Sector> getSectors() {
        return sectors;
    }
}
