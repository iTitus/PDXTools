package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;

@FunctionalInterface
public interface TriggerFactory {

    Trigger create(Triggers triggers, IPdxScript s);

}
