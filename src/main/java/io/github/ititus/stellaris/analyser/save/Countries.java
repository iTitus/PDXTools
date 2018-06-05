package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.HashMap;
import java.util.Map;

public class Countries {

    private final Map<Integer, Country> countries;

    public Countries(PdxScriptObject o) {
        this.countries = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(Country::new));
    }

    public Countries(Map<Integer, Country> countries) {
        this.countries = new HashMap<>(countries);
    }
}
