package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import com.lcv.elverapi.apis.SubApi;

import java.math.BigDecimal;

public class SBPlayerStatsAPI extends SubApi
{
    public SBPlayerStatsAPI(SkyblockAPI parent)
    {
        super(parent, "members." + parent.getUuid() + ".player_data");
    }

    public int getFishingXp()
    {
        return (int) internalApiMap.computeIfAbsent("fishing_xp", k -> get(BigDecimal.valueOf(-1), "experience.SKILL_FISHING").intValue());
    }
    public int getAlchemyXp()
    {
        return (int) internalApiMap.computeIfAbsent("alchemy_xp", k -> get(BigDecimal.valueOf(-1), "experience.SKILL_ALCHEMY").intValue());
    }
    public int getRunecraftingXp()
    {
        return (int) internalApiMap.computeIfAbsent("fishing_xp", k -> get(BigDecimal.valueOf(-1), "experience.SKILL_FISHING").intValue());
    }
    public int getMiningXp()
    {
        return (int) internalApiMap.computeIfAbsent("mining_xp", k -> get(BigDecimal.valueOf(-1), "experience.SKILL_MINING").intValue());
    }
}
