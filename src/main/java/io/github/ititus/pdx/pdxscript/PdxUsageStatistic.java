package io.github.ititus.pdx.pdxscript;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;

import java.util.Comparator;
import java.util.Map;

import static io.github.ititus.pdx.pdxscript.PdxConstants.NULL;
import static io.github.ititus.pdx.pdxscript.PdxConstants.NUMBER_MARKER;

public final class PdxUsageStatistic {

    private static final boolean ENABLE_TRACKING = false;

    private final MutableMap<String, PdxUsage> usages;

    public PdxUsageStatistic() {
        this(Maps.mutable.empty());
    }

    PdxUsageStatistic(MutableMap<String, PdxUsage> usages) {
        this.usages = usages;
    }

    public PdxUsageStatistic init(Map<String, IPdxScript> map) {
        if (ENABLE_TRACKING) {
            map.forEach(this::use);
        }

        return this;
    }

    private void use(String key, IPdxScript actual) {
        if (ENABLE_TRACKING) {
            String fKey = key.chars().allMatch(PdxScriptParser::isDigit) ? NUMBER_MARKER : key;
            usages.computeIfAbsent(fKey, k -> new PdxUsage()).actual(PdxHelper.getTypeString(actual));
        }
    }

    public void use(String key, String expectedType, IPdxScript actual) {
        if (ENABLE_TRACKING) {
            String fKey = key.chars().allMatch(PdxScriptParser::isDigit) ? NUMBER_MARKER : key;
            usages.computeIfAbsent(fKey, k -> new PdxUsage()).expected(expectedType).actual(PdxHelper.getTypeString(actual));
        }
    }

    ImmutableMap<String, PdxUsage> getUsages() {
        return usages.toImmutable();
    }

    public ImmutableList<String> getErrorStrings() {
        Comparator<Boolean> trueFirst = (b1, b2) -> {
            if (b1 == b2) {
                return 0;
            }

            return b1 ? -1 : 1;
        };
        Comparator<String> byName = (s1, s2) -> {
            String[] p1 = s1.split("\\.");
            String[] p2 = s2.split("\\.");
            int minLength = Math.min(p1.length, p2.length);

            for (int i = 0; i < minLength; i++) {
                int c = p1[i].compareTo(p2[i]);
                if (c != 0) {
                    return c;
                }
            }

            if (p1.length > minLength) {
                return 1;
            } else if (p2.length > minLength) {
                return -1;
            }

            return 0;
        };
        Comparator<Map.Entry<String, PdxUsage>> byTypes = Map.Entry.
                <String, PdxUsage>comparingByValue(
                        Comparator.comparing((PdxUsage u) -> u.getExpectedTypes().isEmpty(), trueFirst)
                                .thenComparing((PdxUsage u) -> {
                                    ImmutableSet<String> actualTypes = u.getActualTypes();
                                    return actualTypes.size() == 1 && NULL.equals(actualTypes.getOnly());
                                }, trueFirst.reversed())
                )
                .thenComparing(Map.Entry.comparingByKey(byName));

        MutableList<String> strings = Lists.mutable.empty();
        usages.entrySet().stream()
                .filter(e -> e.getValue().isError())
                .sorted(byTypes)
                .map(e -> e.getKey() + " = " + e.getValue())
                .forEachOrdered(strings::add);
        return strings.toImmutable();
    }
}
