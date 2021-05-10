package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.map.ImmutableMap;

import java.util.NoSuchElementException;

public class Technologies {

    private final ImmutableMap<String, Technology> technologies;

    public Technologies(StellarisGame game, PdxScriptObject o) {
        this.technologies = o.getAsStringObjectMap((k, v) -> Technology.of(game, k, v));
    }

    public Technology get(String name) {
        Technology technology = technologies.get(name);
        if (technology == null) {
            throw new NoSuchElementException("technology " + name + " not found");
        }

        return technology;
    }

    public RichIterable<Technology> all() {
        return technologies.valuesView();
    }

    public RichIterable<String> allNames() {
        return technologies.keysView();
    }
}
