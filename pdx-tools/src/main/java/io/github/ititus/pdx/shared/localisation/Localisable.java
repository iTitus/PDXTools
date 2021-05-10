package io.github.ititus.pdx.shared.localisation;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import org.eclipse.collections.api.list.ImmutableList;

import static io.github.ititus.pdx.pdxscript.PdxConstants.DEFAULT_LANGUAGE;

public interface Localisable extends ExternalLocalisable {

    @Override
    default ImmutableList<String> localise(PdxLocalisation localisation) {
        return localise();
    }

    @Override
    default ImmutableList<String> localise(PdxLocalisation localisation, String language) {
        return localise(language);
    }

    default ImmutableList<String> localise() {
        return localise(DEFAULT_LANGUAGE);
    }

    ImmutableList<String> localise(String language);

}
