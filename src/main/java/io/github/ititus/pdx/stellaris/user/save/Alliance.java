package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.ViewableArrayList;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Alliance {

    private final int leader, nextLeader;
    private final String name;
    private final Date startDate, nextRotation;
    private final ViewableList<Integer> members, associates, shipDesigns;

    public Alliance(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.members = o.getList("members").getAsIntegerList();
        PdxScriptList l = o.getList("associates");
        this.associates = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
        this.startDate = o.getDate("start_date");
        this.nextRotation = o.getDate("next_rotation");
        this.shipDesigns = o.getList("ship_design").getAsIntegerList();
        this.leader = o.getInt("leader");
        this.nextLeader = o.getInt("next_leader");
    }

    public Alliance(int leader, int nextLeader, String name, Date startDate, Date nextRotation, Collection<Integer> members, Collection<Integer> associates, Collection<Integer> shipDesigns) {
        this.leader = leader;
        this.nextLeader = nextLeader;
        this.name = name;
        this.startDate = startDate;
        this.nextRotation = nextRotation;
        this.members = new ViewableArrayList<>(members);
        this.associates = new ViewableArrayList<>(associates);
        this.shipDesigns = new ViewableArrayList<>(shipDesigns);
    }

    public int getLeader() {
        return leader;
    }

    public int getNextLeader() {
        return nextLeader;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getNextRotation() {
        return nextRotation;
    }

    public List<Integer> getMembers() {
        return members.getView();
    }

    public List<Integer> getAssociates() {
        return associates.getView();
    }

    public List<Integer> getShipDesigns() {
        return shipDesigns.getView();
    }
}
