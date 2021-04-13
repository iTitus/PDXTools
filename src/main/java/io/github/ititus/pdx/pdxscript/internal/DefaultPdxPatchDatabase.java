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
