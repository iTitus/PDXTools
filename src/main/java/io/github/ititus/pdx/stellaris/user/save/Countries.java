package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Countries {

    private final ImmutableIntObjectMap<Country> countries;

    public Countries(PdxScriptObject o) {
        this.countries = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(Country::new));
    }

    public Countries(ImmutableIntObjectMap<Country> countries) {
        this.countries = countries;
    }

    public ImmutableIntObjectMap<Country> getCountries() {
        return countries;
    }
}
