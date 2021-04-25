package io.github.ititus.pdx.stellaris.shared;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;
import org.eclipse.collections.api.set.ImmutableSet;

import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public class Modifier {

    private static final ImmutableSet<String> IGNORE = Sets.immutable.of(
            "icon",
            "icon_frame",
            "apply_modifier_to_other_planets",
            "description",
            "description_parameters",
            "show_only_custom_tooltip",
            "custom_tooltip"
    );
    private static final Predicate<String> FILTER = not(IGNORE::contains);

    public final String icon;
    public final int iconFrame;
    public final String applyModifierToOtherPlanets;
    public final Description description;
    public final boolean showOnlyCustomTooltip;
    public final String customTooltip;
    public final ImmutableObjectDoubleMap<String> modifiers;

    public Modifier(PdxScriptObject o) {
        this.icon = o.getString("icon", null);
        this.iconFrame = o.getInt("icon_frame", 0);
        this.applyModifierToOtherPlanets = o.getString("apply_modifier_to_other_planets", null);
        this.description = Description.createOrNull(o);
        this.showOnlyCustomTooltip = o.getBoolean("show_only_custom_tooltip", true);
        this.customTooltip = o.getString("custom_tooltip", null);
        this.modifiers = o.getAsStringDoubleMap(FILTER);
    }
}
