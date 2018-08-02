package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Objects;

public class ShipSection {

    private static final Deduplicator<ShipSection> DEDUPLICATOR = new Deduplicator<>();

    private final String design, slot;
    private final ImmutableList<ShipWeapon> weapons;
    private final ImmutableList<ShipStrikeCraft> strikeCrafts;

    private ShipSection(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.design = o.getString("design");
        this.slot = o.getString("slot");
        this.weapons = o.getImplicitList("weapon").getAsList(ShipWeapon::of);
        this.strikeCrafts = o.getImplicitList("strike_craft").getAsList(ShipStrikeCraft::new);
    }

    private ShipSection(String design, String slot, ImmutableList<ShipWeapon> weapons, ImmutableList<ShipStrikeCraft> strikeCrafts) {
        this.design = design;
        this.slot = slot;
        this.weapons = weapons;
        this.strikeCrafts = strikeCrafts;
    }

    public static ShipSection of(IPdxScript s) {
        return DEDUPLICATOR.deduplicate(new ShipSection(s));
    }

    public static ShipSection of(String design, String slot, ImmutableList<ShipWeapon> weapons, ImmutableList<ShipStrikeCraft> strikeCrafts) {
        return DEDUPLICATOR.deduplicate(new ShipSection(design, slot, weapons, strikeCrafts));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShipSection)) return false;
        ShipSection that = (ShipSection) o;
        return Objects.equals(design, that.design) && Objects.equals(slot, that.slot) && Objects.equals(weapons, that.weapons) && Objects.equals(strikeCrafts, that.strikeCrafts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(design, slot, weapons, strikeCrafts);
    }
}
