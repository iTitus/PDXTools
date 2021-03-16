package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class BuildItem {

    public final int shipDesign;
    public final int pop;
    public final int speciesIndex;
    public final String type;
    public final String army;
    public final String starbaseBuilding;
    public final String starbaseLevel;
    public final String starbaseModule;
    public final ImmutableIntList ships;

    public BuildItem(PdxScriptObject o) {
        this.type = o.getString("type");
        this.shipDesign = o.getInt("ship_design", -1);
        this.pop = o.getInt("pop", -1);
        this.army = o.getString("army");
        this.speciesIndex = o.getInt("species_index");
        this.starbaseBuilding = o.getString("starbase_building");
        this.starbaseLevel = o.getString("starbase_level");
        this.starbaseModule = o.getString("starbase_module");
        this.ships = o.getListAsEmptyOrIntList("ships");
        // TODO: district
    }
}
