package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class BuildQueueItem {

    public final int planet;
    public final int slot;
    public final int id;
    public final double progress;
    public final BuildItem item;
    public final Resources cost;

    public BuildQueueItem(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.item = o.getObjectAs("item", BuildItem::new);
        this.progress = o.getDouble("progress");
        this.planet = o.getInt("planet");
        // TODO: paying_country, sector_build
        this.slot = o.getInt("slot", -1);
        this.cost = o.getObjectAs("cost", Resources::new);
        this.id = o.getInt("id");
    }
}
