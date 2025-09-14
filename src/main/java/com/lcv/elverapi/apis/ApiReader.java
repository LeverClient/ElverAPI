package com.lcv.elverapi.apis;

import org.json.JSONObject;

import java.util.HashMap;

public abstract class ApiReader {
    protected HashMap<String, Object> apiMap = new HashMap<>();
//test
    protected HashMap<String, Object> internalApiMap = new HashMap<>();

    public JSONObject jsonObject;

    public Object get(String... keys) {
        outerLoop:
        for (String key : keys) {
            if (apiMap.containsKey(key)) return apiMap.get(key);

            String[] split = key.split("\\.");

            JSONObject jsObj = jsonObject;
            for (String innerKey : split) {
                if (!jsObj.has(innerKey)) continue outerLoop;


                Object v = jsObj.opt(innerKey);

                if (v instanceof JSONObject vJs) jsObj = vJs;
                else {
                    apiMap.put(key, v);
                    return v;
                }
            }

            if (jsObj == jsonObject) {
                jsonObject = null;
            }

            apiMap.put(key, jsObj);
            return jsObj;
        }

        return null;
    }

    public <T> T andThen(T x, T y) {
        return (x == null) ? y : x;
    }
}
