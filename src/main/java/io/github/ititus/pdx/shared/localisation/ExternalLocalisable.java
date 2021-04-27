package io.github.ititus.pdx.shared.localisation;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import org.eclipse.collections.api.list.ImmutableList;

import static io.github.ititus.pdx.pdxscript.PdxConstants.DEFAULT_LANGUAGE;

public interface ExternalLocalisable {


    default ImmutableList<String> localise(PdxLocalisation localisation) {
        return localise(localisation, DEFAULT_LANGUAGE);
    }

    ImmutableList<String> localise(PdxLocalisation localisation, String language);

}
