package io.github.longxiaoyun;

import io.github.longxiaoyun.utils.BeanMapUtils;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author longxiaoyun
 * @date 2024-08-21
 */
public class Meta extends ConcurrentHashMap<String, Object> {



    /** 初始化容量16 */
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    /** 负载因子 */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    /** 是否大小写不敏感 */
    private boolean caseInsensitive;

    /**
     * 创建Dict
     *
     * @return Dict
     */
    public static Meta create() {
        return new Meta();
    }

    /**
     * 将PO对象转为Meta
     * @param <T>  Bean类型
     * @param bean Bean对象
     * @return Vo
     */
    public static <T> Meta parse(T bean) {
        return create().parseBean(bean);
    }

    /**
     * 根据给定的Pair数组创建Dict对象
     *
     * @param pairs 键值对
     * @return Dict
     * @since 5.4.1
     */
    @SafeVarargs
    public static Meta of(Pair<String, Object>... pairs) {
        final Meta dict = create();
        for (Pair<String, Object> pair : pairs) {
            dict.put(pair.getKey(), pair.getValue());
        }
        return dict;
    }

    public static Meta of(Object... keysAndValues) {
        final Meta dict = create();

        String key = null;
        for (int i = 0; i < keysAndValues.length; i++) {
            if (i % 2 == 0) {
                key = keysAndValues[i].toString();
            } else {
                dict.put(key, keysAndValues[i]);
            }
        }

        return dict;
    }


    public Meta() {
        this(DEFAULT_INITIAL_CAPACITY, false);
    }
    /**
     * 构造方法
     * @param caseInsensitive 是否大小写不敏感
     */
    public Meta(boolean caseInsensitive) {
        this(DEFAULT_INITIAL_CAPACITY, caseInsensitive);
    }

    /**
     * 构造方法
     * @param initialCapacity 初始容量
     */
    public Meta(int initialCapacity) {
        this(initialCapacity, false);
    }

