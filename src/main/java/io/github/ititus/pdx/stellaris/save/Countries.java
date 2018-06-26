package io.github.ititus.pdx.stellaris.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
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

    public Map<Integer, Country> getCountries() {
        return Collections.unmodifiableMap(countries);
    }
}
