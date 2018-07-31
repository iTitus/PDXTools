package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Tiles {

    private final ImmutableIntObjectMap<Tile> tiles;

    public Tiles(PdxScriptObject o) {
        this.tiles = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(Tile::new));
    }

    public Tiles(ImmutableIntObjectMap<Tile> tiles) {
        this.tiles = tiles;
    }

    public ImmutableIntObjectMap<Tile> getTiles() {
        return tiles;
    }
}