    /**
     * 构造方法
     * @param initialCapacity 初始容量
     * @param caseInsensitive 是否大小写不敏感
     */
    public Meta(int initialCapacity, boolean caseInsensitive) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, caseInsensitive);
    }
    /**
     * 构造方法
     * @param initialCapacity 初始容量
     * @param loadFactor      容量增长因子，0~1，即达到容量的百分之多少时扩容
     */
    public Meta(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, false);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始容量
     * @param loadFactor      容量增长因子，0~1，即达到容量的百分之多少时扩容
     * @param caseInsensitive 是否大小写不敏感
     * @since 4.5.16
     */
    public Meta(int initialCapacity, float loadFactor, boolean caseInsensitive) {
        super(initialCapacity, loadFactor);
        this.caseInsensitive = caseInsensitive;
    }

    /**
     * 构造方法
     * @param m Map
     */
    public Meta(Map<String, Object> m) {
        super((null == m) ? new ConcurrentHashMap<>() : m);
    }

    /**
     * @param key 键
     * @param defaultValue 默认值
     * @return 字段值
     * @param <T> 值类型
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defaultValue) {
        final Object result = get(key);
        return (T) (result != null ? result : defaultValue);
    }

    /**
     * 获取string
     * @param key 键
     * @return 字段值
     */
    public String getString(String key) {
        return get(key, null);
    }
    /**
     * 获取string,获取为空返回默认值
     * @param key 键
     * @param defaultValue 默认值
     * @return 字段值
     */
    public String getString(String key, String defaultValue) {
        return get(key, defaultValue);
    }
    /**
     * 获取int
     * @param key 键
     * @return 字段值
     */
    public Integer getInteger(String key) {
        return get(key, null);
    }
    /**
     * 获取int,获取为空返回默认值
     * @param key 键
     * @param defaultValue 默认值
     * @return 字段值
     */
    public int getIntValue(String key, int defaultValue) {
        return get(key, defaultValue);
    }
    /**
     * 获取long
     * @param key 键
     * @return 字段值
     */
    public Long getLong(String key) {
        return get(key, null);
    }
    /**
     * 获取long,获取为空返回默认值
     * @param key 键
     * @param defaultValue 默认值
     * @return 字段值
     */
    public long getLongValue(String key, long defaultValue) {
        return get(key, defaultValue);
    }
    /**
     * 获取float
     * @param key 键
     * @return 字段值
     */
    public Float getFloat(String key) {
        return get(key, null);
    }
    /**
     * 获取float,获取为空返回默认值
     * @param key 键
     * @param defaultValue  默认值
     * @return 字段值
     */
    public float getFloatValue(String key, float defaultValue) {
        return get(key, defaultValue);
    }
    /**
     * 获取short
     * @param key 键
     * @return 字段值
     */
    public Short getShort(String key) {
        return get(key, null);
    }
    /**
     * 获取short,获取为空返回默认值
     * @param key 键
     * @param defaultValue 默认值
     * @return 字段值
     */
    public short getShortValue(String key, short defaultValue) {
        return get(key, defaultValue);
    }
    /**
     * 获取double
     * @param key 键
     * @return 字段值
     */
    public Double getDouble(String key) {
        return get(key, null);
    }
    /**
     * 获取double,获取为空返回默认值
     * @param key 键
     * @param defaultValue 默认值
     * @return 字段值
     */
    public double getDoubleValue(String key, double defaultValue) {
        return get(key, defaultValue);
    }
    /**
     * 获取char
     * @param key 键
     * @return 字段值
     */
    public Character getChar(String key) {
        return get(key, null);
    }
    /**
     * 获取char,获取为空返回默认值
     * @param key 键
     * @param defaultValue 默认值
     * @return 字段值
     */
    public char getCharValue(String key, char defaultValue) {
        return get(key, defaultValue);
    }
    /**
     * 获取byte
     * @param key 键
     * @return 字段值
     */
    public Byte getByte(String key) {
        return get(key, null);
    }
    /**
     * 获取byte,获取为空返回默认值
     * @param key 键
     * @param defaultValue 默认值
     * @return 字段值
     */
    public byte getByteValue(String key, byte defaultValue) {
        return get(key, defaultValue);
    }
    /**
     * 获取boolean
     * @param key 键
     * @return 字段值
     */
    public Boolean getBool(String key) {
        return get(key, null);
    }
    /**
     * 获取boolean,获取为空返回默认值
     * @param key 键
     * @param defaultValue 默认值
     * @return 字段值
     */
    public boolean getBoolValue(String key, boolean defaultValue) {
        return get(key, defaultValue);
    }
    /**
     * 获取BigDecimal
     * @param key 键
     * @return 字段值
     */
    public BigDecimal getBigDecimal(String key) {
        return get(key, null);
    }
    /**
     *  获取BigInteger
     * @param key 键
     * @return 字段值
     */
    public BigInteger getBigInteger(String key) {
        return get(key, null);
    }
    /**
     * 获取byte[]
     * @param key 键
     * @return 字段值
     */
    public byte[] getBytes(String key) {
        return get(key, null);
    }
    /**
     * 获取Date
     * @param key 键
     * @return 字段值
     */
    public Date getDate(String key) {
        return get(key, null);
    }
    /**
     * 获取Time
     * @param key 键
     * @return 字段值
     */
    public Time getTime(String key) {
        return get(key, null);
    }
    /**
     * 获取Timestamp
     * @param key 键
     * @return 字段值
     */
    public Timestamp getTimestamp(String key) {
        return get(key, null);
    }
    /**
     * 获取Number
     * @param key 键
     * @return 字段值
     */
    public Number getNumber(String key) {
        return get(key, null);
    }
    /**
     * 获得特定类型值
     * @param <T>  值类型
     * @param attr 字段名
     * @return 字段值
     * @since 4.6.3
     */
    public <T> T getBean(String attr) {
        return get(attr, null);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(customKey((String) key));
    }

    @Override
    public Object get(Object key) {
        return super.get(customKey((String) key));
    }

    @Override
    public Object put(String key, Object value) {
        return super.put(customKey(key), value);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        m.forEach(this::put);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }


    /**
     *  自定义key
     * @param key   key
     * @return key
     */
    private String customKey(String key) {
        if (this.caseInsensitive && null != key) {
            key = key.toLowerCase();
        }
        return key;
    }

    public <T> Meta parseBean(T bean) {
        Objects.requireNonNull(bean, "Bean class must be not null");
        this.putAll(Objects.requireNonNull(BeanMapUtils.beanToMap(bean)));
        return this;
    }

}
