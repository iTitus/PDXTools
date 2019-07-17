package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Deposits {

    private final ImmutableIntObjectMap<Deposit> deposits;

    public Deposits(PdxScriptObject o) {
        this.deposits = o.getAsIntObjectMap(Deposit::new);
    }

    public Deposits(ImmutableIntObjectMap<Deposit> deposits) {
        this.deposits = deposits;
    }

    public ImmutableIntObjectMap<Deposit> getDeposits() {
        return deposits;
    }
}
