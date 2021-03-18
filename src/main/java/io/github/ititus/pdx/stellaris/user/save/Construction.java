package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Construction {

    public final QueueManager queueManager;
    public final ItemManager itemManager;

    public Construction(PdxScriptObject o) {
        this.queueManager = o.getObjectAs("queue_mgr", QueueManager::new);
        this.itemManager = o.getObjectAs("item_mgr", ItemManager::new);
    }
}
