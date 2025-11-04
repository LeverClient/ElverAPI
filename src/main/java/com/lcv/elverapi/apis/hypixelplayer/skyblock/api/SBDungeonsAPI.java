package com.lcv.elverapi.apis.hypixelplayer.skyblock.api;

import com.lcv.elverapi.apis.SubApi;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.SkyblockAPI;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.util.SBDungeon;

import java.math.BigDecimal;
import java.util.stream.IntStream;

public class SBDungeonsAPI extends SubApi
{
    public SBDungeonsAPI(SkyblockAPI parent)
    {
        super(parent,"members." + parent.getUuid() + ".dungeons");
    }

    public int getExperience()
    {
        return (int) internalApiMap.computeIfAbsent("experience", k -> get(BigDecimal.valueOf(-1), "dungeon_types.catacombs.experience").intValue());
    }
    public int getArcherXp()
    {
        return (int) internalApiMap.computeIfAbsent("archer_xp", k -> get(BigDecimal.valueOf(-1), "player_classes.archer.experience").intValue());
    }
    public int getBerserkerXp()
    {
        return (int) internalApiMap.computeIfAbsent("berserker_xp", k -> get(BigDecimal.valueOf(-1), "player_classes.berserk.experience").intValue());
    }
    public int getHealerXp()
    {
        return (int) internalApiMap.computeIfAbsent("healer_xp", k -> get(BigDecimal.valueOf(-1), "player_classes.healer.experience").intValue());
    }
    public int getMageXp()
    {
        return (int) internalApiMap.computeIfAbsent("mage_xp", k -> get(BigDecimal.valueOf(-1), "player_classes.healer.experience").intValue());
    }
    public int getTankXp()
    {
        return (int) internalApiMap.computeIfAbsent("tank_xp", k -> get(BigDecimal.valueOf(-1), "player_classes.tank.experience").intValue());
    }

    // todo: xp -> level stuff

    public String getSelectedClass()
    {
        return (String) internalApiMap.computeIfAbsent("selected_class", k -> switch (get("", "selected_dungeon_class"))
        {
            case "archer" -> "Archer";
            case "berserk" -> "Berserker";
            case "healer" -> "Healer";
            case "mage" -> "Mage";
            case "tank" -> "Tank";
            case "" -> "DEFAULT_VALUE";
            default -> "UNKNOWN";
        });
    }
    public int getHighestCatacombsFloor()
    {
        return (int) internalApiMap.computeIfAbsent("highest_catacombs_floor", k -> get(-1, "dungeon_types.catacombs.highest_tier_completed"));
    }
    public int getHighestMasterFloor()
    {
        return (int) internalApiMap.computeIfAbsent("highest_master_floor", k -> get(-1, "dungeon_types.master_catacombs.highest_tier_completed"));
    }
    public int getSecrets()
    {
        return (int) internalApiMap.computeIfAbsent("secrets", k -> get(-1, "secrets"));
    }

    public SBDungeon[] getNormalDungeons()
    {
        return (SBDungeon[]) internalApiMap.computeIfAbsent("normal_dungeons", k ->
                IntStream
                        .range(0, 8)
                        .mapToObj(i -> {
                            String prefix = "dungeon_types.catacombs.";
                            int runs = get(-1, prefix + "times_played." + i);
                            int tier = get(-1, prefix + "tier_completions." + i);
                            int milestone = get(-1, prefix + "milestone_completions." + i);
                            int mobs = get(-1, prefix + "mobs_killed." + i);
                            int score = get(-1, prefix + "best_score." + i);
                            int watcher = get(-1, prefix + "watcher_kills." + i);
                            int mostMobs = get(-1, prefix + "most_mobs_killed." + i);
                            int time = get(-1, prefix + "fastest_time." + i);
                            int timeS = get(-1, prefix + "fastest_time_s." + i);
                            int timeSPlus = get(-1, prefix + "fastest_time_s_plus." + i);
                            int healing = get(-1, prefix + "most_healing." + i);
                            int damage = -1;
                            String damageClass = "NONE";
                            String[] classes = {""};
                        })
                        .toArray(SBDungeon[]::new));
    }
    public SBDungeon getEntrance()
    {
        SBDungeon[] floors = getNormalDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("entrance", k -> floors[0]);
    }

    public int[] getRuns()
    {
        return (int[]) internalApiMap.computeIfAbsent("runs", k -> IntStream.range(0, 8)
                .map(i -> get(BigDecimal.valueOf(-1), "dungeon_types.catacombs.times_played." + i).intValue())
                .toArray());
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
