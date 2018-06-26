package io.github.ititus.pdx.pdxscript;

public interface IPdxScript {

    default boolean canAppend(IPdxScript object) {
        return true;
    }

    IPdxScript append(IPdxScript object);

    default String toPdxScript() {
        return toPdxScript(0, false, false);
    }

    String toPdxScript(int indent, boolean bound, boolean indentFirst);

}
