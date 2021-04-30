package io.github.ititus.pdx.shared.scope;

import io.github.ititus.pdx.pdxscript.PdxScriptValue;

public interface Scope {

    boolean evaluateValueTrigger(String name, PdxScriptValue v);

    Scope getScope(String name);

    String getName();

}
