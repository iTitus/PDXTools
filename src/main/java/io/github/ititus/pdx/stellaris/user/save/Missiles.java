package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.LongObjMap;
import com.koloboke.collect.map.hash.HashLongObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Missiles {

    private final LongObjMap<Missile> missiles;

    public Missiles(PdxScriptObject o) {
        this.missiles = o.getAsLongObjMap(Long::parseLong, PdxScriptObject.nullOr(Missile::new));
    }

    public Missiles(LongObjMap<Missile> missiles) {
        this.missiles = HashLongObjMaps.newImmutableMap(missiles);
    }

    public LongObjMap<Missile> getMissiles() {
        return missiles;
    }
}
