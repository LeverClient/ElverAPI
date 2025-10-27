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

    public SBInventoryAPI getInventoryApi()
    {
        return (SBInventoryAPI) internalApiMap.computeIfAbsent("api/inventory", k -> new SBInventoryAPI(this));
    }

    public SBDungeonsAPI getDungeonsApi()
    {
        return (SBDungeonsAPI) internalApiMap.computeIfAbsent("api/dungeons", k -> new SBDungeonsAPI(this));
    }

    public SBPlayerStatsAPI getPlayerStatsApi()
    {
        return (SBPlayerStatsAPI) internalApiMap.computeIfAbsent("api/player_stats", k -> new SBPlayerStatsAPI(this));
    }

    public SBGearAPI getGearApi()
    {
        return (SBGearAPI) internalApiMap.computeIfAbsent("api/pets", k -> new SBGearAPI(this));
    }
    public SBAccessoryAPI getAccessoryApi()
    {
        return (SBAccessoryAPI) internalApiMap.computeIfAbsent("api/accessory", k -> new SBAccessoryAPI(this));
    }
    public SBPetAPI getPetApi()
    {
        return (SBPetAPI) internalApiMap.computeIfAbsent("api/pets", k -> new SBPetAPI(this));
    }

    @Override
    public String getApiEndpoint() {
        return "https://api.hypixel.net/v2/skyblock/profiles?key=" + apiKey + "&uuid=" + uuid;
    }
}
