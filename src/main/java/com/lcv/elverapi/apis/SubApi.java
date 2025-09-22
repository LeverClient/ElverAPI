package com.lcv.elverapi.apis;

public abstract class SubApi extends ApiReader {

    public final Api parent;

    public final String prefix;

    public final String indexPrefix;

    public SubApi(Api parent, String prefix) {
        this.parent = parent;
        this.prefix = prefix;
        this.indexPrefix = prefix.endsWith(".") ? prefix : prefix + ".";
    }
    public <T> T get(T defaultValue, String... keys) {
        String[] prefixedKeys = new String[keys.length];
        for (int i = 0; i < prefixedKeys.length; i++) {
            prefixedKeys[i] = indexPrefix + keys[i];
        }

        return parent.get(defaultValue, prefixedKeys);
    }

    public boolean exists() {
        return parent.get(this.prefix) != null;
    }
}
