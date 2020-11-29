package me.loper.configuration;


import me.loper.configuration.adapter.ConfigurationAdapter;

import java.util.List;

/**
 * An default implementation of {@link Configuration}.
 *
 * <p>Values are loaded into memory on init.</p>
 */
public class PluginConfiguration implements Configuration {

    /**
     * The configurations loaded values.
     *
     * <p>The value corresponding to each key is stored at the index defined
     * by {@link ConfigKey#ordinal()}.</p>
     */
    private Object[] values = null;

    private final Class<?> configKeys;

    private final ConfigurationAdapter adapter;

    public PluginConfiguration(ConfigurationAdapter adapter, Class<?> configKeys) {
        this.configKeys = configKeys;
        this.adapter = adapter;

        load();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(ConfigKey<T> key) {
        return (T) this.values[key.ordinal()];
    }

    @Override
    public synchronized void load() {
        boolean reload = true;


        ConfigKeysManager keysManager = new ConfigKeysManager(this.configKeys);
        List<? extends ConfigKey<?>> keys = keysManager.getKeys();

        if (this.values == null) {
            this.values = new Object[keys.size()];
            reload = false;
        }

        for (ConfigKey<?> key : keys) {
            if (reload && key instanceof ConfigKeyTypes.EnduringKey) {
                continue;
            }

            Object value = key.get(this.adapter);
            this.values[key.ordinal()] = value;
        }
    }

    @Override
    public void reload() {
        this.adapter.reload();
        load();
    }
}
