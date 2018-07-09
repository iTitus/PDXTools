package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Starbase {

    private final int updateFlag, nextBuildItemId, nextShipyardBuildItemId, shipDesign, station, system, owner;
    private final String level;
    private final List<BuildQueueItem> buildQueue, shipyardBuildQueue;
    private final StarbaseBuildings modules, buildings;

    public Starbase(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        IPdxScript s1 = o.get("build_queue_item");
        if (s1 instanceof PdxScriptObject) {
            this.buildQueue = CollectionUtil.listOf(new BuildQueueItem(s1));
            o.use("build_queue_item", PdxConstants.OBJECT);
        } else if (s1 instanceof PdxScriptList) {
            this.buildQueue = ((PdxScriptList) s1).getAsList(BuildQueueItem::new);
            o.use("build_queue_item", PdxConstants.LIST);
        } else {
            this.buildQueue = CollectionUtil.listOf();
        }
        s1 = o.get("shipyard_build_queue_item");
        if (s1 instanceof PdxScriptObject) {
            this.shipyardBuildQueue = CollectionUtil.listOf(new BuildQueueItem(s1));
            o.use("shipyard_build_queue_item", PdxConstants.OBJECT);
        } else if (s1 instanceof PdxScriptList) {
            this.shipyardBuildQueue = ((PdxScriptList) s1).getAsList(BuildQueueItem::new);
            o.use("shipyard_build_queue_item", PdxConstants.LIST);
        } else {
            this.shipyardBuildQueue = CollectionUtil.listOf();
        }
        this.level = o.getString("level");
        this.updateFlag = o.getInt("update_flag");
        PdxScriptObject o1 = o.getObject("modules");
        this.modules = o1 != null ? o1.getAs(StarbaseBuildings::new) : new StarbaseBuildings(Collections.emptyMap());
        o1 = o.getObject("buildings");
        this.buildings = o1 != null ? o1.getAs(StarbaseBuildings::new) : new StarbaseBuildings(Collections.emptyMap());
        this.nextBuildItemId = o.getInt("next_build_item_id");
        this.nextShipyardBuildItemId = o.getInt("next_shipyard_build_item_id");
        this.shipDesign = o.getInt("ship_design");
        this.station = o.getInt("station");
        this.system = o.getInt("system");
        this.owner = o.getInt("owner");
    }

    public Starbase(int updateFlag, int nextBuildItemId, int nextShipyardBuildItemId, int shipDesign, int station, int system, int owner, String level, Collection<BuildQueueItem> buildQueue, Collection<BuildQueueItem> shipyardBuildQueue, StarbaseBuildings modules, StarbaseBuildings buildings) {
        this.updateFlag = updateFlag;
        this.nextBuildItemId = nextBuildItemId;
        this.nextShipyardBuildItemId = nextShipyardBuildItemId;
        this.shipDesign = shipDesign;
        this.station = station;
        this.system = system;
        this.owner = owner;
        this.level = level;
        this.buildQueue = new ArrayList<>(buildQueue);
        this.shipyardBuildQueue = new ArrayList<>(shipyardBuildQueue);
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

    public List<BuildQueueItem> getBuildQueue() {
        return Collections.unmodifiableList(buildQueue);
    }

    public List<BuildQueueItem> getShipyardBuildQueue() {
        return Collections.unmodifiableList(shipyardBuildQueue);
    }

    public StarbaseBuildings getModules() {
        return modules;
    }

    public StarbaseBuildings getBuildings() {
        return buildings;
    }
}
