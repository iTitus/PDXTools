package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class ShipSection {

    public final String design, slot;
    public final ImmutableList<ShipWeapon> weapons;
    public final ImmutableList<ShipStrikeCraft> strikeCrafts;

    public ShipSection(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.design = o.getString("design");
        this.slot = o.getString("slot");
        this.weapons = o.getImplicitListAsList("weapon", ShipWeapon::new);
        this.strikeCrafts = o.getImplicitListAsList("strike_craft", ShipStrikeCraft::new);
    }
}
