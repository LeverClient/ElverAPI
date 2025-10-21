package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import com.lcv.elverapi.apis.SubApi;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.stream.IntStream;

public class SBDungeonsAPI extends SubApi
{
    public SBDungeonsAPI(SkyblockAPI parent)
    {
        super(parent,"members." + parent.getUuid() + ".dungeons");
    }

    public int[] getRuns()
    {
        return (int[]) internalApiMap.computeIfAbsent("runs", k -> IntStream.range(0, 8)
                .map(i -> get(BigDecimal.valueOf(-1), "dungeon_types.catacombs.times_played." + i).intValue())
                .toArray());
    }
    public int getExperience()
    {
        return (int) internalApiMap.computeIfAbsent("experience", k -> get(BigDecimal.valueOf(-1), "dungeon_types.catacombs.experience").intValue());
    }
    public int[] getBestScores()
    {
        return (int[]) internalApiMap.computeIfAbsent("best_scores", k -> IntStream.range(0, 8)
                .map(i -> get(BigDecimal.valueOf(-1), "dungeon_types.catacombs.best_score." + i).intValue())
                .toArray());
    }
    public int[] getTotalMobsKilled()
    {
        return (int[]) internalApiMap.computeIfAbsent("total_mobs_killed", k -> IntStream.range(0, 8)
                .map(i -> get(BigDecimal.valueOf(-1), "dungeon_types.catacombs.mobs_killed." + i).intValue())
                .toArray());
    }
    public int[] getMostMobsKilled()
    {
        return (int[]) internalApiMap.computeIfAbsent("most_mobs_killed", k -> IntStream.range(0, 8)
                .map(i -> get(BigDecimal.valueOf(-1), "dungeon_types.catacombs.most_mobs_killed." + i).intValue())
                .toArray());
    }
    public int[] getMostDamageMage()
    {
        return (int[]) internalApiMap.computeIfAbsent("most_damage_mage", k -> IntStream.range(0, 8)
                .map(i -> get(BigDecimal.valueOf(-1), "dungeon_types.catacombs.most_damage_mage." + i).intValue())
                .toArray());
    }
}
