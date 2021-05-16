package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class QueueManager {

    public final ImmutableIntObjectMap<Queue> queues;

    public QueueManager(PdxScriptObject o) {
        this.queues = o.getObjectAsIntObjectMap("queues", Queue::new);
    }
}
