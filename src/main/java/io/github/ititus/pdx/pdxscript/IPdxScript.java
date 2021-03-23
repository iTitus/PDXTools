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
        if (this instanceof PdxScriptValue) {
            return (PdxScriptValue) this;
        }

        throw new IllegalStateException("expected value but was " + this);
    }

    default PdxScriptList expectList() {
        if (this instanceof PdxScriptList) {
            return (PdxScriptList) this;
        }

        throw new IllegalStateException("expected list but was " + this);
    }

    default PdxScriptList expectImplicitList() {
        if (this instanceof PdxScriptList) {
            PdxScriptList l = (PdxScriptList) this;
            if (l.getMode() == PdxScriptList.Mode.IMPLICIT) {
                return l;
            }
        }

        return PdxScriptList.builder().add(this).build(PdxScriptList.Mode.IMPLICIT);
    }

    default PdxScriptObject expectObject() {
        if (this instanceof PdxScriptObject) {
            return (PdxScriptObject) this;
        }

        throw new IllegalStateException("expected object but was " + this);
    }
}
