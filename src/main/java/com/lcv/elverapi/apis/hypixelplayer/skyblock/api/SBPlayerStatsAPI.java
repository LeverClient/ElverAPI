package com.lcv.elverapi.apis.hypixelplayer.skyblock.api;

import com.lcv.elverapi.apis.SubApi;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.SkyblockAPI;

import java.math.BigDecimal;

public class SBPlayerStatsAPI extends SubApi
{
    public SBPlayerStatsAPI(SkyblockAPI parent)
    {
        super(parent, "members." + parent.getUuid());
    }

    public int getSkyblockXp()
    {
        return (int) internalApiMap.computeIfAbsent("skyblock_xp", k -> get(-1, "leveling.experience"));
    }
    public int getAlchemyXp()
    {
        return (int) internalApiMap.computeIfAbsent("alchemy_xp", k -> get(BigDecimal.valueOf(-1), "player_data.experience.SKILL_ALCHEMY").intValue());
    }
    public int getCarpentryXp()
    {
        return (int) internalApiMap.computeIfAbsent("carpentry_xp", k -> get(BigDecimal.valueOf(-1), "player_data.experience.SKILL_CARPENTRY").intValue());
    }
    public int getCombatXp()
    {
        return (int) internalApiMap.computeIfAbsent("combat_xp", k -> get(BigDecimal.valueOf(-1), "player_data.experience.SKILL_COMBAT").intValue());
    }
    public int getEnchantingXp()
    {
        return (int) internalApiMap.computeIfAbsent("enchanting_xp", k -> get(BigDecimal.valueOf(-1), "player_data.experience.SKILL_ENCHANTING").intValue());
    }
    public int getFarmingXp()
    {
        return (int) internalApiMap.computeIfAbsent("farming_xp", k -> get(BigDecimal.valueOf(-1), "player_data.experience.SKILL_FARMING").intValue());
    }
    public int getFishingXp()
    {
        return (int) internalApiMap.computeIfAbsent("fishing_xp", k -> get(BigDecimal.valueOf(-1), "player_data.experience.SKILL_FISHING").intValue());
    }
    public int getForagingXp()
    {
        return (int) internalApiMap.computeIfAbsent("foraging_xp", k -> get(BigDecimal.valueOf(-1), "player_data.experience.SKILL_FORAGING").intValue());
    }
    public int getMiningXp()
    {
        return (int) internalApiMap.computeIfAbsent("mining_xp", k -> get(BigDecimal.valueOf(-1), "player_data.experience.SKILL_MINING").intValue());
    }
    public int getRunecraftingXp()
    {
        return (int) internalApiMap.computeIfAbsent("fishing_xp", k -> get(BigDecimal.valueOf(-1), "player_data.experience.SKILL_FISHING").intValue());
    }
    public int getSocialXp()
    {
        return (int) internalApiMap.computeIfAbsent("social_xp", k -> get(BigDecimal.valueOf(-1), "player_data.experience.SKILL_SOCIAL").intValue());
    }
    public int getTamingXp()
    {
        return (int) internalApiMap.computeIfAbsent("taming_xp", k -> get(BigDecimal.valueOf(-1), "player_data.experience.SKILL_TAMING").intValue());
    }

    public int getSkyblockLevel()
    {
        int xp = getSkyblockXp();
        return (int) internalApiMap.computeIfAbsent("skyblock_level", k -> xp / 100);
    }
    // todo: xp -> level array
}
