package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Starbase {

    private final List<ShipyardBuildQueueItem> shipyardBuildQueue;
    private final String level;
    private final StarbaseBuildings modules, buildings;
    private final int nextBuildItemId, nextShipyardBuildItemId, shipDesign, station, system, owner;

    public Starbase(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        IPdxScript s1 = o.get("shipyard_build_queue_item");
        if (s1 instanceof PdxScriptObject) {
            this.shipyardBuildQueue = CollectionUtil.listOf(((PdxScriptObject) s1).<ShipyardBuildQueueItem>getAs(ShipyardBuildQueueItem::new));
        } else if (s1 instanceof PdxScriptList) {
            this.shipyardBuildQueue = ((PdxScriptList) s1).getAsList(ShipyardBuildQueueItem::new);
        } else {
            this.shipyardBuildQueue = CollectionUtil.listOf();
        }
        this.level = o.getString("level");
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

    public Starbase(List<ShipyardBuildQueueItem> shipyardBuildQueue, String level, StarbaseBuildings modules, StarbaseBuildings buildings, int nextBuildItemId, int nextShipyardBuildItemId, int shipDesign, int station, int system, int owner) {
        this.shipyardBuildQueue = new ArrayList<>(shipyardBuildQueue);
        this.level = level;
        this.modules = modules;
        this.buildings = buildings;
        this.nextBuildItemId = nextBuildItemId;
        this.nextShipyardBuildItemId = nextShipyardBuildItemId;
        this.shipDesign = shipDesign;
        this.station = station;
        this.system = system;
        this.owner = owner;
    }

    public List<ShipyardBuildQueueItem> getShipyardBuildQueue() {
        return Collections.unmodifiableList(shipyardBuildQueue);
    }

    public String getLevel() {
        return level;
    }

    public StarbaseBuildings getModules() {
        return modules;
    }

    public StarbaseBuildings getBuildings() {
        return buildings;
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
}
