package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.util.Date;

public class Alliance {

    private final int leader, nextLeader;
    private final String name;
    private final Date startDate, nextRotation;
    private final ImmutableIntList members, associates, shipDesigns;

    public Alliance(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.members = o.getList("members").getAsIntList();
        PdxScriptList l = o.getList("associates");
        this.associates = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.startDate = o.getDate("start_date");
        this.nextRotation = o.getDate("next_rotation");
        this.shipDesigns = o.getList("ship_design").getAsIntList();
        this.leader = o.getInt("leader");
        this.nextLeader = o.getInt("next_leader");
    }

    public Alliance(int leader, int nextLeader, String name, Date startDate, Date nextRotation, ImmutableIntList members, ImmutableIntList associates, ImmutableIntList shipDesigns) {
        this.leader = leader;
        this.nextLeader = nextLeader;
        this.name = name;
        this.startDate = startDate;
        this.nextRotation = nextRotation;
        this.members = members;
        this.associates = associates;
        this.shipDesigns = shipDesigns;
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

    public ImmutableIntList getMembers() {
        return members;
    }

    public ImmutableIntList getAssociates() {
        return associates;
    }

    public ImmutableIntList getShipDesigns() {
        return shipDesigns;
    }
}
