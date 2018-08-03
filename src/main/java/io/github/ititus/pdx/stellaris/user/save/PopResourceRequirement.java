package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;

import java.util.Objects;

public class PopResourceRequirement {

    private static final Deduplicator<PopResourceRequirement> DEDUPLICATOR = new Deduplicator<>();

    private final double value;
    private final String type;

    private PopResourceRequirement(PdxScriptObject o) {
        this.type = o.getString("type");
        this.value = o.getDouble("value");
    }

    private PopResourceRequirement(double value, String type) {
        this.value = value;
        this.type = type;
    }

    public static PopResourceRequirement of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new PopResourceRequirement(o));
    }

    public static PopResourceRequirement of(double value, String type) {
        return DEDUPLICATOR.deduplicate(new PopResourceRequirement(value, type));
    }

    public double getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PopResourceRequirement)) {
            return false;
        }
        PopResourceRequirement that = (PopResourceRequirement) o;
        return Double.compare(that.value, value) == 0 && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}
