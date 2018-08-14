package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;

public class SectorManager {

    private final ImmutableList<SectorManagerData> dataList;

    public SectorManager(PdxScriptObject o) {
        PdxScriptList l = o.getList("data");
        this.dataList = l != null ? l.getAsList(SectorManagerData::new) : Lists.immutable.empty();
    }

    public SectorManager(ImmutableList<SectorManagerData> dataList) {
        this.dataList = dataList;
    }

    public ImmutableList<SectorManagerData> getDataList() {
        return dataList;
    }
}
