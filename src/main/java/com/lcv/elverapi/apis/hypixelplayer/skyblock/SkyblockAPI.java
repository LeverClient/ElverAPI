package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import com.lcv.elverapi.apis.Api;
import org.json.JSONArray;
import org.json.JSONObject;

public class SkyblockAPI extends Api {
    private final String uuid;
    private final String apiKey;

    public SkyblockAPI(String uuid, String apiKey)
    {
        this.uuid = uuid;
        this.apiKey = apiKey;
        doHttp();
        this.jsonObject = new JSONObject(response.body());
        JSONArray profiles = get(null, "profiles");
        this.noData = profiles == null || noData;
        if (!noData) {
            for (Object o : profiles) {
                JSONObject json = (JSONObject) o;
                if (json.getBoolean("selected")) {
                    this.jsonObject = json;
                    break;
                }
            }
        }
    }

    public String getUuid() {
        return uuid;
    }

    public String getUuidNoHyphen()
    {
        StringBuilder str = new StringBuilder();
        for (String s : uuid.split("-"))
        {
            str.append(s);
        }
        return str.toString();
    }

    public SBInventoryAPI getInventoryApi()
    {
        return (SBInventoryAPI) internalApiMap.computeIfAbsent("api/inventory", (k) -> new SBInventoryAPI(this));
    }

    @Override
    public String getApiEndpoint() {
        return "https://api.hypixel.net/v2/skyblock/profiles?key=" + apiKey + "&uuid=" + uuid;
    }
}
