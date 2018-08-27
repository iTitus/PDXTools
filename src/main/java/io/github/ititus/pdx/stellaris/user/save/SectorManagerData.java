package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class SectorManagerData {

    private final long sector;
    private final ImmutableIntList planets;
    private final ImmutableList<SectorPlannedProject> plannedProjects;
    private final SectorColonyData colony;
    private final Budget budget;
    private final SectorBudgets sectorBudgets;

    public SectorManagerData(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.colony = o.getObject("colony").getAs(SectorColonyData::new);
        this.budget = o.getObject("budget").getAs(Budget::new);
        this.sector = o.getLong("sector");
        this.planets = o.getList("planets").getAsIntList();
        this.sectorBudgets = o.getObject("sector_budgets").getAs(SectorBudgets::new);
        this.plannedProjects = o.getList("planned_project").getAsList(SectorPlannedProject::new);
    }

    public SectorManagerData(long sector, ImmutableIntList planets, ImmutableList<SectorPlannedProject> plannedProjects, SectorColonyData colony, Budget budget, SectorBudgets sectorBudgets) {
        this.sector = sector;
        this.planets = planets;
        this.plannedProjects = plannedProjects;
        this.colony = colony;
        this.budget = budget;
        this.sectorBudgets = sectorBudgets;
    }

    public long getSector() {
        return sector;
    }

    public ImmutableIntList getPlanets() {
        return planets;
    }

    public ImmutableList<SectorPlannedProject> getPlannedProjects() {
        return plannedProjects;
    }

    public SectorColonyData getColony() {
        return colony;
    }

    public Budget getBudget() {
        return budget;
    }

    public SectorBudgets getSectorBudgets() {
        return sectorBudgets;
    }
}
