package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableLongObjectMap;

public class ItemManager {

    public final ImmutableLongObjectMap<QueueItem> items;

    public ItemManager(PdxScriptObject o) {
        this.items = o.getObjectAsLongObjectMap("items", QueueItem::new);
    }
}
