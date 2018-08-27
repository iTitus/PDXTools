package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;

public class Budget {

    private final double popMineralMaintenance, consumerGoods, shipMineralMaintenances;
    private final ImmutableDoubleList popMaintenance;
    private final Variables values, buildingMaintenances, armyMaintenances, shipMaintenances;

    public Budget(PdxScriptObject o) {
        this.values = o.getObject("values").getAs(Variables::new);
        PdxScriptObject o1 = o.getObject("building_maintenances");
        this.buildingMaintenances = o1 != null ? o1.getAs(Variables::new) : null;
        o1 = o.getObject("army_maintenances");
        this.armyMaintenances = o1 != null ? o1.getAs(Variables::new) : null;
        o1 = o.getObject("ship_maintenances");
        this.shipMaintenances = o1 != null ? o1.getAs(Variables::new) : null;
        PdxScriptList l = o.getList("pop_maintenance");
        this.popMaintenance = l != null ? l.getAsDoubleList() : DoubleLists.immutable.empty();
        this.popMineralMaintenance = o.getDouble("pop_mineral_maintenance");
        this.consumerGoods = o.getDouble("consumer_goods");
        this.shipMineralMaintenances = o.getDouble("ship_mineral_maintenances");
    }

    public Budget(double popMineralMaintenance, double consumerGoods, double shipMineralMaintenances, ImmutableDoubleList popMaintenance, Variables values, Variables buildingMaintenances, Variables armyMaintenances, Variables shipMaintenances) {
        this.popMineralMaintenance = popMineralMaintenance;
        this.consumerGoods = consumerGoods;
        this.shipMineralMaintenances = shipMineralMaintenances;
        this.popMaintenance = popMaintenance;
        this.values = values;
        this.buildingMaintenances = buildingMaintenances;
        this.armyMaintenances = armyMaintenances;
        this.shipMaintenances = shipMaintenances;
    }

    public double getPopMineralMaintenance() {
        return popMineralMaintenance;
    }

    public double getConsumerGoods() {
        return consumerGoods;
    }

    public double getShipMineralMaintenances() {
        return shipMineralMaintenances;
    }

    public ImmutableDoubleList getPopMaintenance() {
        return popMaintenance;
    }

    public Variables getValues() {
        return values;
    }

    public Variables getBuildingMaintenances() {
        return buildingMaintenances;
    }

    public Variables getArmyMaintenances() {
        return armyMaintenances;
    }

    public Variables getShipMaintenances() {
        return shipMaintenances;
    }
}
