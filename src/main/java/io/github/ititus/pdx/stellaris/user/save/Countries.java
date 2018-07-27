package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Countries {

    private final IntObjMap<Country> countries;

    public Countries(PdxScriptObject o) {
        this.countries = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(Country::new));
    }

    public Countries(IntObjMap<Country> countries) {
        this.countries = HashIntObjMaps.newImmutableMap(countries);
    }

    public IntObjMap<Country> getCountries() {
        return countries;
    }
}
