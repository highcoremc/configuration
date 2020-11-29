package me.loper.configuration;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * All of the {@link ConfigKey}s used by LuckPerms.
 *
 * <p>The {@link #getKeys()} method and associated behaviour allows this class
 * to function a bit like an enum, but with generics.</p>
 */
@SuppressWarnings("ClassGetClass")
public final class ConfigKeysManager {

    private final List<ConfigKeyTypes.BaseConfigKey<?>> keys;

    public ConfigKeysManager(Class<?> configKeys) {
        this.keys = Arrays.stream(configKeys.getClass().getFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .filter(f -> ConfigKey.class.equals(f.getType()))
                .map(f -> {
                    try {
                        return (ConfigKeyTypes.BaseConfigKey<?>) f.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(ImmutableCollectors.toList());

        for (int i = 0; i < keys.size(); i++) {
            this.keys.get(i).ordinal = i;
        }
    }

    /**
     * Gets a list of the keys defined in this class.
     *
     * @return the defined keys
     */
    public List<? extends ConfigKey<?>> getKeys() {
        return this.keys;
    }

}
