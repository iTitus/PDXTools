package io.github.ititus.pdx.shared.effect;

import io.github.ititus.pdx.pdxscript.IPdxScript;

@FunctionalInterface
public interface EffectFactory {

    Effect create(Effects effects, IPdxScript s);

}
