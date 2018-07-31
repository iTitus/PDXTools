package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Starbase {

    private final int updateFlag, nextBuildItemId, nextShipyardBuildItemId, shipDesign, station, system, owner;
    private final String level;
    private final ImmutableList<BuildQueueItem> buildQueue, shipyardBuildQueue;
    private final StarbaseBuildings modules, buildings;

    public Starbase(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.buildQueue = o.getImplicitList("build_queue_item").getAsList(BuildQueueItem::new);
        this.shipyardBuildQueue = o.getImplicitList("shipyard_build_queue_item").getAsList(BuildQueueItem::new);
        this.level = o.getString("level");
        this.updateFlag = o.getInt("update_flag");
        PdxScriptObject o1 = o.getObject("modules");
        this.modules = o1 != null ? o1.getAs(StarbaseBuildings::new) : null;
        o1 = o.getObject("buildings");
        this.buildings = o1 != null ? o1.getAs(StarbaseBuildings::new) : null;
        this.nextBuildItemId = o.getInt("next_build_item_id");
        this.nextShipyardBuildItemId = o.getInt("next_shipyard_build_item_id");
        this.shipDesign = o.getInt("ship_design");
        this.station = o.getInt("station");
        this.system = o.getInt("system");
        this.owner = o.getInt("owner");
    }

    public Starbase(int updateFlag, int nextBuildItemId, int nextShipyardBuildItemId, int shipDesign, int station, int system, int owner, String level, ImmutableList<BuildQueueItem> buildQueue, ImmutableList<BuildQueueItem> shipyardBuildQueue, StarbaseBuildings modules, StarbaseBuildings buildings) {
        this.updateFlag = updateFlag;
        this.nextBuildItemId = nextBuildItemId;
        this.nextShipyardBuildItemId = nextShipyardBuildItemId;
        this.shipDesign = shipDesign;
        this.station = station;
        this.system = system;
        this.owner = owner;
        this.level = level;
        this.buildQueue = buildQueue;
        this.shipyardBuildQueue = shipyardBuildQueue;
        this.modules = modules;
        this.buildings = buildings;
    }

    public int getUpdateFlag() {
        return updateFlag;
    }

    public int getNextBuildItemId() {
        return nextBuildItemId;
    }

    public int getNextShipyardBuildItemId() {
        return nextShipyardBuildItemId;
    }

    public int getShipDesign() {
        return shipDesign;
    }

    public int getStation() {
        return station;
    }

    public int getSystem() {
        return system;
    }

    public int getOwner() {
        return owner;
    }

    public String getLevel() {
        return level;
    }

    public ImmutableList<BuildQueueItem> getBuildQueue() {
        return buildQueue;
    }

    public ImmutableList<BuildQueueItem> getShipyardBuildQueue() {
        return shipyardBuildQueue;
    }

    public StarbaseBuildings getModules() {
        return modules;
    }

    public StarbaseBuildings getBuildings() {
        return buildings;
    }
}
