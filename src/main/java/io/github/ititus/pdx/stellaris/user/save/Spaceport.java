package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Spaceport {

    private final int nextBuildItemId;
    private final List<BuildQueueItem> buildQueue;

    public Spaceport(PdxScriptObject o) {
        this.nextBuildItemId = o.getInt("next_build_item_id");
        IPdxScript s1 = o.get("build_queue_item");
        if (s1 instanceof PdxScriptObject) {
            this.buildQueue = CollectionUtil.listOf(new BuildQueueItem(s1));
            o.use("build_queue_item", PdxConstants.OBJECT);
        } else if (s1 instanceof PdxScriptList) {
            this.buildQueue = ((PdxScriptList) s1).getAsList(BuildQueueItem::new);
            o.use("build_queue_item", PdxConstants.LIST);
        } else {
            this.buildQueue = CollectionUtil.listOf();
        }
    }

    public Spaceport(int nextBuildItemId, Collection<BuildQueueItem> buildQueue) {
        this.nextBuildItemId = nextBuildItemId;
        this.buildQueue = new ArrayList<>(buildQueue);
    }

    public int getNextBuildItemId() {
        return nextBuildItemId;
    }

    public List<BuildQueueItem> getBuildQueue() {
        return Collections.unmodifiableList(buildQueue);
    }
}
