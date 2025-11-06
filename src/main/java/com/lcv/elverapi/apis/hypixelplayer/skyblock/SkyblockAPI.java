package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import com.lcv.elverapi.apis.Api;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.api.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
    public SBInventoryAPI getInventoryApi()
    {
        return (SBInventoryAPI) internalApiMap.computeIfAbsent("api/inventory", k -> new SBInventoryAPI(this));
    }
    // todo: finish this once foraging api is added
    public Object getSkillsApi()
    {
        return null;
    }
    public SBDungeonsAPI getDungeonsApi()
    {
        return (SBDungeonsAPI) internalApiMap.computeIfAbsent("api/dungeons", k -> new SBDungeonsAPI(this));
    }
    public SBSlayerAPI getSlayersApi()
    {
        return (SBSlayerAPI) internalApiMap.computeIfAbsent("api/dungeons", k -> new SBSlayerAPI(this));
    }

    @Override
    public String getApiEndpoint() {
        return "https://api.hypixel.net/v2/skyblock/profiles?key=" + apiKey + "&uuid=" + uuid;
    }
}
