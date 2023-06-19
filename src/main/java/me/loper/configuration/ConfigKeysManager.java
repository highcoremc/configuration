package me.loper.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ClassGetClass")
public final class ConfigKeysManager {

    private final List<ConfigKeyTypes.BaseConfigKey<?>> keys;

    public ConfigKeysManager(Class<?> configKeys) {
        Field[] fields = configKeys.getFields();
        this.keys = Arrays.stream(fields)
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
