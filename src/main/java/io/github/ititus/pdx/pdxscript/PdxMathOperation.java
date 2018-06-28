package io.github.ititus.pdx.pdxscript;

import java.util.function.BinaryOperator;

public enum PdxMathOperation {
    ADD(PdxScriptParser.ADD, (n1, n2) -> {
        if (n1 instanceof Double || n2 instanceof Double) {
            return n1.doubleValue() + n2.doubleValue();
        }
        if (n1 instanceof Long || n2 instanceof Long) {
            return n1.longValue() + n2.longValue();
        }
        return n1.intValue() + n2.intValue();
    }),
    /*SUBTRACT(PdxScriptParser.SUBTRACT, (n1, n2) -> {
        if (n1 instanceof Double || n2 instanceof Double) {
            return n1.doubleValue() - n2.doubleValue();
        }
        if (n1 instanceof Long || n2 instanceof Long) {
            return n1.longValue() - n2.longValue();
        }
        return n1.intValue() - n2.intValue();
    }),*/
    MULTIPLY(PdxScriptParser.MULTIPLY, (n1, n2) -> {
        if (n1 instanceof Double || n2 instanceof Double) {
            return n1.doubleValue() * n2.doubleValue();
        }
        if (n1 instanceof Long || n2 instanceof Long) {
            return n1.longValue() * n2.longValue();
        }
        return n1.intValue() * n2.intValue();
    }),
    DIVIDE(PdxScriptParser.DIVIDE, (n1, n2) -> {
        if (n1 instanceof Double || n2 instanceof Double) {
            return n1.doubleValue() / n2.doubleValue();
        }
        double d = n1.doubleValue() / n2.doubleValue();
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
        switch (operator) {
            case PdxScriptParser.ADD:
                return ADD;
            /*case PdxScriptParser.SUBTRACT:
                return SUBTRACT;*/
            case PdxScriptParser.MULTIPLY:
                return MULTIPLY;
            case PdxScriptParser.DIVIDE:
                return DIVIDE;
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
