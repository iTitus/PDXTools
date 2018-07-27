package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Tiles {

    private final IntObjMap<Tile> tiles;

    public Tiles(PdxScriptObject o) {
        this.tiles = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Tile::new));
    }

    public Tiles(IntObjMap<Tile> tiles) {
        this.tiles = HashIntObjMaps.newImmutableMap(tiles);
    }

    public IntObjMap<Tile> getTiles() {
        return tiles;
    }
}
