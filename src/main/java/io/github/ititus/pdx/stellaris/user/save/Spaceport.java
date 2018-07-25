package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableArrayList;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.Collection;
import java.util.List;

public class Spaceport {

    private final int nextBuildItemId;
    private final ViewableList<BuildQueueItem> buildQueue;

    public Spaceport(PdxScriptObject o) {
        this.nextBuildItemId = o.getInt("next_build_item_id");
        this.buildQueue = o.getImplicitList("build_queue_item").getAsList(BuildQueueItem::new);
    }

    public Spaceport(int nextBuildItemId, Collection<BuildQueueItem> buildQueue) {
        this.nextBuildItemId = nextBuildItemId;
        this.buildQueue = new ViewableArrayList<>(buildQueue);
    }

    public int getNextBuildItemId() {
        return nextBuildItemId;
    }

    public List<BuildQueueItem> getBuildQueue() {
        return buildQueue.getView();
    }
}
