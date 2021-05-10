package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Faction {

    public final boolean autoDelete;
    public final boolean neutral;
    public final boolean hostile;
    public final boolean needsBorderAccess;
    public final boolean generateBorders;
    public final boolean needsColony;
    public final int aggroRange;
    public final String aggroRangeMeasureFrom;
    public final boolean primitive;
    public final String primitiveAge;
    public final boolean spaceCreatures;

    public Faction(PdxScriptObject o) {
        this.autoDelete = o.getBoolean("auto_delete", true);
        this.neutral = o.getBoolean("neutral", false);
        this.hostile = o.getBoolean("hostile", false);
        this.needsBorderAccess = o.getBoolean("needs_border_access", true);
        this.generateBorders = o.getBoolean("generate_borders", true);
        this.needsColony = o.getBoolean("needs_colony", true);
        this.aggroRange = o.getInt("aggro_range");
        this.aggroRangeMeasureFrom = o.getString("aggro_range_measure_from");
        this.primitive = o.getBoolean("primitive", false);
        this.primitiveAge = o.getString("primitive_age", null);
        this.spaceCreatures = o.getBoolean("space_creatures", false);
    }
}
