package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Spaceport {

    private final int nextBuildItemId;
    private final ImmutableList<BuildQueueItem> buildQueue;

    public Spaceport(PdxScriptObject o) {
        this.nextBuildItemId = o.getInt("next_build_item_id");
        this.buildQueue = o.getImplicitList("build_queue_item").getAsList(BuildQueueItem::new);
    }

    public Spaceport(int nextBuildItemId, ImmutableList<BuildQueueItem> buildQueue) {
        this.nextBuildItemId = nextBuildItemId;
        this.buildQueue = buildQueue;
    }

    public int getNextBuildItemId() {
        return nextBuildItemId;
    }

    public ImmutableList<BuildQueueItem> getBuildQueue() {
        return buildQueue;
    }
}
