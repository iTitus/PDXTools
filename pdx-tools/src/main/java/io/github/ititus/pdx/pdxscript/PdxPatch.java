package io.github.ititus.pdx.pdxscript;

import java.util.List;
import java.util.function.UnaryOperator;

@FunctionalInterface
public interface PdxPatch extends UnaryOperator<List<String>> {
}
