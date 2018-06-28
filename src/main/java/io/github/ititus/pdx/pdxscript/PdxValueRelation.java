package io.github.ititus.pdx.pdxscript;

enum PdxValueRelation {
    EQUALS(PdxScriptParser.EQUALS),
    LESS_THAN(PdxScriptParser.LESS_THAN),
    GREATER_THAN(PdxScriptParser.GREATER_THAN),
    LESS_THAN_OR_EQUALS(PdxScriptParser.LESS_THAN_OR_EQUALS),
    GREATER_THAN_OR_EQUALS(PdxScriptParser.GREATER_THAN_OR_EQUALS);

    private final String sign;

    PdxValueRelation(String sign) {
        this.sign = sign;
    }

    public static PdxValueRelation get(String sign) {
        switch (sign) {
            case PdxScriptParser.EQUALS:
                return EQUALS;
            case PdxScriptParser.LESS_THAN:
                return LESS_THAN;
            case PdxScriptParser.GREATER_THAN:
                return GREATER_THAN;
            case PdxScriptParser.LESS_THAN_OR_EQUALS:
                return LESS_THAN_OR_EQUALS;
            case PdxScriptParser.GREATER_THAN_OR_EQUALS:
                return GREATER_THAN_OR_EQUALS;
        }
        return null;
    }

    public String getSign() {
        return sign;
    }
}
