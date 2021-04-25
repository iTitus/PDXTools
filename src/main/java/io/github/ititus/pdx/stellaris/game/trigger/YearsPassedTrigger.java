package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;

public class YearsPassedTrigger extends Trigger {

    public final PdxRelation relation;
    public final int years;

    public YearsPassedTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        PdxScriptValue v = s.expectValue();
        this.relation = v.getRelation();
        this.years = v.expectInt();
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: all
        // return scope.getYearsPassed() <relation> years;
        throw new UnsupportedOperationException();
    }
}
