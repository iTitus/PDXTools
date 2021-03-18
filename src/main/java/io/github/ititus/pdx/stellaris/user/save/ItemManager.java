package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

import static io.github.ititus.pdx.pdxscript.PdxHelper.nullOr;

public class ItemManager {

    public final ImmutableIntObjectMap<QueueItem> items;

    public ItemManager(PdxScriptObject o) {
        this.items = o.getObjectAsIntObjectMap("items", nullOr(QueueItem::new));
    }
}
