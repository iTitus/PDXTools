package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import org.eclipse.collections.api.map.ImmutableMap;

public class Technologies {

    public final ImmutableMap<String, Technology> technologies;

    private final StellarisGame game;

    public Technologies(StellarisGame game, PdxScriptObject o) {
        this.game = game;
        this.technologies = o.getAsStringObjectMap(s -> new Technology(game, s));
    }
}
