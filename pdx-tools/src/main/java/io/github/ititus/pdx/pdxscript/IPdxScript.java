package io.github.ititus.pdx.pdxscript;

public interface IPdxScript {

    default boolean canAppend(IPdxScript script) {
        return true;
    }

    default PdxScriptList append(IPdxScript script) {
        return PdxScriptList.builder()
                .add(this)
                .add(script)
                .build(PdxScriptList.Mode.IMPLICIT);
    }

    default String toPdxScript() {
        return toPdxScript(0, true, null);
    }

    String toPdxScript(int indent, boolean root, String key);

    PdxRelation getRelation();

    String getTypeString();

    default PdxScriptValue expectValue() {
        throw new IllegalStateException("expected value but was " + this);
    }

    default void expectEmpty() {
        throw new IllegalStateException("expected empty list or object but was " + this);
    }

    default PdxScriptList expectList() {
        throw new IllegalStateException("expected explicit list but was " + this);
    }

    default PdxScriptList expectImplicitList() {
        return PdxScriptList.builder().add(this).build(PdxScriptList.Mode.IMPLICIT);
    }

    default PdxScriptObject expectObject() {
        throw new IllegalStateException("expected object but was " + this);
    }
}
