package io.github.ititus.pdx.pdxscript.internal;

import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;
import io.github.ititus.pdx.pdxscript.PdxPatch;
import io.github.ititus.pdx.pdxscript.PdxPatchDatabase;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultPdxPatchDatabase implements PdxPatchDatabase {

    public static final DefaultPdxPatchDatabase INSTANCE = new DefaultPdxPatchDatabase();

    private final Map<Path, PdxPatch> patches = Map.ofEntries(
            patch("""
                    --- a/Stellaris/common/random_names/00_empire_names.txt
                    +++ b/Stellaris/common/random_names/00_empire_names.txt
                    @@ -1154,34 +1154,34 @@
                     		"Destroyers" = 1
                     		"Reavers" = 1
                     		"Bloodletters" = 1
                    -		"Annihilators"
                    -		"Exterminators"
                    -		"Eradicators"
                    -		"Despoilers"
                    -		"Purifiers"
                    -		"Cleansers"
                    -		"Berserkers"
                    -		"Skull Lords"
                    -		"Bloodborn"
                    -		"Executioners"
                    -		"Heralds of Death"
                    -		"Desolators"
                    -		"Ravagers"
                    -		"Punishers"
                    -		"Bloodborn"
                    -		"Chosen"
                    -		"Death Bringers"
                    -		"Crusaders"
                    -		"Slaughterers"
                    -		"Butchers"
                    -		"Star Hunters"
                    -		"Euthanizers"
                    -		"Silencers"
                    -		"Death Lords"
                    -		"Decimators"
                    -		"Obliterators"
                    -		"Slayers"
                    -		"Reapers"
                    +		"Annihilators" = 1
                    +		"Exterminators" = 1
                    +		"Eradicators" = 1
                    +		"Despoilers" = 1
                    +		"Purifiers" = 1
                    +		"Cleansers" = 1
                    +		"Berserkers" = 1
                    +		"Skull Lords" = 1
                    +		"Bloodborn" = 1
                    +		"Executioners" = 1
                    +		"Heralds of Death" = 1
                    +		"Desolators" = 1
                    +		"Ravagers" = 1
                    +		"Punishers" = 1
                    +		"Bloodborn" = 1
                    +		"Chosen" = 1
                    +		"Death Bringers" = 1
                    +		"Crusaders" = 1
                    +		"Slaughterers" = 1
                    +		"Butchers" = 1
                    +		"Star Hunters" = 1
                    +		"Euthanizers" = 1
                    +		"Silencers" = 1
                    +		"Death Lords" = 1
                    +		"Decimators" = 1
                    +		"Obliterators" = 1
                    +		"Slayers" = 1
                    +		"Reapers" = 1
                     	}
                     }
                    \s
                    """),
            patch("""
                    --- a/Stellaris/common/random_names/00_war_names.txt
                    +++ b/Stellaris/common/random_names/00_war_names.txt
                    @@ -13,7 +13,7 @@
                     		"Great_War" = 1
                     		"Galactic_War" = 1
                     		"War_of_the_Alliances" = 1
                    -		"Federation_War"
                    +		"Federation_War" = 1
                     	}
                     }
                    \s
                    """),
            patch("""
                    --- a/Stellaris/common/technology/00_soc_tech.txt
                    +++ b/Stellaris/common/technology/00_soc_tech.txt
                    @@ -4119,7 +4119,6 @@
                     	is_dangerous = yes
                     	prerequisites = { "tech_precognition_interface" }
                     	weight = @tier5weight2
                    -	is_dangerous = yes
                     	is_reverse_engineerable = no
                    \s
                     	feature_flags = {
                    """),
            patch("""
                    --- a/Stellaris/dlc_metadata/dlc_info.txt
                    +++ b/Stellaris/dlc_metadata/dlc_info.txt
                    @@ -34,7 +34,7 @@
                     		steam_id = "447680"
                     		microsoft_store_id = "9MT1H9QPRPZG"
                     		gog_store_id = ""
                    -		paradoxplaza_store_url ""
                    +		paradoxplaza_store_url = ""
                     		category = "content_pack"
                     		gui = "arachnoid"
                     		icon = "GFX_arachnoid"
                    """),
            patch("""
                    --- a/Stellaris/gfx/models/planets/_planetary_entities.asset
                    +++ b/Stellaris/gfx/models/planets/_planetary_entities.asset
                    @@ -5010,7 +5010,6 @@
                     	default_state = "idle"
                     	state = { name = "idle" animation = "idle" time_offset = { 0 100 }\s
                     		start_event = { trigger_once = yes sound = { soundeffect = "amb_planet_world_relic" } }
                    -		}
                     	}
                     \t
                     }
                    """),
            patch("""
                    --- a/Stellaris/gfx/models/ships/caravaneer_01/_caravaneer_01_ships_entities.asset
                    +++ b/Stellaris/gfx/models/ships/caravaneer_01/_caravaneer_01_ships_entities.asset
                    @@ -240,8 +240,8 @@
                     	game_data = {
                     		trail_locators = {
                     			"engine_large_01" = 		{ width = @large_trail_W 	lenght = @large_trail_L   	}
                    -			"engine_medium_01" = 		{ width = @mnedium_trail_W 	lenght = @medium_trail_L   	}
                    -			"engine_medium_02" = 		{ width = @mnedium_trail_W 	lenght = @medium_trail_L   	}
                    +			"engine_medium_01" = 		{ width = @medium_trail_W 	lenght = @medium_trail_L   	}
                    +			"engine_medium_02" = 		{ width = @medium_trail_W 	lenght = @medium_trail_L   	}
                     		}
                     	}
                     	attach = { "root" = "caravaneer_cruiser_01_lights_entity" }
                    """),
            patch("""
                    --- a/Stellaris/gfx/models/ships/titans/mammalian_01/_mammalian_01_titan_meshes.gfx
                    +++ b/Stellaris/gfx/models/ships/titans/mammalian_01/_mammalian_01_titan_meshes.gfx
                    @@ -64,5 +64,3 @@
                     		}
                     	}
                     }
                    -
                    -}
                    """),
            patch("""
                    --- a/Stellaris/gfx/models/ships/titans/molluscoid_01/_molluscoid_titan_meshes.gfx
                    +++ b/Stellaris/gfx/models/ships/titans/molluscoid_01/_molluscoid_titan_meshes.gfx
                    @@ -64,5 +64,3 @@
                     		}
                     	}
                     }
                    -
                    -}
                    """),
            patch("""
                    --- a/Stellaris/gfx/particles/_necroid_portrait.gfx
                    +++ b/Stellaris/gfx/particles/_necroid_portrait.gfx
                    @@ -6,7 +6,4 @@
                     	\t
                     		scale = 1.0
                     	}
                    -
                    -
                    -
                    -
                    +}
                    """),
            patch("""
                    --- a/Stellaris/gfx/particles/_particle_entities.asset
                    +++ b/Stellaris/gfx/particles/_particle_entities.asset
                    @@ -115,7 +115,6 @@
                    \s
                     entity = {
                     	name = "stellarite_remnant_particle_entity"
                    -	scale = 1
                    \s
                     	locator = {
                     		name = "root"
                    """),
            patch("""
                    --- a/Stellaris/sound/soundeffects.asset
                    +++ b/Stellaris/sound/soundeffects.asset
                    @@ -1304,7 +1304,7 @@
                     	}\s
                     	max_audible = 2
                     	max_audible_behaviour = fail
                    -	playbackrate_random_offset { -5.0 5.0 }
                    +	playbackrate_random_offset = { -5.0 5.0 }
                     	is3d = yes
                     	volume = 0.25
                     }
                    @@ -1316,7 +1316,7 @@
                     	}\s
                     	max_audible = 2
                     	max_audible_behaviour = fail
                    -	playbackrate_random_offset { -5.0 5.0 }
                    +	playbackrate_random_offset = { -5.0 5.0 }
                     	is3d = yes
                     	volume = 0.25
                     }
                    """)
    );

    private DefaultPdxPatchDatabase() {
    }

    private static Map.Entry<Path, PdxPatch> patch(String diff) {
        int start = diff.indexOf("--- ");
        if (start < 0) {
            throw new IllegalArgumentException("invalid diff");
        }

        start += "--- ".length();
        int firstLineEnd = diff.indexOf('\n', start);
        int firstTab = diff.indexOf('\t', start);
        int end = firstTab >= 0 && firstTab < firstLineEnd ? firstTab : firstLineEnd;
        String path = diff.substring(start, end);

        String realPath;
        if (path.startsWith("a/")) {
            realPath = path.substring("a/".length());
        } else {
            realPath = path;
        }

        List<String> patchLines = Arrays.asList(diff.split("\n"));
        Patch<String> patch = UnifiedDiffUtils.parseUnifiedDiff(patchLines);
        return Map.entry(
                Path.of(realPath),
                lines -> {
                    try {
                        return patch.applyTo(lines);
                    } catch (PatchFailedException e) {
                        throw new RuntimeException("unable to patch pdx script file " + realPath, e);
                    }
                }
        );
    }

    @Override
    public Optional<PdxPatch> findPatch(Path scriptFile) {
        Path p;
        try {
            p = scriptFile.toRealPath();
        } catch (IOException e) {
            throw new UncheckedIOException("unable to normalize given path", e);
        }

        for (Map.Entry<Path, PdxPatch> entry : patches.entrySet()) {
            if (p.endsWith(entry.getKey())) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }
}
