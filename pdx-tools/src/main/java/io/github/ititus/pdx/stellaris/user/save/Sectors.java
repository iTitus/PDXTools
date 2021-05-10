package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class Sectors {

    public final int resources;
    public final Object monthlyTransfer;
    public final ImmutableIntList owned;

    public Sectors(PdxScriptObject o) {
        this.resources = o.getInt("resources");
        this.monthlyTransfer = o.getObjectAs("monthly_transfer", o1 -> {
            if (o1.size() > 0) {
                throw new RuntimeException("expected empty object for key monthly_transfer");
            }

            return null;
        }); // TODO: check if always empty?
        this.owned = o.getListAsEmptyOrIntList("owned");
    }
}
