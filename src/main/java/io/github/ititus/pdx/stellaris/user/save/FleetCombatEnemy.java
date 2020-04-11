package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

import java.util.Objects;

public class FleetCombatEnemy {

    private final int fleet, country;
    private final String shipClass, countryName, fleetName;
    private final ImmutableIntList shipSizeCount, shipSizeCountLost;
    private final ImmutableList<String> shipSizeKey, shipSizeName;
    private final Flag empireFlag;
    private final FleetCombatEnemyLeader leader;

    public FleetCombatEnemy(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.fleet = o.getInt("fleet");
        this.shipClass = o.getString("ship_class");
        this.country = o.getInt("country", -1);
        PdxScriptObject o1 = o.getObject("empire_flag");
        this.empireFlag = o1 != null ? o1.getAs(Flag::new) : null;
        this.countryName = o.getString("country_name");
        this.fleetName = o.getString("fleet_name");
        this.shipSizeKey = o.getList("ship_size_key").getAsStringList();
        this.shipSizeName = o.getList("ship_size_name").getAsStringList();
        this.shipSizeCount = o.getList("ship_size_count").getAsIntList();
        this.shipSizeCountLost = o.getList("ship_size_count_lost").getAsIntList();
        o1 = o.getObject("leader");
        this.leader = o1 != null ? o1.getAs(FleetCombatEnemyLeader::new) : null;
    }

    public FleetCombatEnemy(int fleet, int country, String shipClass, String countryName, String fleetName,
                            ImmutableIntList shipSizeCount, ImmutableIntList shipSizeCountLost,
                            ImmutableList<String> shipSizeKey, ImmutableList<String> shipSizeName, Flag empireFlag,
                            FleetCombatEnemyLeader leader) {
        this.fleet = fleet;
        this.country = country;
        this.shipClass = shipClass;
        this.countryName = countryName;
        this.fleetName = fleetName;
        this.shipSizeCount = shipSizeCount;
        this.shipSizeCountLost = shipSizeCountLost;
        this.shipSizeKey = shipSizeKey;
        this.shipSizeName = shipSizeName;
        this.empireFlag = empireFlag;
        this.leader = leader;
    }

    public int getFleet() {
        return fleet;
    }

    public int getCountry() {
        return country;
    }

    public String getShipClass() {
        return shipClass;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getFleetName() {
        return fleetName;
    }

    public ImmutableIntList getShipSizeCount() {
        return shipSizeCount;
    }

    public ImmutableIntList getShipSizeCountLost() {
        return shipSizeCountLost;
    }

    public ImmutableList<String> getShipSizeKey() {
        return shipSizeKey;
    }

    public ImmutableList<String> getShipSizeName() {
        return shipSizeName;
    }

    public Flag getEmpireFlag() {
        return empireFlag;
    }

    public FleetCombatEnemyLeader getLeader() {
        return leader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FleetCombatEnemy)) {
            return false;
        }
        FleetCombatEnemy that = (FleetCombatEnemy) o;
        return fleet == that.fleet && country == that.country && Objects.equals(shipClass, that.shipClass) && Objects.equals(countryName, that.countryName) && Objects.equals(fleetName, that.fleetName) && Objects.equals(shipSizeCount, that.shipSizeCount) && Objects.equals(shipSizeCountLost, that.shipSizeCountLost) && Objects.equals(shipSizeKey, that.shipSizeKey) && Objects.equals(shipSizeName, that.shipSizeName) && Objects.equals(empireFlag, that.empireFlag) && Objects.equals(leader, that.leader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fleet, country, shipClass, countryName, fleetName, shipSizeCount, shipSizeCountLost,
                shipSizeKey, shipSizeName, empireFlag, leader);
    }
}
