package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class TechnologyPreReqForDesc {

    public final String hidePrereqForDesc;
    public final Desc ship;
    public final Desc component;
    public final ImmutableList<Desc> custom;

    public TechnologyPreReqForDesc(PdxScriptObject o) {
        this.hidePrereqForDesc = o.getString("hide_prereq_for_desc", null);
        this.ship = o.getObjectAsNullOr("ship", Desc::new);
        this.component = o.getObjectAsNullOr("component", Desc::new);
        this.custom = o.getImplicitListAsList("custom", Desc::new);
    }

    public static class Desc {

        public final String title;
        public final String desc;

        public Desc(IPdxScript s) {
            PdxScriptObject o = s.expectObject();
            this.title = o.getString("title");
            this.desc = o.getString("desc");
        }
    }
}
