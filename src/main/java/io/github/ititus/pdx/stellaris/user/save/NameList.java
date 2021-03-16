package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class NameList {

    private static final NameList INSTANCE = new NameList();

    private NameList() {
    }

    public static NameList of(PdxScriptObject o) {
        if (o.size() == 0) {
            return INSTANCE;
        }

        throw new IllegalStateException("unexpected content for name_list: " + o);
    }
}
