package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableLongObjectMap;

public class Sectors {

    private final ImmutableLongObjectMap<Sector> sectors;

    public Sectors(PdxScriptObject o) {
        this.sectors = o.getAsLongObjectMap(Sector::new);
    }

    public Sectors(ImmutableLongObjectMap<Sector> sectors) {
        this.sectors = sectors;
    }

    public ImmutableLongObjectMap<Sector> getSectors() {
        return sectors;
    }
}
