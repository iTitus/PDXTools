package io.github.ititus.pdx.pdxscript;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;

import java.util.Comparator;
import java.util.Map;

public class PdxUsageStatistic implements PdxConstants {

    private final MutableMap<String, PdxUsage> usages;

    public PdxUsageStatistic(ImmutableMap<String, IPdxScript> map) {
        this.usages = Maps.mutable.empty();
        map.forEachKeyValue(this::use);
    }

    public PdxUsageStatistic(MutableMap<String, PdxUsage> usages) {
        this.usages = usages;
    }

    private void use(String key, IPdxScript actual) {
        usages.computeIfAbsent(key, k -> new PdxUsage()).actual(PdxConstants.getTypeString(actual));
    }

    public void use(String key, String expectedType, IPdxScript actual) {
        usages.computeIfAbsent(key, k -> new PdxUsage()).expected(expectedType).actual(PdxConstants.getTypeString(actual));
    }

    public ImmutableMap<String, PdxUsage> getUsages() {
        return usages.toImmutable();
    }

    public ImmutableList<String> getErrorStrings() {
        MutableList<String> strings = Lists.mutable.empty();
        usages.entrySet().stream().filter(e -> e.getValue().isError()).sorted(Comparator.comparing(Map.Entry::getKey)).map(p -> p.getKey() + " = " + p.getValue()).forEachOrdered(strings::add);
        return strings.toImmutable();
    }
}
