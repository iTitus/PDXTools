package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.time.LocalDate;

public class Federation {

    private final int leader;
    private final String name;
    private final LocalDate startDate;
    private final ImmutableIntList members, associates, shipDesigns;

    public Federation(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.members = o.getList("members").getAsIntList();
        PdxScriptList l = o.getList("associates");
        this.associates = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.startDate = o.getDate("start_date");
        this.shipDesigns = o.getList("ship_design").getAsIntList();
        this.leader = o.getInt("leader");
    }

    public Federation(int leader, String name, LocalDate startDate, ImmutableIntList members,
                      ImmutableIntList associates, ImmutableIntList shipDesigns) {
        this.leader = leader;
        this.name = name;
        this.startDate = startDate;
        this.members = members;
        this.associates = associates;
        this.shipDesigns = shipDesigns;
    }

    public int getLeader() {
        return leader;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
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
