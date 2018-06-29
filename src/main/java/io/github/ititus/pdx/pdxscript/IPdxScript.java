package io.github.ititus.pdx.pdxscript;

public interface IPdxScript {

    default boolean canAppend(IPdxScript script) {
        return true;
    }

    default PdxScriptList append(IPdxScript script) {
        return PdxScriptList.builder().add(this).add(script).build(true, PdxRelation.EQUALS);
    }

    default String toPdxScript() {
        return toPdxScript(0, false, false, null);
    }

    String toPdxScript(int indent, boolean bound, boolean indentFirst, String key);

    PdxRelation getRelation();

}
