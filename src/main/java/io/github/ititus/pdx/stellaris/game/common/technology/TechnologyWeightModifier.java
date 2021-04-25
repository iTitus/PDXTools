package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;

import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public class TechnologyWeightModifier {

    private static final ImmutableSet<String> IGNORE = Sets.immutable.of(
            "factor",
            "add"
    );
    private static final Predicate<String> FILTER = not(IGNORE::contains);

    public final double factor;
    public final double add;
    public final ImmutableList<Trigger> triggers;

    private final StellarisGame game;

    public TechnologyWeightModifier(StellarisGame game, IPdxScript s) {
        this.game = game;
        PdxScriptObject o = s.expectObject();
        this.factor = o.getDouble("factor", 1);
        this.add = o.getDouble("add", 0);
        if (factor != 1 && add != 0) {
            throw new RuntimeException("factor and add both present");
        } else if (factor == 1 && add == 0) {
            throw new RuntimeException("factor and add both absent");
        }

        this.triggers = game.triggers.create(o, FILTER);
    }

    public boolean isActive(Scope scope) {
        return Trigger.evaluateAnd(scope, triggers);
    }
}
