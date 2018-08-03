package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Objects;

public class Spaceport {

    private static final Deduplicator<Spaceport> DEDUPLICATOR = new Deduplicator<>(s -> s.getBuildQueue().isEmpty());

    private final int nextBuildItemId;
    private final ImmutableList<BuildQueueItem> buildQueue;

    private Spaceport(PdxScriptObject o) {
        this.nextBuildItemId = o.getInt("next_build_item_id");
        this.buildQueue = o.getImplicitList("build_queue_item").getAsList(BuildQueueItem::new);
    }

    private Spaceport(int nextBuildItemId, ImmutableList<BuildQueueItem> buildQueue) {
        this.nextBuildItemId = nextBuildItemId;
        this.buildQueue = buildQueue;
    }

    public static Spaceport of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new Spaceport(o));
    }

    public static Spaceport of(int nextBuildItemId, ImmutableList<BuildQueueItem> buildQueue) {
        return DEDUPLICATOR.deduplicate(new Spaceport(nextBuildItemId, buildQueue));
    }

    public int getNextBuildItemId() {
        return nextBuildItemId;
    }

    public ImmutableList<BuildQueueItem> getBuildQueue() {
        return buildQueue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Spaceport)) {
            return false;
        }
        Spaceport spaceport = (Spaceport) o;
        return nextBuildItemId == spaceport.nextBuildItemId && Objects.equals(buildQueue, spaceport.buildQueue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nextBuildItemId, buildQueue);
    }
}
