package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.map.ImmutableMap;

import java.time.LocalDate;

public class Federation {

    public final String name;
    public final FederationProgression federationProgression;
    public final ImmutableIntList members;
    public final ImmutableIntList associates;
    public final LocalDate startDate;
    public final ImmutableIntList shipDesigns;
    public final int leader;
    public final ImmutableMap<String, FlagData> flags;

    public Federation(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.federationProgression = o.getObjectAs("federation_progression", FederationProgression::new);
        this.members = o.getListAsIntList("members");
        this.associates = o.getListAsEmptyOrIntList("associates");
        this.startDate = o.getDate("start_date");
        this.shipDesigns = o.getListAsEmptyOrIntList("ship_design");
        this.leader = o.getInt("leader");
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
    }
}
