package io.github.ititus.pdx.pdxscript;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public final class PdxScriptValue implements IPdxScript {

    private final PdxRelation relation;
    private final Object value;

    public PdxScriptValue(PdxRelation relation, Object value) {
        if (relation == null || (value != null && !(value instanceof Boolean) && !(value instanceof Number) && !(value instanceof Date) && !(value instanceof PdxColorWrapper) && !(value instanceof String))) {
            throw new IllegalArgumentException(String.valueOf(value));
        }
        this.relation = relation;
        if (value instanceof String) {
            this.value = ((String) value).intern();
        } else if (value instanceof Date && value.equals(START_DATE)) {
            this.value = START_DATE;
        } else {
            this.value = value;
        }
    }

    @Override
    public PdxRelation getRelation() {
        return relation;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toPdxScript(int indent, boolean root, String key) {
        if (root) {
            throw new IllegalArgumentException();
        }

        StringBuilder b = new StringBuilder(PdxScriptParser.indent(indent));

        if (key != null) {
            b.append(PdxScriptParser.quoteIfNecessary(key));
            b.append(relation.getSign());
        }

        if (value == null) {
            b.append(NONE);
        } else if (value instanceof Boolean) {
            b.append((boolean) value ? YES : NO);
        } else if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(SDF_PATTERN, Locale.ENGLISH);
            b.append(PdxScriptParser.quote(sdf.format(value)));
        } else if (value instanceof String) {
            b.append(PdxScriptParser.quote((String) value));
        } else if (value instanceof PdxColorWrapper) {
            b.append(((PdxColorWrapper) value).toPdxScript());
        } else if (value instanceof Number) {
            b.append(value);
        } else {
            throw new IllegalArgumentException();
        }

        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdxScriptValue)) {
            return false;
        }
        PdxScriptValue that = (PdxScriptValue) o;
        return relation == that.relation && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relation, value);
    }

    @Override
    public String toString() {
        return "PdxScriptValue{" +
                "relation=" + relation +
                ", value=" + value +
                '}';
    }
}
