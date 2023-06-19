package me.loper.configuration.adapter;


import java.util.List;
import java.util.Map;

public interface ConfigurationAdapter {

    void reload();

    String getString(String path, String def);

    int getInteger(String path, int def);

    float getFloat(String path, float def);

    double getDouble(String path, double def);

    boolean getBoolean(String path, boolean def);

    List<String> getStringList(String path, List<String> def);

    List<String> getKeys(String path, List<String> def);

    Map<String, String> getMapStringToString(String path, Map<String, String> def);

    Map<String, List<String>> getMapStringToListString(String path, Map<String, List<String>> def);
}
