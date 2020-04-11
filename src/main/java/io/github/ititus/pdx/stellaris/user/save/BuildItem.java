package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.util.Objects;

public class BuildItem {

    private final int shipDesign, pop, speciesIndex;
    private final String type, army, starbaseBuilding, starbaseLevel, starbaseModule;
    private final ImmutableIntList ships;

    public BuildItem(PdxScriptObject o) {
        this.type = o.getString("type");
        this.shipDesign = o.getInt("ship_design", -1);
        this.pop = o.getInt("pop", -1);
        this.army = o.getString("army");
        this.speciesIndex = o.getInt("species_index");
        this.starbaseBuilding = o.getString("starbase_building");
        this.starbaseLevel = o.getString("starbase_level");
        this.starbaseModule = o.getString("starbase_module");
        PdxScriptList l = o.getList("ships");
        this.ships = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        // TODO: district
    }

    public BuildItem(int shipDesign, int pop, int speciesIndex, String type, String army, String starbaseBuilding,
                     String starbaseLevel, String starbaseModule, ImmutableIntList ships) {
        this.shipDesign = shipDesign;
        this.pop = pop;
        this.speciesIndex = speciesIndex;
        this.type = type;
        this.army = army;
        this.starbaseBuilding = starbaseBuilding;
        this.starbaseLevel = starbaseLevel;
        this.starbaseModule = starbaseModule;
        this.ships = ships;
    }

    public int getShipDesign() {
        return shipDesign;
    }

    public int getPop() {
        return pop;
    }

    public int getSpeciesIndex() {
        return speciesIndex;
    }

    public String getType() {
        return type;
    }

    public String getArmy() {
        return army;
    }

    public String getStarbaseBuilding() {
        return starbaseBuilding;
    }

    public String getStarbaseLevel() {
        return starbaseLevel;
    }

    public String getStarbaseModule() {
        return starbaseModule;
    }

    public ImmutableIntList getShips() {
        return ships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildItem)) {
            return false;
        }
        BuildItem buildItem = (BuildItem) o;
        return shipDesign == buildItem.shipDesign && pop == buildItem.pop && speciesIndex == buildItem.speciesIndex && Objects.equals(type, buildItem.type) && Objects.equals(army, buildItem.army) && Objects.equals(starbaseBuilding, buildItem.starbaseBuilding) && Objects.equals(starbaseLevel, buildItem.starbaseLevel) && Objects.equals(starbaseModule, buildItem.starbaseModule) && Objects.equals(ships, buildItem.ships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipDesign, pop, speciesIndex, type, army, starbaseBuilding, starbaseLevel,
                starbaseModule, ships);
    }
}
