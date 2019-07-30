package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.Deduplicator;

import java.time.LocalDate;
import java.util.Objects;

public final class PdxScriptValue implements IPdxScript {

    private static final Deduplicator<LocalDate> DATE_DEDUPLICATOR = new Deduplicator<>();
    private static final Deduplicator<PdxScriptValue> DEDUPLICATOR = new Deduplicator<>();

    private final PdxRelation relation;
    private final Object value;

    private PdxScriptValue(PdxRelation relation, Object value) {
        if (relation == null || (value != null && !(value instanceof Boolean) && !(value instanceof Number) && !(value instanceof LocalDate) && !(value instanceof PdxColorWrapper) && !(value instanceof String))) {
            throw new IllegalArgumentException(String.valueOf(value));
        }
        this.relation = relation;
        if (value instanceof String) {
            this.value = ((String) value).intern();
        } else if (value instanceof LocalDate) {
            this.value = DATE_DEDUPLICATOR.deduplicate((LocalDate) value);
        } else {
            this.value = value;
        }
    }

    public static PdxScriptValue of(PdxRelation relation, Object value) {
        return DEDUPLICATOR.deduplicate(new PdxScriptValue(relation, value));
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
        } else if (value instanceof LocalDate) {
            b.append(PdxScriptParser.quote(((LocalDate) value).format(DTF)));
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
        return "PdxScriptValue{relation=" + relation + ", value=" + value + '}';
    }
}
