package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Faction {

    private final boolean autoDelete, hostile, needsBorderAccess, generateBorders, needsColony, primitive, spaceCreatures;
    private final double aggroRange;
    private final String aggroRangeMeasureFrom, primitiveAge;

    public Faction(PdxScriptObject o) {
        this.autoDelete = o.getBoolean("auto_delete", true);
        this.hostile = o.getBoolean("hostile");
        this.needsBorderAccess = o.getBoolean("needs_border_access", true);
        this.generateBorders = o.getBoolean("generate_borders", true);
        this.needsColony = o.getBoolean("needs_colony", true);
        this.aggroRange = o.getDouble("aggro_range");
        this.aggroRangeMeasureFrom = o.getString("aggro_range_measure_from");
        this.primitive = o.getBoolean("primitive");
        this.primitiveAge = o.getString("primitive_age");
        this.spaceCreatures = o.getBoolean("space_creatures");
    }

    public Faction(boolean autoDelete, boolean hostile, boolean needsBorderAccess, boolean generateBorders, boolean needsColony, boolean primitive, boolean spaceCreatures, double aggroRange, String aggroRangeMeasureFrom, String primitiveAge) {
        this.autoDelete = autoDelete;
        this.hostile = hostile;
        this.needsBorderAccess = needsBorderAccess;
        this.generateBorders = generateBorders;
        this.needsColony = needsColony;
        this.primitive = primitive;
        this.spaceCreatures = spaceCreatures;
        this.aggroRange = aggroRange;
        this.aggroRangeMeasureFrom = aggroRangeMeasureFrom;
        this.primitiveAge = primitiveAge;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public boolean isHostile() {
        return hostile;
    }

    public boolean isNeedsBorderAccess() {
        return needsBorderAccess;
    }

    public boolean isGenerateBorders() {
        return generateBorders;
    }

    public boolean isNeedsColony() {
        return needsColony;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public boolean isSpaceCreatures() {
        return spaceCreatures;
    }

    public double getAggroRange() {
        return aggroRange;
    }

    public String getAggroRangeMeasureFrom() {
        return aggroRangeMeasureFrom;
    }

    public String getPrimitiveAge() {
        return primitiveAge;
    }
}
