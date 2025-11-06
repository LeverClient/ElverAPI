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
    public int getHighestNormalFloor()
    {
        return (int) internalApiMap.computeIfAbsent("highest_normal_floor", k -> get(-1, "dungeon_types.catacombs.highest_tier_completed"));
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
                            String[] classes = {"archer", "berserk", "healer", "mage", "tank"};
                            for (String str : classes)
                            {
                                int num = get(-1, prefix + "most_damage_" + str + "." + i);
                                if (num > damage)
                                {
                                    damage = num;
                                    damageClass = switch (str)
                                    {
                                        case "archer" -> "Archer";
                                        case "berserk" -> "Berserker";
                                        case "healer" -> "Healer";
                                        case "mage" -> "Mage";
                                        case "tank" -> "Tank";
                                        default -> "UNKNOWN";
                                    };
                                }
                            }
                            return new SBDungeon(i, false, runs, tier, milestone, mobs, score, watcher, mostMobs, time, timeS, timeSPlus, healing, damage, damageClass);
                        })
                        .toArray(SBDungeon[]::new));
    }
    public SBDungeon getEntrance()
    {
        SBDungeon[] floors = getNormalDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("entrance", k -> floors[0]);
    }
    public SBDungeon getFloorOne()
    {
        SBDungeon[] floors = getNormalDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("floor_one", k -> floors[1]);
    }
    public SBDungeon getFloorTwo()
    {
        SBDungeon[] floors = getNormalDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("entrance", k -> floors[2]);
    }
    public SBDungeon getFloorThree()
    {
        SBDungeon[] floors = getNormalDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("entrance", k -> floors[3]);
    }
    public SBDungeon getFloorFour()
    {
        SBDungeon[] floors = getNormalDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("entrance", k -> floors[4]);
    }
    public SBDungeon getFloorFive()
    {
        SBDungeon[] floors = getNormalDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("entrance", k -> floors[5]);
    }
    public SBDungeon getFloorSix()
    {
        SBDungeon[] floors = getNormalDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("entrance", k -> floors[6]);
    }
    public SBDungeon getFloorSeven()
    {
        SBDungeon[] floors = getNormalDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("entrance", k -> floors[7]);
    }

    public SBDungeon[] getMasterDungeons()
    {
        return (SBDungeon[]) internalApiMap.computeIfAbsent("master_dungeons", k ->
                IntStream
                        .range(1, 8)
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
                            String[] classes = {"archer", "berserk", "healer", "mage", "tank"};
                            for (String str : classes)
                            {
                                int num = get(-1, prefix + "most_damage_" + str + "." + i);
                                if (num > damage)
                                {
                                    damage = num;
                                    damageClass = switch (str)
                                    {
                                        case "archer" -> "Archer";
                                        case "berserk" -> "Berserker";
                                        case "healer" -> "Healer";
                                        case "mage" -> "Mage";
                                        case "tank" -> "Tank";
                                        default -> "UNKNOWN";
                                    };
                                }
                            }
                            return new SBDungeon(i, true, runs, tier, milestone, mobs, score, watcher, mostMobs, time, timeS, timeSPlus, healing, damage, damageClass);
                        })
                        .toArray(SBDungeon[]::new));
    }
    public SBDungeon getMasterFloorOne()
    {
        SBDungeon[] floors = getMasterDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("master_floor_one", k -> floors[0]);
    }
    public SBDungeon getMasterFloorTwo()
    {
        SBDungeon[] floors = getMasterDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("master_floor_two", k -> floors[1]);
    }
    public SBDungeon getMasterFloorThree()
    {
        SBDungeon[] floors = getMasterDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("master_floor_three", k -> floors[2]);
    }
    public SBDungeon getMasterFloorFour()
    {
        SBDungeon[] floors = getMasterDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("master_floor_four", k -> floors[3]);
    }
    public SBDungeon getMasterFloorFive()
    {
        SBDungeon[] floors = getMasterDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("master_floor_five", k -> floors[4]);
    }
    public SBDungeon getMasterFloorSix()
    {
        SBDungeon[] floors = getMasterDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("master_floor_six", k -> floors[5]);
    }
    public SBDungeon getMasterFloorSeven()
    {
        SBDungeon[] floors = getMasterDungeons();
        return (SBDungeon) internalApiMap.computeIfAbsent("master_floor_seven", k -> floors[6]);
    }
}
