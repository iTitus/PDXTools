package io.github.ititus.pdx.pdxscript;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PdxScriptValue implements IPdxScript {

    private final Object value;

    public PdxScriptValue(Object value) {
        if (value != null && !(value instanceof Boolean) && !(value instanceof Number) && !(value instanceof Date) && !(value instanceof ColorWrapper) && !(value instanceof String)) {
            throw new IllegalArgumentException(String.valueOf(value));
        }
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public IPdxScript append(IPdxScript object) {
        return PdxScriptList.builder().add(this).add(object).build();
    }

    @Override
    public String toPdxScript(int indent, boolean bound, boolean indentFirst) {
        StringBuilder b = new StringBuilder(indentFirst ? PdxScriptParser.indent(indent) : "");
        if (value == null) {
            b.append(PdxScriptParser.NONE);
        } else if (value instanceof Boolean) {
            b.append((boolean) value ? PdxScriptParser.YES : PdxScriptParser.NO);
        } else if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(PdxScriptParser.SDF_PATTERN, Locale.ENGLISH);
            b.append(sdf.format(value));
        } else if (value instanceof String) {
            b.append(PdxScriptParser.quote((String) value));
        } else if (value instanceof ColorWrapper) {
            b.append(((ColorWrapper) value).toPdxScript());
        } else {
            b.append(value);
        }
        return b.toString();
    }

    @Override
    public String toString() {
        return "value = [" + value + "]";
    }

}
