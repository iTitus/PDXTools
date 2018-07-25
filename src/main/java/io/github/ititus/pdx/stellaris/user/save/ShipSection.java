package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableArrayList;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.Collection;
import java.util.List;

public class ShipSection {

    private final String design, slot;
    private final ViewableList<ShipWeapon> weapons;
    private final ViewableList<ShipStrikeCraft> strikeCrafts;

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

    public ShipSection(String design, String slot, Collection<ShipWeapon> weapons, Collection<ShipStrikeCraft> strikeCrafts) {
        this.design = design;
        this.slot = slot;
        this.weapons = new ViewableArrayList<>(weapons);
        this.strikeCrafts = new ViewableArrayList<>(strikeCrafts);
    }

    public String getDesign() {
        return design;
    }

    public String getSlot() {
        return slot;
    }

    public List<ShipWeapon> getWeapons() {
        return weapons.getView();
    }

    public List<ShipStrikeCraft> getStrikeCrafts() {
        return strikeCrafts.getView();
    }
}
