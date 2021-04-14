package io.github.ititus.pdx.stellaris.game.common.deposits;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.ImmutableMap;

public class Deposits {

    public final ImmutableMap<String, Deposit> deposits;

    public Deposits(PdxScriptObject o) {
        this.deposits = o.getAsStringObjectMap(Deposit::new);
    }
}
