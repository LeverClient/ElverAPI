package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import com.lcv.elverapi.apis.Api;
import org.json.JSONArray;
import org.json.JSONObject;

public class SkyblockAPI extends Api {
    private final String uuid;
    private final String apiKey;
    private final JSONArray profiles;
    private final String profile;

    public SkyblockAPI(String uuid, String apiKey)
    {
        new SkyblockAPI(uuid, apiKey, "");
    }

    public SkyblockAPI(String uuid, String apiKey, String profile)
    {
        this.uuid = uuid;
        this.apiKey = apiKey;
        doHttp();
        this.jsonObject = new JSONObject(response.body());
        this.noData = (this.profiles = get(null,"profiles")) == null || noData;
        if (!noData)
        {

        }
    }

    public String getUuid() {
        return uuid;
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
