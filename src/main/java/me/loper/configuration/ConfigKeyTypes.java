package me.loper.configuration;

import me.loper.configuration.adapter.ConfigurationAdapter;

import java.util.function.Function;

public class ConfigKeyTypes {

    @Deprecated
    public static <T> CustomKey<T> customKey(Function<ConfigurationAdapter, T> function) {
        return new CustomKey<>(function);
    }

    public static <T> CustomKey<T> key(Function<ConfigurationAdapter, T> function) {
        return new CustomKey<>(function);
    }

    public static StringKey stringKey(Function<ConfigurationAdapter, String> function) {
        return new StringKey(function);
    }

    @Deprecated
    public static <T> EnduringKey<T> enduringKey(ConfigKey<T> delegate) {
        return new EnduringKey<>(delegate);
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

    public static class StringKey extends BaseConfigKey<String> {
        private final Function<ConfigurationAdapter, String> function;

        private StringKey(Function<ConfigurationAdapter, String> function) {
            this.function = function;
        }

        @Override
        public String get(ConfigurationAdapter adapter) {
            return this.function.apply(adapter);
        }
    }

    public static class CustomKey<T> extends BaseConfigKey<T> {
        private final Function<ConfigurationAdapter, T> function;

        private CustomKey(Function<ConfigurationAdapter, T> function) {
            this.function = function;
        }

        @Override
        public T get(ConfigurationAdapter adapter) {
            return this.function.apply(adapter);
        }
    }

    @Deprecated
    public static class EnduringKey<T> extends BaseConfigKey<T> {
        private final ConfigKey<T> delegate;

        private EnduringKey(ConfigKey<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T get(ConfigurationAdapter adapter) {
            return this.delegate.get(adapter);
        }
    }

}
