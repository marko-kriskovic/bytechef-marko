
/*
 * Copyright 2021 <your company/name>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.commons.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ivica Cardic
 */
public final class MapUtils {

    private static final DefaultConversionService conversionService = new DefaultConversionService();

    static {
        @SuppressWarnings("rawtypes")
        ServiceLoader<Converter> serviceLoader = ServiceLoader.load(Converter.class);

        for (Converter<?, ?> converter : serviceLoader) {
            conversionService.addConverter(converter);
        }
    }

    private MapUtils() {
    }

    public static <K> Map<K, ?> append(Map<K, ?> map, K key, Map<K, ?> values) {
        Objects.requireNonNull(key, "'key' must not be null");
        Objects.requireNonNull(values, "'values' must not be null");

        Map<K, Object> submap = new HashMap<>(getMap(map, key, Map.of()));

        submap.putAll(values);

        Map<K, Object> newMap = new HashMap<>(map);

        newMap.put(key, submap);

        return Collections.unmodifiableMap(newMap);
    }

    public static <K, V> Map<K, V> concat(Map<K, V> map1, Map<K, V> map2) {
        Objects.requireNonNull(map1, "'map1' must not be null");
        Objects.requireNonNull(map2, "'map2' must not be null");

        return Stream.concat(stream(map1), stream(map2))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2));
    }

    public static <K> boolean containsKey(Map<K, ?> map, String key) {
        Objects.requireNonNull(map, "'map' must not be null");

        return map.containsKey(key);
    }

    public static <K> Object get(Map<K, ?> map, K key) {
        Objects.requireNonNull(map, "'map' must not be null");

        return map.get(key);
    }

    public static <K, T> T get(Map<K, ?> map, K key, Class<T> returnType) {
        Object value = get(map, key);

        if (value == null) {
            return null;
        }

        return convert(value, returnType);
    }

    public static <K, T> T get(Map<K, ?> map, K key, Class<T> returnType, T defaultValue) {
        T value = get(map, key, returnType);

        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    public static <K> Object[] getArray(Map<K, ?> map, K key) {
        Object value = get(map, key);

        if (value == null) {
            return null;
        }

        Class<?> valueClass = value.getClass();

        if (valueClass.isArray()) {
            return (Object[]) value;
        }

        List<?> list = getList(map, key);

        if (list == null) {
            return null;
        }

        return list.toArray((Object[]) Array.newInstance(Object.class, 0));
    }

    public static <K> Object[] getArray(Map<K, ?> map, K key, Object[] defaultValue) {
        Object value = get(map, key);

        if (value == null) {
            return defaultValue;
        }

        Class<?> valueClass = value.getClass();

        if (valueClass.isArray()) {
            return (Object[]) value;
        }

        List<?> list = getList(map, key);

        if (list == null) {
            return defaultValue;
        }

        return list.toArray((Object[]) Array.newInstance(Object.class, 0));
    }

    @SuppressWarnings("unchecked")
    public static <K, T> T[] getArray(Map<K, ?> map, K key, Class<T> elementType) {
        Object value = get(map, key);

        if (value == null) {
            return null;
        }

        Class<?> valueClass = value.getClass();

        Class<?> valueComponentType = valueClass.getComponentType();

        if (valueClass.isArray() && valueComponentType.equals(elementType)) {
            return (T[]) value;
        }

        List<T> list = getList(map, key, elementType);

        if (list == null) {
            return null;
        }

        return list.toArray((T[]) Array.newInstance(elementType, 0));
    }

    @SuppressWarnings("unchecked")
    public static <K, T> T[] getArray(Map<K, ?> map, K key, Class<T> elementType, T[] defaultValue) {
        Object value = get(map, key);

        if (value == null) {
            return defaultValue;
        }

        Class<?> valueClass = value.getClass();

        Class<?> valueComponentType = valueClass.getComponentType();

        if (valueClass.isArray() && valueComponentType.equals(elementType)) {
            return (T[]) value;
        }

        List<T> list = getList(map, key, elementType);

        if (list == null) {
            return defaultValue;
        }

        return list.toArray((T[]) Array.newInstance(elementType, 0));
    }

    public static <K> Boolean getBoolean(Map<K, ?> map, K key) {
        return get(map, key, Boolean.class);
    }

    public static <K> boolean getBoolean(Map<K, ?> map, K key, boolean defaultValue) {
        Boolean value = getBoolean(map, key);

        return value != null ? value : defaultValue;
    }

    public static <K> Date getDate(Map<K, ?> map, K key) {
        return get(map, key, Date.class);
    }

    public static <K> Date getDate(Map<K, ?> map, K key, Date defaultValue) {
        Date date = getDate(map, key);

        if (date == null) {
            date = defaultValue;
        }

        return date;
    }

    public static <K> Double getDouble(Map<K, ?> map, K key) {
        return get(map, key, Double.class);
    }

    public static <K> Double getDouble(Map<K, ?> map, K key, double defaultValue) {
        return get(map, key, Double.class, defaultValue);
    }

    public static <K> Duration getDuration(Map<K, ?> map, K key) {
        return get(map, key, Duration.class);
    }

    public static <K> Duration getDuration(Map<K, ?> map, K key, Duration defaultDuration) {
        Duration value = getDuration(map, key);

        return value != null ? value : defaultDuration;
    }

    public static <K> Float getFloat(Map<K, ?> map, K key) {
        return get(map, key, Float.class);
    }

    public static <K> float getFloat(Map<K, ?> map, K key, float defaultValue) {
        return get(map, key, Float.class, defaultValue);
    }

    public static <K> Integer getInteger(Map<K, ?> map, K key) {
        return get(map, key, Integer.class);
    }

    public static <K> int getInteger(Map<K, ?> map, K key, int defaultValue) {
        return get(map, key, Integer.class, defaultValue);
    }

    public static <K> List<?> getList(Map<K, ?> map, K key) {
        return get(map, key, List.class);
    }

    public static <K> List<?> getList(Map<K, ?> map, K key, List<?> defaultValue) {
        List<?> list = get(map, key, List.class);

        return list == null ? defaultValue : list;
    }

    public static <K, T> List<T> getList(Map<K, ?> map, K key, Class<T> elementType) {
        List<?> list = get(map, key, List.class);

        if (list == null) {
            return null;
        }

        List<T> typedList = new ArrayList<>();

        for (Object item : list) {
            typedList.add(convert(item, elementType));
        }

        return Collections.unmodifiableList(typedList);
    }

    public static <K, T> List<T> getList(Map<K, ?> map, K key, Class<T> elementType, List<T> defaultValue) {
        List<T> list = getList(map, key, elementType);

        return list != null ? list : defaultValue;
    }

    public static <K> List<?> getList(Map<K, ?> map, K key, Class<?>[] elementTypes) {
        List<?> list = get(map, key, List.class);

        if (list != null) {
            list = list.stream()
                .map(value -> convert(value, Arrays.asList(elementTypes)))
                .toList();
        }

        return list;
    }

    public static <K> List<?> getList(
        Map<K, ?> map, K key, List<Class<?>> elementTypes, List<?> defaultValue) {

        List<?> list = get(map, key, List.class);

        if (list == null) {
            list = defaultValue;
        } else {
            list = list.stream()
                .map(value -> convert(value, elementTypes))
                .toList();
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public static <K, T> List<T> getList(Map<K, ?> map, K key, ParameterizedTypeReference<T> elementType) {
        ResolvableType resolvableType = ResolvableType.forType(elementType);

        return getList(map, key, (Class<T>) resolvableType.getRawClass());
    }

    @SuppressWarnings("unchecked")
    public static <K, T> List<T> getList(
        Map<K, ?> map, K key, ParameterizedTypeReference<T> elementType, List<T> defaultValue) {

        ResolvableType resolvableType = ResolvableType.forType(elementType);

        List<T> list = getList(map, key, (Class<T>) resolvableType.getRawClass(), defaultValue);

        return list != null ? list : defaultValue;
    }

    public static <K> LocalDate getLocalDate(Map<K, ?> map, K key) {
        return get(map, key, LocalDate.class);
    }

    public static <K> LocalDate getLocalDate(Map<K, ?> map, K key, LocalDate defaultValue) {
        LocalDate localDate = getLocalDate(map, key);

        if (localDate == null) {
            localDate = defaultValue;
        }

        return localDate;
    }

    public static <K> LocalDateTime getLocalDateTime(Map<K, ?> map, K key) {
        return get(map, key, LocalDateTime.class);
    }

    public static <K> LocalDateTime getLocalDateTime(Map<K, ?> map, K key, LocalDateTime defaultValue) {
        LocalDateTime localDateTime = getLocalDateTime(map, key);

        if (localDateTime == null) {
            localDateTime = defaultValue;
        }

        return localDateTime;
    }

    public static <K> LocalTime getLocalTime(Map<K, ?> map, K key) {
        return get(map, key, LocalTime.class);
    }

    public static <K> LocalTime getLocalTime(Map<K, ?> map, K key, LocalTime defaultValue) {
        LocalTime localTime = getLocalTime(map, key);

        if (localTime == null) {
            localTime = defaultValue;
        }

        return localTime;
    }

    public static <K> Long getLong(Map<K, ?> map, K key) {
        return get(map, key, Long.class);
    }

    public static <K> long getLong(Map<K, ?> map, K key, long defaultValue) {
        return get(map, key, Long.class, defaultValue);
    }

    public static <K> Map<K, ?> getMap(Map<K, ?> map, K key) {
        Map<?, ?> value = get(map, key, Map.class);

        if (value == null) {
            return null;
        }

        return Collections.unmodifiableMap(toMap(value, entry -> (K) entry.getKey(), Map.Entry::getValue));
    }

    public static <K> Map<K, ?> getMap(Map<K, ?> map, K key, Map<K, ?> defaultValue) {
        Map<?, ?> value = get(map, key, Map.class);

        if (value == null) {
            return Collections.unmodifiableMap(defaultValue);
        }

        return Collections.unmodifiableMap(toMap(value, entry -> (K) entry.getKey(), Map.Entry::getValue));
    }

    public static <K, V> Map<K, V> getMap(Map<K, ?> map, K key, Class<V> valueType) {
        Map<?, ?> value = get(map, key, Map.class);

        if (value == null) {
            return null;
        }

        return Collections.unmodifiableMap(
            toMap(value, entry -> (K) entry.getKey(), entry -> convert(entry.getValue(), valueType)));
    }

    public static <K, V> Map<K, V> getMap(
        Map<K, ?> map, K key, Class<V> valueType, Map<K, V> defaultValue) {

        Map<K, V> value = getMap(map, key, valueType);

        return value == null ? Collections.unmodifiableMap(defaultValue) : value;
    }

    public static <K> Map<K, ?> getMap(Map<K, ?> map, K key, List<Class<?>> valueTypes) {
        Map<K, ?> mapValue = getMap(map, key);

        if (mapValue != null) {
            mapValue = MapUtils.toMap(mapValue, Map.Entry::getKey, entry -> convert(entry.getValue(), valueTypes));
        }

        return mapValue;
    }

    public static <K> Map<K, ?> getMap(
        Map<K, ?> map, K key, List<Class<?>> valueTypes, Map<K, ?> defaultValue) {

        Map<K, ?> mapValue = getMap(map, key);

        if (mapValue == null) {
            mapValue = Collections.unmodifiableMap(defaultValue);
        } else {
            mapValue = MapUtils.toMap(mapValue, Map.Entry::getKey, entry -> convert(entry.getValue(), valueTypes));
        }

        return mapValue;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> getMap(
        Map<K, ?> map, K key, ParameterizedTypeReference<V> elementType, Map<K, V> defaultValue) {

        ResolvableType resolvableType = ResolvableType.forType(elementType);

        return getMap(map, key, (Class<V>) resolvableType.getRawClass(), defaultValue);
    }

    public static <K> Object getRequired(Map<K, ?> map, K key) {
        Object value = get(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K, T> T getRequired(Map<K, ?> map, K key, Class<T> returnType) {
        T value = get(map, key, returnType);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> Object[] getRequiredArray(Map<K, ?> map, K key) {
        Object[] value = getArray(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K, T> T[] getRequiredArray(Map<K, ?> map, K key, Class<T> elementType) {
        T[] value = getArray(map, key, elementType);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> Boolean getRequiredBoolean(Map<K, ?> map, K key) {
        Boolean value = getBoolean(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> Date getRequiredDate(Map<K, ?> map, K key) {
        Date value = getDate(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> Double getRequiredDouble(Map<K, ?> map, K key) {
        Double value = getDouble(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> Float getRequiredFloat(Map<K, ?> map, K key) {
        Float value = getFloat(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> Integer getRequiredInteger(Map<K, ?> map, K key) {
        Integer value = getInteger(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> List<?> getRequiredList(Map<K, ?> map, K key) {
        List<?> value = getList(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K, T> List<T> getRequiredList(Map<K, ?> map, K key, Class<T> elementType) {
        List<T> value = getList(map, key, elementType);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K, T> List<T> getRequiredList(
        Map<K, ?> map, K key, ParameterizedTypeReference<T> elementType) {
        List<T> value = getList(map, key, elementType);

        Assert.notNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> LocalDate getRequiredLocalDate(Map<K, ?> map, K key) {
        LocalDate value = getLocalDate(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> LocalTime getRequiredLocalTime(Map<K, ?> map, K key) {
        LocalTime value = getLocalTime(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> LocalDateTime getRequiredLocalDateTime(Map<K, ?> map, K key) {
        LocalDateTime value = getLocalDateTime(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K, V> Map<K, V> getRequiredMap(Map<K, ?> map, K key, Class<V> valueType) {
        Map<K, V> value = getMap(map, key, valueType);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> getRequiredMap(
        Map<K, ?> map, K key, ParameterizedTypeReference<V> elementType, Map<K, V> defaultValue) {

        ResolvableType resolvableType = ResolvableType.forType(elementType);

        Map<K, V> value = getMap(map, key, (Class<V>) resolvableType.getRawClass());

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> Map<K, ?> getRequiredMap(Map<K, ?> map, K key) {
        Map<K, ?> value = getMap(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> String getRequiredString(Map<K, ?> map, K key) {
        String value = getString(map, key);

        Objects.requireNonNull(value, "Unknown value for : " + key);

        return value;
    }

    public static <K> String getString(Map<K, ?> map, K key) {
        return get(map, key, String.class);
    }

    public static <K> String getString(Map<K, ?> map, K key, String defaultValue) {
        String value = getString(map, key);

        return value != null ? value : defaultValue;
    }

    public static <K, V> Stream<Map.Entry<K, V>> stream(Map<K, V> map) {
        Objects.requireNonNull(map, "'map' must not be null");

        Set<Map.Entry<K, V>> entry = map.entrySet();

        return entry.stream();
    }

    public static <K, V, T> Map<K, V> toMap(
        List<T> list, Function<T, ? extends K> keyMapper, Function<T, ? extends V> valueMapper) {

        return list.stream()
            .collect(Collectors.toMap(keyMapper, valueMapper));
    }

    public static <K, V, K1, V1> Map<K1, V1> toMap(
        Map<K, V> map, Function<Map.Entry<K, V>, ? extends K1> keyMapper,
        Function<Map.Entry<K, V>, ? extends V1> valueMapper) {

        return map.entrySet()
            .stream()
            .collect(Collectors.toMap(keyMapper, valueMapper));
    }

    private static <T> T convert(Object value, Class<T> elementType) {
        return conversionService.convert(value, elementType);
    }

    private static Object convert(Object value, List<Class<?>> elementTypes) {
        for (Class<?> elementType : elementTypes) {
            if (conversionService.canConvert(value.getClass(), elementType)) {
                value = convert(value, elementType);
            }

            if (value.getClass() == elementType) {
                break;
            }
        }

        return value;
    }
}