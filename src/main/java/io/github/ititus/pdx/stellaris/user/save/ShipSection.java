package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class ShipSection {

    private final String design, slot;
    private final ImmutableList<ShipWeapon> weapons;
    private final ImmutableList<ShipStrikeCraft> strikeCrafts;

    public ShipSection(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.design = o.getString("design");
        this.slot = o.getString("slot");
        this.weapons = o.getImplicitList("weapon").getAsList(ShipWeapon::new);
        this.strikeCrafts = o.getImplicitList("strike_craft").getAsList(ShipStrikeCraft::new);
    }

    public ShipSection(String design, String slot, ImmutableList<ShipWeapon> weapons, ImmutableList<ShipStrikeCraft> strikeCrafts) {
        this.design = design;
        this.slot = slot;
        this.weapons = weapons;
        this.strikeCrafts = strikeCrafts;
    }

    public String getDesign() {
        return design;
    }

    public String getSlot() {
        return slot;
    }

    public ImmutableList<ShipWeapon> getWeapons() {
        return weapons;
    }

    public ImmutableList<ShipStrikeCraft> getStrikeCrafts() {
        return strikeCrafts;
    }
}
