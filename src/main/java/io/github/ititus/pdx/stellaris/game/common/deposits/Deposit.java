package io.github.ititus.pdx.stellaris.game.common.deposits;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.scope.PlanetScope;
import io.github.ititus.pdx.stellaris.shared.Resources;

public class Deposit {

    public final boolean isNull;
    public final DepositResources resources;
    private final StellarisGame game;

    public Deposit(StellarisGame game, IPdxScript s) {
        this.game = game;
        PdxScriptObject o = s.expectObject();
        this.isNull = o.getBoolean("is_null", false);
        this.resources = o.getObjectAsNullOr("resources", o_ -> new DepositResources(game, o_));
    }

    public Resources getProduced(PlanetScope scope) {
        return resources != null ? resources.getProduced(scope) : new Resources();
    }

    public Resources getCost(PlanetScope scope) {
        return resources != null ? resources.getCost(scope) : new Resources();
    }
}
