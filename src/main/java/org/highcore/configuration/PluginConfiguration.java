package org.highcore.configuration;


import org.highcore.configuration.adapter.ConfigurationAdapter;

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
    private ConfigKey<?>[] keys = null;

    private final Class<?> configKeys;

    private final ConfigurationAdapter adapter;

    public PluginConfiguration(ConfigurationAdapter adapter, Class<?> configKeys) {
        this.configKeys = configKeys;
        this.adapter = adapter;

        load();
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized <T> T get(ConfigKey<T> key) {
        if (null == this.values[key.ordinal()]) {
            this.values[key.ordinal()] = this.keys[key.ordinal()].get(this.adapter, this);
        }

        return (T) this.values[key.ordinal()];
    }

    @Override
    public synchronized void load() {
        ConfigKeysManager keysManager = new ConfigKeysManager(this.configKeys);
        List<? extends ConfigKey<?>> keys = keysManager.getKeys();

        if (this.keys == null) {
            this.keys = new ConfigKey<?>[keys.size()];
        }

        if (this.values == null) {
            this.values = new Object[keys.size()];
        }

        for (ConfigKey<?> key : keys) {
            this.keys[key.ordinal()] = key;
        }
    }

    @Override
    public void reload() {
        this.adapter.reload();
        load();
    }
}
