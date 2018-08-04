package io.github.ititus.pdx.pdxscript;

import java.util.function.BinaryOperator;

public enum PdxMathOperation implements PdxConstants {
    ADD(PdxConstants.ADD, (n1, n2) -> {
        if (n1 instanceof Double || n2 instanceof Double) {
            return n1.doubleValue() + n2.doubleValue();
        }
        if (n1 instanceof Long || n2 instanceof Long) {
            return n1.longValue() + n2.longValue();
        }
        return n1.intValue() + n2.intValue();
    }),
    SUBTRACT(PdxConstants.SUBTRACT, (n1, n2) -> {
        if (n1 instanceof Double || n2 instanceof Double) {
            return n1.doubleValue() - n2.doubleValue();
        }
        if (n1 instanceof Long || n2 instanceof Long) {
            return n1.longValue() - n2.longValue();
        }
        return n1.intValue() - n2.intValue();
    }),
    MULTIPLY(PdxConstants.MULTIPLY, (n1, n2) -> {
        if (n1 instanceof Double || n2 instanceof Double) {
            return n1.doubleValue() * n2.doubleValue();
        }
        if (n1 instanceof Long || n2 instanceof Long) {
            return n1.longValue() * n2.longValue();
        }
        return n1.intValue() * n2.intValue();
    }),
    DIVIDE(PdxConstants.DIVIDE, (n1, n2) -> {
        double d = n1.doubleValue() / n2.doubleValue();
        if (n1 instanceof Double || n2 instanceof Double) {
            return d;
        }
        if (n1 instanceof Long || n2 instanceof Long) {
            long l = n1.longValue() / n2.longValue();
            if (d != l) {
                return d;
            }
            return l;
        }
        int i = n1.intValue() / n2.intValue();
        if (d != i) {
            return d;
        }
        return i;
    });

    private final String operator;
    private final BinaryOperator<Number> operation;

    PdxMathOperation(String operator, BinaryOperator<Number> operation) {
        this.operator = operator;
        this.operation = operation;
    }

    public static PdxMathOperation get(String operator) {
        if (operator != null && !operator.isEmpty()) {
            if (operator.equals(PdxConstants.ADD)) {
                return ADD;
            } else if (operator.equals(PdxConstants.SUBTRACT)) {
                return SUBTRACT;
            } else if (operator.equals(PdxConstants.MULTIPLY)) {
                return MULTIPLY;
            } else if (operator.equals(PdxConstants.DIVIDE)) {
                return DIVIDE;
            }
        }
        return null;
    }

    public String getOperator() {
        return operator;
    }

    public Number apply(Number n1, Number n2) {
        return operation.apply(n1, n2);
    }
}
