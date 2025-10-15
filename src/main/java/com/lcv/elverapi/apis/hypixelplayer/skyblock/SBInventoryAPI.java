package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import com.lcv.elverapi.apis.SubApi;
import org.json.JSONObject;

public class SBInventoryAPI extends SubApi {
    public SBInventoryAPI(SkyblockAPI parent)
    {
        super(parent, "members." + parent.getUuidNoHyphen() + ".inventory");
        System.out.println(this.exists());
    }

    public String getInventory()
    {
        return (String) internalApiMap.computeIfAbsent("inventory", (k) -> get(null, "inv_content"));
    }
}
