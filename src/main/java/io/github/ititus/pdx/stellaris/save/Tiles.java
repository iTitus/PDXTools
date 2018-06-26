package io.github.ititus.pdx.stellaris.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Tiles {

    private final Map<Integer, Tile> tiles;

    public Tiles(PdxScriptObject o) {
        this.tiles = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Tile::new));
    }

    public Tiles(Map<Integer, Tile> tiles) {
        this.tiles = new HashMap<>(tiles);
    }

    public Map<Integer, Tile> getTiles() {
        return Collections.unmodifiableMap(tiles);
    }
}
