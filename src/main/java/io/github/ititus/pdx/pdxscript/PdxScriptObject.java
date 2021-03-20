package io.github.ititus.pdx.pdxscript;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.*;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.primitive.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.github.ititus.pdx.pdxscript.PdxConstants.*;
import static io.github.ititus.pdx.pdxscript.PdxHelper.getTypeString;

public final class PdxScriptObject implements IPdxScript {

    private final PdxRelation relation;
    private final ImmutableMap<String, IPdxScript> map;
    private final PdxUsageStatistic usageStatistic;

    private PdxScriptObject(PdxRelation relation, ImmutableMap<String, IPdxScript> map) {
        this.relation = relation;
        this.map = map;
        this.usageStatistic = new PdxUsageStatistic().init(this.map);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public PdxRelation getRelation() {
        return relation;
    }

    public int size() {
        return map.size();
    }

    public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    public boolean hasKey(String key, String type) {
        return map.containsKey(key) && getTypeString(getRaw(key)).equals(type);
    }

    public IPdxScript getRaw(String key) {
        return map.get(key);
    }

    public IPdxScript get(String key) {
        IPdxScript raw = getRaw(key);
        if (raw == null) {
            throw new NoSuchElementException("given key contains no value");
        }

        return raw;
    }

    public IPdxScript get(String key, IPdxScript def) {
        IPdxScript raw = getRaw(key);
        return raw != null ? raw : def;
    }

    public String getString(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, STRING, s);
        return extractString(key, s);
    }

