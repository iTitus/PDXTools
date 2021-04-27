package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import org.eclipse.collections.api.list.ImmutableList;

public class TechnologyWeightModifiers {

    public final double factor;
    public final ImmutableList<TechnologyWeightModifier> modifiers;

    private final StellarisGame game;

    public TechnologyWeightModifiers(StellarisGame game, PdxScriptObject o) {
        this.game = game;
        this.factor = o.getDouble("factor", 1);
        this.modifiers = o.getImplicitListAsList("modifier", s -> new TechnologyWeightModifier(game, s));
    }
}
