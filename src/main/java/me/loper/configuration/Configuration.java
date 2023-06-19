package me.loper.configuration;

public interface Configuration {

    void reload();

    void load();

    /**
     * Gets the value of a given context key.
     *
     * @param key the key
     * @param <T> the key return type
     * @return the value mapped to the given key. May be null.
     */
    <T> T get(ConfigKey<T> key);
}
