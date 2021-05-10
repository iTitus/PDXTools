package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class FleetCombatatant {

    public final int fleet, country;
    public final String shipClass, countryName, fleetName;
    public final ImmutableIntList shipSizeCount, shipSizeCountLost;
    public final ImmutableList<String> shipSizeKey, shipSizeName;
    public final Flag empireFlag;
    public final FleetCombatEnemyLeader leader;

    public FleetCombatatant(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.fleet = o.getInt("fleet", -1);
        this.shipClass = o.getString("ship_class", null);
        this.country = o.getInt("country", -1);
        this.empireFlag = o.getObjectAsNullOr("empire_flag", Flag::new);
        this.countryName = o.getString("country_name", null);
        this.fleetName = o.getString("fleet_name", null);
        this.shipSizeKey = o.getListAsEmptyOrStringList("ship_size_key");
        this.shipSizeName = o.getListAsEmptyOrStringList("ship_size_name");
        this.shipSizeCount = o.getListAsEmptyOrIntList("ship_size_count");
        this.shipSizeCountLost = o.getListAsEmptyOrIntList("ship_size_count_lost");
        this.leader = o.getObjectAsNullOr("leader", FleetCombatEnemyLeader::new);
    }
}