    public String getNullOrString(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, STRING, s);
        return extractNullOrString(key, s);
    }

    public String getString(String key, String def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, STRING, s);
        if (s == null) {
            return def;
        }

        return extractString(key, s);
    }

    public boolean getBoolean(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, BOOLEAN, s);
        return extractBoolean(key, s);
    }

    public boolean getBoolean(String key, boolean def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, BOOLEAN, s);
        if (s == null) {
            return def;
        }

        return extractBoolean(key, s);
    }

    public int getUnsignedInt(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, U_INT, s);
        return extractUnsignedInt(key, s);
    }

    public int getUnsignedInt(String key, int def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, U_INT, s);
        if (s == null) {
            return def;
        }

        return extractUnsignedInt(key, s);
    }

    public int getInt(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, INT, s);
        return extractInt(key, s);
    }

    public int getInt(String key, int def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, INT, s);
        if (s == null) {
            return def;
        }

        return extractInt(key, s);
    }

    public long getLong(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, LONG, s);
        return extractLong(key, s);
    }

    public long getLong(String key, long def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, LONG, s);
        if (s == null) {
            return def;
        }

        return extractLong(key, s);
    }

    public double getDouble(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, DOUBLE, s);
        return extractDouble(key, s);
    }

    public double getDouble(String key, double def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, DOUBLE, s);
        if (s == null) {
            return def;
        }

        return extractDouble(key, s);
    }

    public LocalDate getDate(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, DATE, s);
        return extractDate(key, s);
    }

    public LocalDate getDate(String key, LocalDate def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, DATE, s);
        if (s == null) {
            return def;
        }

        return extractDate(key, s);
    }

    public PdxColor getColor(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, COLOR, s);
        return extractColor(key, s);
    }

    public PdxColor getColor(String key, PdxColor def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, COLOR, s);
        if (s == null) {
            return def;
        }

        return extractColor(key, s);
    }

    public PdxScriptList getImplicitList(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, IMPLICIT_LIST, s);
        return extractImplicitList(key, s);
    }

    public <T> ImmutableList<T> getImplicitListAsList(String key, Function<? super IPdxScript, ? extends T> fct) {
        return getImplicitList(key).getAsList(fct);
    }

    public ImmutableIntList getImplicitListAsIntList(String key) {
        return getImplicitList(key).getAsIntList();
    }

    public ImmutableIntList getImplicitListAsUnsignedIntList(String key) {
        return getImplicitList(key).getAsUnsignedIntList();
    }

    public ImmutableLongList getImplicitListAsLongList(String key) {
        return getImplicitList(key).getAsLongList();
    }

    public ImmutableDoubleList getImplicitListAsDoubleList(String key) {
        return getImplicitList(key).getAsDoubleList();
    }

    public ImmutableList<String> getImplicitListAsStringList(String key) {
        return getImplicitList(key).getAsStringList();
    }

    public ImmutableList<LocalDate> getImplicitListAsDateList(String key) {
        return getImplicitList(key).getAsDateList();
    }

    public ImmutableList<PdxColor> getImplicitListAsColorList(String key) {
        return getImplicitList(key).getAsColorList();
    }

    public PdxScriptList getList(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, LIST, s);
        return extractList(key, s);
    }

    public PdxScriptList getListOrEmpty(String key) {
        return getList(key, PdxScriptList.EMPTY);
    }

    public PdxScriptList getList(String key, PdxScriptList def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, LIST, s);
        if (s == null) {
            return def;
        }

        return extractList(key, s);
    }

    public <T> ImmutableList<T> getListAsList(String key, Function<? super IPdxScript, ? extends T> fct) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, LIST, s);
        return extractList(key, s).getAsList(fct);
    }

    public ImmutableIntList getListAsIntList(String key) {
        return getListAs(key, PdxScriptList::getAsIntList);
    }

    public ImmutableIntList getListAsUnsignedIntList(String key) {
        return getListAs(key, PdxScriptList::getAsUnsignedIntList);
    }

    public ImmutableLongList getListAsLongList(String key) {
        return getListAs(key, PdxScriptList::getAsLongList);
    }

    public ImmutableDoubleList getListAsDoubleList(String key) {
        return getListAs(key, PdxScriptList::getAsDoubleList);
    }

    public ImmutableList<String> getListAsStringList(String key) {
        return getListAs(key, PdxScriptList::getAsStringList);
    }

    public ImmutableList<LocalDate> getListAsDateList(String key) {
        return getListAs(key, PdxScriptList::getAsDateList);
    }

    public ImmutableList<PdxColor> getListAsColorList(String key) {
        return getListAs(key, PdxScriptList::getAsColorList);
    }

    public <T> T getListAs(String key, Function<? super PdxScriptList, ? extends T> fct) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, LIST, s);
        return fct.apply(extractList(key, s));
    }

    public ImmutableIntList getListAsEmptyOrIntList(String key) {
        return getListAs(key, PdxScriptList::getAsIntList, IntLists.immutable.empty());
    }

    public ImmutableIntList getListAsEmptyOrUnsignedIntList(String key) {
        return getListAs(key, PdxScriptList::getAsUnsignedIntList, IntLists.immutable.empty());
    }

    public ImmutableLongList getListAsEmptyOrLongList(String key) {
        return getListAs(key, PdxScriptList::getAsLongList, LongLists.immutable.empty());
    }

    public ImmutableDoubleList getListAsEmptyOrDoubleList(String key) {
        return getListAs(key, PdxScriptList::getAsDoubleList, DoubleLists.immutable.empty());
    }

    public ImmutableList<String> getListAsEmptyOrStringList(String key) {
        return getListAs(key, PdxScriptList::getAsStringList, Lists.immutable.empty());
    }

    public ImmutableList<LocalDate> getListAsEmptyOrDateList(String key) {
        return getListAs(key, PdxScriptList::getAsDateList, Lists.immutable.empty());
    }

    public ImmutableList<PdxColor> getListAsEmptyOrColorList(String key) {
        return getListAs(key, PdxScriptList::getAsColorList, Lists.immutable.empty());
    }

    public <T> T getListAs(String key, Function<? super PdxScriptList, ? extends T> fct, T def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, LIST, s);
        if (s == null) {
            return def;
        }

        return fct.apply(extractList(key, s));
    }

    public <T> ImmutableList<T> getListAsEmptyOrList(String key, Function<? super IPdxScript, ? extends T> fct) {
        return getListAsList(key, fct, Lists.immutable.empty());
    }

    public <T> ImmutableList<T> getListAsList(String key, Function<? super IPdxScript, ? extends T> fct, ImmutableList<T> def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, LIST, s);
        if (s == null) {
            return def;
        }

        return extractList(key, s).getAsList(fct);
    }

    public PdxScriptObject getObject(String key) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, OBJECT, s);
        return extractObject(key, s);
    }

    public ImmutableIntIntMap getObjectAsIntIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsIntIntMap);
    }

    public ImmutableIntIntMap getObjectAsIntUnsignedIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsIntUnsignedIntMap);
    }

    public ImmutableIntLongMap getObjectAsIntLongMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsIntLongMap);
    }

    public ImmutableIntDoubleMap getObjectAsIntDoubleMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsIntDoubleMap);
    }

    public ImmutableIntObjectMap<String> getObjectAsIntStringMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsIntStringMap);
    }

    public <V> ImmutableIntObjectMap<V> getObjectAsIntObjectMap(String key, Function<? super IPdxScript, ? extends V> fct) {
        return getObjectAs(key, o -> o.getAsIntObjectMap(fct));
    }

    public ImmutableLongIntMap getObjectAsLongIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsLongIntMap);
    }

    public ImmutableLongIntMap getObjectAsLongUnsignedIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsLongUnsignedIntMap);
    }

    public ImmutableLongLongMap getObjectAsLongLongMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsLongLongMap);
    }

    public ImmutableLongDoubleMap getObjectAsLongDoubleMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsLongDoubleMap);
    }

    public ImmutableLongObjectMap<String> getObjectAsLongStringMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsLongStringMap);
    }

    public <V> ImmutableLongObjectMap<V> getObjectAsLongObjectMap(String key, Function<? super IPdxScript, ? extends V> fct) {
        return getObjectAs(key, o -> o.getAsLongObjectMap(fct));
    }

    public ImmutableObjectIntMap<String> getObjectAsStringIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsStringIntMap);
    }

    public ImmutableObjectIntMap<String> getObjectAsStringUnsignedIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsStringUnsignedIntMap);
    }

    public ImmutableObjectLongMap<String> getObjectAsStringLongMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsStringLongMap);
    }

    public ImmutableObjectDoubleMap<String> getObjectAsStringDoubleMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsStringDoubleMap);
    }

    public ImmutableMap<String, String> getObjectAsStringStringMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsStringStringMap);
    }

    public <V> ImmutableMap<String, V> getObjectAsStringObjectMap(String key, Function<? super IPdxScript, ? extends V> fct) {
        return getObjectAs(key, o -> o.getAsStringObjectMap(fct));
    }

    public <T> T getObjectAs(String key, Function<? super PdxScriptObject, ? extends T> fct) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, OBJECT, s);
        return fct.apply(extractObject(key, s));
    }

    public <T> T getScriptAs(String key, Function<? super IPdxScript, ? extends T> fct) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, getTypeString(s), s);
        return fct.apply(s);
    }

    public ImmutableIntIntMap getObjectAsEmptyOrIntIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsIntIntMap, IntIntMaps.immutable.empty());
    }

    public ImmutableIntIntMap getObjectAsEmptyOrIntUnsignedIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsIntUnsignedIntMap, IntIntMaps.immutable.empty());
    }

    public ImmutableIntLongMap getObjectAsEmptyOrIntLongMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsIntLongMap, IntLongMaps.immutable.empty());
    }

    public ImmutableIntDoubleMap getObjectAsEmptyOrIntDoubleMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsIntDoubleMap, IntDoubleMaps.immutable.empty());
    }

    public ImmutableIntObjectMap<String> getObjectAsEmptyOrIntStringMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsIntStringMap, IntObjectMaps.immutable.empty());
    }

    public <V> ImmutableIntObjectMap<V> getObjectAsEmptyOrIntObjectMap(String key, Function<? super IPdxScript, ? extends V> fct) {
        return getObjectAs(key, o -> o.getAsIntObjectMap(fct), IntObjectMaps.immutable.empty());
    }

    public ImmutableLongIntMap getObjectAsEmptyOrLongIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsLongIntMap, LongIntMaps.immutable.empty());
    }

    public ImmutableLongIntMap getObjectAsEmptyOrLongUnsignedIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsLongUnsignedIntMap, LongIntMaps.immutable.empty());
    }

    public ImmutableLongLongMap getObjectAsEmptyOrLongLongMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsLongLongMap, LongLongMaps.immutable.empty());
    }

    public ImmutableLongDoubleMap getObjectAsEmptyOrLongDoubleMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsLongDoubleMap, LongDoubleMaps.immutable.empty());
    }

    public ImmutableLongObjectMap<String> getObjectAsEmptyOrLongStringMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsLongStringMap, LongObjectMaps.immutable.empty());
    }

    public <V> ImmutableLongObjectMap<V> getObjectAsEmptyOrLongObjectMap(String key, Function<? super IPdxScript, ? extends V> fct) {
        return getObjectAs(key, o -> o.getAsLongObjectMap(fct), LongObjectMaps.immutable.empty());
    }

    public ImmutableObjectIntMap<String> getObjectAsEmptyOrStringIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsStringIntMap, ObjectIntMaps.immutable.empty());
    }

    public ImmutableObjectIntMap<String> getObjectAsEmptyOrStringUnsignedIntMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsStringUnsignedIntMap, ObjectIntMaps.immutable.empty());
    }

    public ImmutableObjectLongMap<String> getObjectAsEmptyOrStringLongMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsStringLongMap, ObjectLongMaps.immutable.empty());
    }

    public ImmutableObjectDoubleMap<String> getObjectAsEmptyOrStringDoubleMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsStringDoubleMap, ObjectDoubleMaps.immutable.empty());
    }

    public ImmutableMap<String, String> getObjectAsEmptyOrStringStringMap(String key) {
        return getObjectAs(key, PdxScriptObject::getAsStringStringMap, Maps.immutable.empty());
    }

    public <V> ImmutableMap<String, V> getObjectAsEmptyOrStringObjectMap(String key, Function<? super IPdxScript, ? extends V> fct) {
        return getObjectAs(key, o -> o.getAsStringObjectMap(fct), Maps.immutable.empty());
    }

    public <T> T getObjectAsNullOr(String key, Function<? super PdxScriptObject, ? extends T> fct) {
        return getObjectAs(key, fct, null);
    }

    public <T> T getObjectAs(String key, Function<? super PdxScriptObject, ? extends T> fct, T def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, OBJECT, s);
        if (s == null) {
            return def;
        }

        return fct.apply(extractObject(key, s));
    }

    public <T> T getScriptAs(String key, Function<? super IPdxScript, ? extends T> fct, T def) {
        IPdxScript s = getRaw(key);
        usageStatistic.use(key, getTypeString(s), s);
        if (s == null) {
            return def;
        }

        return fct.apply(s);
    }

    public <T> T getAs(Function<? super PdxScriptObject, ? extends T> fct) {
        return fct.apply(this);
    }

    public ImmutableIntIntMap getAsIntIntMap() {
        MutableIntIntMap map = IntIntMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(Integer.parseInt(oldK), oldV.expectValue().expectInt());
        });
        return map.toImmutable();
    }

    public ImmutableIntIntMap getAsIntUnsignedIntMap() {
        MutableIntIntMap map = IntIntMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(Integer.parseInt(oldK), oldV.expectValue().expectUnsignedInt());
        });
        return map.toImmutable();
    }

    public ImmutableIntLongMap getAsIntLongMap() {
        MutableIntLongMap map = IntLongMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(Integer.parseInt(oldK), oldV.expectValue().expectLong());
        });
        return map.toImmutable();
    }

    public ImmutableIntDoubleMap getAsIntDoubleMap() {
        MutableIntDoubleMap map = IntDoubleMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(Integer.parseInt(oldK), oldV.expectValue().expectDouble());
        });
        return map.toImmutable();
    }

    public ImmutableIntObjectMap<String> getAsIntStringMap() {
        MutableIntObjectMap<String> map = IntObjectMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(Integer.parseInt(oldK), oldV.expectValue().expectString());
        });
        return map.toImmutable();
    }

    public <V> ImmutableIntObjectMap<V> getAsIntObjectMap(Function<? super IPdxScript, ? extends V> valueFct) {
        MutableIntObjectMap<V> map = IntObjectMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            V v = valueFct.apply(oldV);
            if (v != null) {
                usageStatistic.use(oldK, getTypeString(oldV), oldV);
                map.put(Integer.parseInt(oldK), v);
            }
        });
        return map.toImmutable();
    }

    public ImmutableLongIntMap getAsLongIntMap() {
        MutableLongIntMap map = LongIntMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(Long.parseLong(oldK), oldV.expectValue().expectInt());
        });
        return map.toImmutable();
    }

    public ImmutableLongIntMap getAsLongUnsignedIntMap() {
        MutableLongIntMap map = LongIntMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(Long.parseLong(oldK), oldV.expectValue().expectUnsignedInt());
        });
        return map.toImmutable();
    }

    public ImmutableLongLongMap getAsLongLongMap() {
        MutableLongLongMap map = LongLongMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(Long.parseLong(oldK), oldV.expectValue().expectLong());
        });
        return map.toImmutable();
    }

    public ImmutableLongDoubleMap getAsLongDoubleMap() {
        MutableLongDoubleMap map = LongDoubleMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(Long.parseLong(oldK), oldV.expectValue().expectDouble());
        });
        return map.toImmutable();
    }

    public ImmutableLongObjectMap<String> getAsLongStringMap() {
        MutableLongObjectMap<String> map = LongObjectMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(Long.parseLong(oldK), oldV.expectValue().expectString());
        });
        return map.toImmutable();
    }

    public <V> ImmutableLongObjectMap<V> getAsLongObjectMap(Function<? super IPdxScript, ? extends V> valueFct) {
        MutableLongObjectMap<V> map = LongObjectMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            V v = valueFct.apply(oldV);
            if (v != null) {
                usageStatistic.use(oldK, getTypeString(oldV), oldV);
                map.put(Long.parseLong(oldK), v);
            }
        });
        return map.toImmutable();
    }

    public ImmutableObjectIntMap<String> getAsStringIntMap() {
        MutableObjectIntMap<String> map = ObjectIntMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(oldK, oldV.expectValue().expectInt());
        });
        return map.toImmutable();
    }

    public ImmutableObjectIntMap<String> getAsStringUnsignedIntMap() {
        MutableObjectIntMap<String> map = ObjectIntMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(oldK, oldV.expectValue().expectUnsignedInt());
        });
        return map.toImmutable();
    }

    public ImmutableObjectLongMap<String> getAsStringLongMap() {
        MutableObjectLongMap<String> map = ObjectLongMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(oldK, oldV.expectValue().expectLong());
        });
        return map.toImmutable();
    }

    public ImmutableObjectDoubleMap<String> getAsStringDoubleMap() {
        MutableObjectDoubleMap<String> map = ObjectDoubleMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(oldK, oldV.expectValue().expectDouble());
        });
        return map.toImmutable();
    }

    public ImmutableMap<String, String> getAsStringStringMap() {
        MutableMap<String, String> map = Maps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            usageStatistic.use(oldK, getTypeString(oldV), oldV);
            map.put(oldK, oldV.expectValue().expectString());
        });
        return map.toImmutable();
    }


    public <V> ImmutableMap<String, V> getAsStringObjectMap(Function<? super IPdxScript, ? extends V> valueFct) {
        return getAsStringObjectMap(null, valueFct);
    }

    public <V> ImmutableMap<String, V> getAsStringObjectMap(Predicate<String> keyFilter, Function<? super IPdxScript, ? extends V> valueFct) {
        MutableMap<String, V> map = Maps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            if (keyFilter != null && !keyFilter.test(oldK)) {
                return;
            }

            V v = valueFct.apply(oldV);
            if (v != null) {
                usageStatistic.use(oldK, getTypeString(oldV), oldV);
                map.put(oldK, v);
            }
        });
        return map.toImmutable();
    }

    private PdxScriptObject extractObject(String key, IPdxScript s) {
        if (s instanceof PdxScriptObject) {
            return (PdxScriptObject) s;
        }

        throw new NoSuchElementException("expected object for key " + key + " but got " + s);
    }

    private PdxScriptList extractImplicitList(String key, IPdxScript s) {
        if (s == null) {
            return PdxScriptList.EMPTY_IMPLICIT;
        } else if (s instanceof PdxScriptList) {
            PdxScriptList l = (PdxScriptList) s;
            if (l.getMode() == PdxScriptList.Mode.IMPLICIT) {
                return l;
            }
        }

        return PdxScriptList.builder().add(s).build(PdxScriptList.Mode.IMPLICIT);
    }

    private PdxScriptList extractList(String key, IPdxScript s) {
        if (s instanceof PdxScriptList) {
            PdxScriptList l = (PdxScriptList) s;
            if (l.getMode() != PdxScriptList.Mode.IMPLICIT) {
                return l;
            }
        }

        throw new NoSuchElementException("expected explicit list for key " + key + " but got " + s);
    }

    private boolean extractBoolean(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Boolean) {
                return (boolean) v;
            }
        }

        throw new NoSuchElementException("expected boolean for key " + key + " but got " + s);
    }

    private int extractInt(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Integer) {
                return (int) v;
            } else if (v instanceof String) {
                try {
                    return Integer.parseInt((String) v);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        throw new NoSuchElementException("expected int for key " + key + " but got " + s);
    }

    private int extractUnsignedInt(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Integer) {
                return (int) v;
            } else if (v instanceof Long) {
                long l = (long) v;
                if (l >= 0 && l <= UNSIGNED_INT_MAX_LONG) {
                    return (int) l;
                }
            } else if (v instanceof String) {
                try {
                    return Integer.parseUnsignedInt((String) v);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        throw new NoSuchElementException("expected unsigned int for key " + key + " but got " + s);
    }

    private long extractLong(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Long || v instanceof Integer) {
                return ((Number) v).longValue();
            } else if (v instanceof String) {
                try {
                    return Long.parseLong((String) v);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        throw new NoSuchElementException("expected int for key " + key + " but got " + s);
    }

    private double extractDouble(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Double || v instanceof Long || v instanceof Integer) {
                return ((Number) v).doubleValue();
            } else if (v instanceof String) {
                try {
                    return Double.parseDouble((String) v);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        throw new NoSuchElementException("expected double for key " + key + " but got " + s);
    }

    private String extractString(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof String) {
                return (String) v;
            }
        }

        throw new NoSuchElementException("expected string for key " + key + " but got " + s);
    }

    private String extractNullOrString(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v == null) {
                return null;
            } else if (v instanceof String) {
                return (String) v;
            }
        }

        throw new NoSuchElementException("expected string or null for key " + key + " but got " + s);
    }

    private LocalDate extractDate(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof LocalDate) {
                return (LocalDate) v;
            }
        }

        throw new NoSuchElementException("expected date for key " + key + " but got " + s);
    }

    private PdxColor extractColor(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof PdxColor) {
                return (PdxColor) v;
            }
        }

        throw new NoSuchElementException("expected color for key " + key + " but got " + s);
    }

    public PdxUsageStatistic getUsageStatistic() {
        MutableMap<String, PdxUsage> usages = Maps.mutable.empty();
        usageStatistic.getUsages().forEachKeyValue((key, usage) -> usages.merge(key, usage, PdxUsage::merge));
        map.forEachKeyValue((key, value) -> {
            String prefix = (key.chars().allMatch(Character::isDigit) ? NUMBER_MARKER : key) + DOT_CHAR;
            if (value instanceof PdxScriptObject) {
                ((PdxScriptObject) value).getUsageStatistic().getUsages().forEachKeyValue((k, usage) -> usages.merge(prefix + k, usage, PdxUsage::merge));
            } else if (value instanceof PdxScriptList) {
                ((PdxScriptList) value).getUsageStatistic().getUsages().forEachKeyValue((k, usage) -> usages.merge(prefix + k, usage, PdxUsage::merge));
            }
        });
        return new PdxUsageStatistic(usages);
    }

    @Override
    public String toPdxScript(int indent, boolean root, String key) {
        if ((root && indent != 0) || (root && key != null)) {
            throw new IllegalArgumentException();
        }

        StringBuilder b = new StringBuilder();

        PdxHelper.listObjectOpen(indent, root, key, b, relation, map.isEmpty());

        // TODO: Print alphabetically sorted by k
        map.forEachKeyValue((k, v) -> {
            b.append(v.toPdxScript(root ? indent : indent + 1, false, k));
            b.append(LINE_FEED);
        });

        PdxHelper.listObjectClose(indent, root, b, map.isEmpty());

        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof PdxScriptObject)) {
            return false;
        }

        PdxScriptObject that = (PdxScriptObject) o;
        return relation == that.relation && Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relation, map);
    }

    @Override
    public String toString() {
        return "PdxScriptObject{" +
                "relation=" + relation +
                ", map=" + map +
                '}';
    }

    public static class Builder {

        private final MutableMap<String, IPdxScript> map;

        public Builder() {
            this.map = Maps.mutable.empty();
        }

        public Builder add(String key, IPdxScript value) {
            Objects.requireNonNull(value);
            String interned = key; // key.intern();
            IPdxScript existing = map.get(interned);
            if (existing == null) {
                map.put(interned, value);
            } else if (existing.canAppend(value)) {
                map.put(interned, existing.append(value));
            } else {
                throw new UnsupportedOperationException("key=" + interned + ", existing=" + existing + ", appendix=" + value);
            }

            return this;
        }

        public PdxScriptObject build() {
            return build(PdxRelation.EQUALS);
        }

        public PdxScriptObject build(PdxRelation relation) {
            return new PdxScriptObject(relation, map.toImmutable());
        }
    }
}
