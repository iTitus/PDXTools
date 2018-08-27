package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;

public class SectorBudgets {

    private final ImmutableDoubleList maintenance, energy, minerals;

    public SectorBudgets(PdxScriptObject o) {
        this.maintenance = o.getList("maintenance").getAsDoubleList();
        this.energy = o.getList("energy").getAsDoubleList();
        this.minerals = o.getList("minerals").getAsDoubleList();
    }

    public SectorBudgets(ImmutableDoubleList maintenance, ImmutableDoubleList energy, ImmutableDoubleList minerals) {
        this.maintenance = maintenance;
        this.energy = energy;
        this.minerals = minerals;
    }

    public ImmutableDoubleList getMaintenance() {
        return maintenance;
    }

    public ImmutableDoubleList getEnergy() {
        return energy;
    }

    public ImmutableDoubleList getMinerals() {
        return minerals;
    }
}
