package com.lcv.elverapi.apis.hypixelplayer.skyblock.api;

import com.lcv.elverapi.apis.SubApi;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.SkyblockAPI;

public class SBSlayerAPI extends SubApi
{
    public SBSlayerAPI(SkyblockAPI parent)
    {
        super(parent, "members." + parent.getUuid() + ".slayer.slayer_bosses");
    }

    public int getSlayerXp()
    {
        return (int) internalApiMap.computeIfAbsent("slayer_xp", k -> {
            int xp = 0;
            String[] arr = {"zombie", "wolf", "spider", "enderman", "blaze", "vampire"};
            for (String s : arr)
            {
                xp += get(0, s + ".xp");
            }
            return xp;
        });
    }
}
