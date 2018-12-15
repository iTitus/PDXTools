package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;
import io.github.ititus.pdx.util.Util;

public class HomeBase {

    private static final Deduplicator<HomeBase> DEDUPLICATOR = new Deduplicator<>();

    private final int starbase;

    private HomeBase(PdxScriptObject o) {
        this.starbase = o.getInt("starbase");
    }

    private HomeBase(int starbase) {
        this.starbase = starbase;
    }

    public static HomeBase of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new HomeBase(o));
    }

    public static HomeBase of(int starbase) {
        return DEDUPLICATOR.deduplicate(new HomeBase(starbase));
    }

    public int getStarbase() {
        return starbase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HomeBase)) {
            return false;
        }
        return starbase == ((HomeBase) o).starbase;
    }

    @Override
    public int hashCode() {
        return Util.hash(starbase);
    }
}
