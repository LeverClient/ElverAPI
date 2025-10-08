package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import com.lcv.elverapi.apis.SubApi;

public class SBInventoryAPI extends SubApi {
    public SBInventoryAPI(SkyblockAPI parent)
    {
        super(parent, "inventory");
    }

    public String getInventory()
    {
        return (String) internalApiMap.computeIfAbsent("inventory", (k) -> get(null, "inv_content/data"));
    }
}
