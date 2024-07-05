package org.highcore.configuration;

import org.highcore.configuration.adapter.ConfigurationAdapter;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ConfigKeyTypes {
    public static <T> CustomKey<T> key(BiFunction<ConfigurationAdapter, PluginConfiguration, T> function) {
        return new CustomKey<>(function);
    }

    public static <T> CustomKey<T> key(Function<ConfigurationAdapter, T> function) {
        return new CustomKey<>((a, c) -> function.apply(a));
    }

    public static StringKey stringKey(String path, String defaultValue) {
        return new StringKey(path, defaultValue);
    }

    public static LongKey longKey(String path, long defaultValue) {
        return new LongKey(path, defaultValue);
    }

    public static IntegerKey intKey(String path, int defaultValue) {
        return new IntegerKey(path, defaultValue);
    }

    public static ConfigKey<Boolean> boolKey(String path, boolean defaultValue) {
        return new BooleanKey(path, defaultValue);
    }

    public static <T extends Enum<T>> ConfigKey<T> enumKey(Class<T> enumClass, String path, T defaultValue) {
        return new EnumKey<>(enumClass, path, defaultValue);
    }

    public abstract static class BaseConfigKey<T> implements ConfigKey<T> {
        int ordinal = -1;

        public BaseConfigKey() {
        }

        @Override
        public int ordinal() {
            return this.ordinal;
        }
    }

    public static class EnumKey<T extends Enum<T>> extends BaseConfigKey<T> {
        private final String path;
        private final T defaultValue;
        private final Class<T> enumClass;

        private EnumKey(Class<T> enumClass, String path, T defaultValue) {
            this.enumClass = enumClass;
            this.path = path;
            this.defaultValue = defaultValue;
        }

        @Override
        public T get(ConfigurationAdapter adapter, PluginConfiguration config) {
            return Enum.valueOf(this.enumClass, adapter.getString(path, this.defaultValue.name()));
        }
    }

    public static class StringKey extends BaseConfigKey<String> {
        private final String path;
        private final String defaultValue;

        private StringKey(String path, String defaultValue) {
            this.path = path;
            this.defaultValue = defaultValue;
        }

        @Override
        public String get(ConfigurationAdapter adapter, PluginConfiguration config) {
            return adapter.getString(this.path, this.defaultValue);
        }
    }

    public static class LongKey extends BaseConfigKey<Long> {
        private final String path;
        private final long defaultValue;

        private LongKey(String path, long defaultValue) {
            this.path = path;
            this.defaultValue = defaultValue;
        }

        @Override
        public Long get(ConfigurationAdapter adapter, PluginConfiguration config) {
            return adapter.getLong(this.path, this.defaultValue);
        }
    }

    public static class BooleanKey extends BaseConfigKey<Boolean> {
        private final String path;
        private final boolean defaultValue;

        private BooleanKey(String path, boolean defaultValue) {
            this.path = path;
            this.defaultValue = defaultValue;
        }

        @Override
        public Boolean get(ConfigurationAdapter adapter, PluginConfiguration config) {
            return adapter.getBoolean(this.path, this.defaultValue);
        }
    }

    public static class IntegerKey extends BaseConfigKey<Integer> {
        private final String path;
        private final int defaultValue;

        private IntegerKey(String path, int defaultValue) {
            this.path = path;
            this.defaultValue = defaultValue;
        }

        @Override
        public Integer get(ConfigurationAdapter adapter, PluginConfiguration config) {
            return adapter.getInteger(path, defaultValue);
        }
    }

    public static class CustomKey<T> extends BaseConfigKey<T> {
        private final BiFunction<ConfigurationAdapter, PluginConfiguration, T> function;

        private CustomKey(BiFunction<ConfigurationAdapter, PluginConfiguration, T> function) {
            this.function = function;
        }

        @Override
        public T get(ConfigurationAdapter adapter, PluginConfiguration config) {
            return this.function.apply(adapter, config);
        }
    }

}
