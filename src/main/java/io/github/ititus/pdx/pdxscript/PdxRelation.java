package io.github.ititus.pdx.pdxscript;

public enum PdxRelation {

    EQUALS(PdxConstants.EQUALS),
    LESS_THAN(PdxConstants.LESS_THAN),
    GREATER_THAN(PdxConstants.GREATER_THAN),
    NOT_EQUALS(PdxConstants.NOT_EQUALS),
    LESS_THAN_OR_EQUALS(PdxConstants.LESS_THAN_OR_EQUALS),
    GREATER_THAN_OR_EQUALS(PdxConstants.GREATER_THAN_OR_EQUALS);

    private final String sign;

    PdxRelation(String sign) {
        this.sign = sign;
    }

    public static PdxRelation get(String sign) {
        if (sign != null && !sign.isEmpty()) {
            if (sign.equals(PdxConstants.EQUALS)) {
                return EQUALS;
            } else if (sign.equals(PdxConstants.NOT_EQUALS)) {
                return NOT_EQUALS;
            } else if (sign.equals(PdxConstants.LESS_THAN)) {
                return LESS_THAN;
            } else if (sign.equals(PdxConstants.GREATER_THAN)) {
                return GREATER_THAN;
            } else if (sign.equals(PdxConstants.LESS_THAN_OR_EQUALS)) {
                return LESS_THAN_OR_EQUALS;
            } else if (sign.equals(PdxConstants.GREATER_THAN_OR_EQUALS)) {
                return GREATER_THAN_OR_EQUALS;
            }
        }

        return null;
    }

    public String getSign() {
        return sign;
    }
}
