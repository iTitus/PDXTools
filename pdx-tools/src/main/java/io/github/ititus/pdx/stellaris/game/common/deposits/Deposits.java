package io.github.ititus.pdx.stellaris.game.common.deposits;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import org.eclipse.collections.api.map.ImmutableMap;

public class Deposits {

    public final ImmutableMap<String, Deposit> deposits;

    public Deposits(StellarisGame game, PdxScriptObject o) {
        this.deposits = o.getAsStringObjectMap((k, v) -> new Deposit(game, k, v));
    }

    public Deposit get(String depositType) {
        return deposits.get(depositType);
    }
}
