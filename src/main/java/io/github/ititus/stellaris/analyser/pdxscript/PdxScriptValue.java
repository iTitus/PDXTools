package io.github.ititus.stellaris.analyser.pdxscript;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PdxScriptValue implements IPdxScript {

    private final Object value;

    public PdxScriptValue(Object value) {
        if (value != null && !(value instanceof Boolean) && !(value instanceof Number) && !(value instanceof Date) && !(value instanceof String)) {
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
        String s = indentFirst ? PdxScriptParser.indent(indent) : "";
        if (value == null) {
            s += PdxScriptParser.NONE;
        } else if (value instanceof Boolean) {
            s += (Boolean) value ? PdxScriptParser.YES : PdxScriptParser.NO;
        } else if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(PdxScriptParser.SDF_PATTERN, Locale.ENGLISH);
            s += sdf.format(value);
        } else if (value instanceof String) {
            s += PdxScriptParser.quote((String) value);
        } else {
            s += String.valueOf(value);
        }
        return s;
    }

    @Override
    public String toString() {
        return "value = [" + value + "]";
    }

}
