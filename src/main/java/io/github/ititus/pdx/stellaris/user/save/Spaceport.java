package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Spaceport {

    private final int nextBuildItemId;
    private final List<BuildQueueItem> buildQueue;

    public Spaceport(PdxScriptObject o) {
        this.nextBuildItemId = o.getInt("next_build_item_id");
        this.buildQueue = o.getImplicitList("build_queue_item").getAsList(BuildQueueItem::new);
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
