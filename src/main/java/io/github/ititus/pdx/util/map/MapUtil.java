package io.github.ititus.pdx.util.map;

import com.koloboke.collect.map.hash.HashObjObjMap;
import com.koloboke.collect.map.hash.HashObjObjMaps;

import java.util.Map;

public class MapUtil {

    public static <K1, K2, V2> Map<K1, Map<K2, V2>> toImmutableDeep(Map<K1, Map<K2, V2>> map) {
        return HashObjObjMaps.newImmutableMap(map.keySet(), (Iterable<HashObjObjMap<K2, V2>>) map.values().stream().map(HashObjObjMaps::newImmutableMap)::iterator);
    }

}
