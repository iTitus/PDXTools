package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

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
}
