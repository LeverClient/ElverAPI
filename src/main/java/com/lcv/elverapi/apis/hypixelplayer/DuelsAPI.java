package com.lcv.elverapi.apis.hypixelplayer;

import com.lcv.elverapi.apis.SubApi;

import java.util.HashMap;

public class DuelsAPI extends SubApi
{
    public DuelsAPI(HypixelPlayerAPI parent)
    {
        super(parent, "stats.Duels");
    }

    public int getWins()
    {
        return (int) internalApiMap.computeIfAbsent("wins", (k) -> get(0, "wins"));
    }

    public int getLosses()
    {
        return (int) internalApiMap.computeIfAbsent("losses", (k) -> get(0, "losses"));
    }

    public double getWLR()
    {
        int wins = getWins();
        int losses = getLosses();
        return (double) internalApiMap.computeIfAbsent("wlr", (k) -> (double) wins / losses);
    }

    public int getKills()
    {
        return (int) internalApiMap.computeIfAbsent("kills", (k) -> get(0, "kills"));
    }

    public int getDeaths()
    {
        return (int) internalApiMap.computeIfAbsent("deaths", (k) -> get(0, "deaths"));
    }

    public double getKDR()
    {
        int kills = getKills();
        int deaths = getDeaths();
        return (double) internalApiMap.computeIfAbsent("kdr", (k) -> (double) kills / deaths);
    }

    public int getBowShot()
    {
        return (int) internalApiMap.computeIfAbsent("bow_shot", (k) -> get(0, "bow_shots"));
    }

    public int getBowHit()
    {
        return (int) internalApiMap.computeIfAbsent("bow_hit", (k) -> get(0, "bow_hits"));
    }

    public double getBowAccuracy()
    {
        int bowHit = getBowHit();
        int bowShot = getBowShot();
        return (double) internalApiMap.computeIfAbsent("bow_accuracy", (k) -> (double) bowHit / bowShot * 100);
    }

    public int getSwordSwung()
    {
        return (int) internalApiMap.computeIfAbsent("sword_swing", (k) -> get(0, "melee_swings"));
    }

    public int getSwordHit()
    {
        return (int) internalApiMap.computeIfAbsent("sword_hit", (k) -> get(0, "melee_hits"));
    }

    public double getSwordAccuracy()
    {
        int swordHit = getSwordHit();
        int swordSwung = getSwordSwung();
        return (double) internalApiMap.computeIfAbsent("sword_accuracy", (k) -> (double) swordHit / swordSwung * 100);
    }

    public int getHealthHealed()
    {
        return (int) internalApiMap.computeIfAbsent("health_healed", (k) -> get(0, "health_regenerated"));
    }

    public int getDamageDealt()
    {
        return (int) internalApiMap.computeIfAbsent("damage_dealt", (k) -> get(0, "damage_dealt"));
    }

    public int getCoins()
    {
        return (int) internalApiMap.computeIfAbsent("coins", (k) -> get(0, "coins"));
    }

    public int getPingPreference()
    {
        return (int) internalApiMap.computeIfAbsent("ping_preference", (k) -> get(0, "pingPreference"));
    }

    public int getLevel()
    {
        String prestige = getPrestige();
        int level = 0;
        String s = "all_modes_" + prestige.toLowerCase() + "_title_prestige";
        if (get(null, s) != null)
            level = get(0, s);
        final int finalLevel = level;
        return (int) internalApiMap.computeIfAbsent("level", (k) -> finalLevel);
    }

    public String getPrestige()
    {
        int wins = getWins();
        String prestige = "";
        for (int i = 0; i < PRESTIGE_LIST.length; i++)
        {
            prestige = PRESTIGE_LIST[i];
            if (wins < PRESTIGE_WIN_LIST[i])
                break;
        }
        final String finalPrestige = prestige;
        return (String) internalApiMap.computeIfAbsent("prestige", (k) -> finalPrestige);
    }

    public String getPrestigeFormatted()
    {
        String prestige = getPrestige();
        return (String) internalApiMap.computeIfAbsent("prestige_formatted", (k) -> formatRank(prestige));
    }

    public double getPrestigePercentage()
    {
        int wins = getWins();
        int overflowWins = 0;
        int presWinReq = 0;
        for (int i = 0; i < PRESTIGE_LIST.length; i++)
        {
            if (wins < PRESTIGE_WIN_LIST[i])
                break;
            overflowWins = wins - PRESTIGE_WIN_LIST[i];
            presWinReq = PRESTIGE_WIN_LIST[i + 1] - PRESTIGE_WIN_LIST[i];
        }
        double percentage = (double) overflowWins / presWinReq * 100;
        return (double) internalApiMap.computeIfAbsent("prestige_percentage", (k) -> percentage);
    }

    public String getNextPrestige()
    {
        String prestige = getPrestige();
        String nextPrestige = "";
        for (int i = 0; i < PRESTIGE_LIST.length; i++)
        {
            if (prestige.equals(PRESTIGE_LIST[i]))
            {
                nextPrestige = PRESTIGE_LIST[i + 1];
                break;
            }
        }
        final String finalNextPrestige = nextPrestige;
        return (String) internalApiMap.computeIfAbsent("next_prestige", (k) -> finalNextPrestige);
    }

    public String getNextPrestigeFormatted()
    {
        String nextPrestige = getNextPrestige();
        return (String) internalApiMap.computeIfAbsent("next_prestige_formatted", (k) -> formatRank(nextPrestige));
    }

    public String getRank()
    {
        String prestige = getPrestige();
        StringBuilder rank = new StringBuilder(prestige + " ");
        int level = getLevel();
        String[] romanLet = {"L", "XL", "X", "IX", "V", "IV", "I"};
        int[] romanNum = {50, 40, 10, 9, 5, 4, 1};
        for (int i = 0; i < romanLet.length; i++)
        {
            while (romanNum[i] <= level)
            {
                rank.append(romanLet[i]);
                level -= romanNum[i];
            }
        }
        final String finalRank = rank.toString();
        return (String) internalApiMap.computeIfAbsent("rank", (k) -> finalRank);
    }

    public String getRankFormatted()
    {
        String rank = getRank();
        return (String) internalApiMap.computeIfAbsent("rank_formatted", (k) -> formatRank(rank));
    }

    public double getRankPercentage()
    {
        int wins = getWins();
        int level = getLevel();
        int overflowWins = 0;
        int rankWinReq = 0;
        for (int i = 0; i < PRESTIGE_LIST.length; i++)
        {
            if (wins < PRESTIGE_WIN_LIST[i])
                break;
            overflowWins = wins - PRESTIGE_WIN_LIST[i];
            rankWinReq = RANK_WIN_LIST[i + 1];
        }
        overflowWins -= (level - 1) * rankWinReq;
        double percentage = (double) overflowWins / rankWinReq * 100;
        return (double) internalApiMap.computeIfAbsent("rank_percentage", (k) -> percentage);
    }

    public String[] getRecentlyPlayed()
    {
        return (String[]) internalApiMap.computeIfAbsent("recent_played", (k) -> get("","duels_recently_played2").split("#"));
    }

    public static String formatRank(String rank)
    {
        return PRESTIGE_COLOR_MAP.get(rank.split(" ")[0]) + rank;
    }

    public static HashMap<String, String> PRESTIGE_COLOR_MAP = new HashMap<>();

    static
    {
        PRESTIGE_COLOR_MAP.put("None", "§7");
        PRESTIGE_COLOR_MAP.put("Rookie", "§7");
        PRESTIGE_COLOR_MAP.put("Iron", "§f");
        PRESTIGE_COLOR_MAP.put("Gold", "§6");
        PRESTIGE_COLOR_MAP.put("Diamond", "§3");
        PRESTIGE_COLOR_MAP.put("Master", "§2");
        PRESTIGE_COLOR_MAP.put("Legend", "§4");
        PRESTIGE_COLOR_MAP.put("Grandmaster", "§e");
        PRESTIGE_COLOR_MAP.put("Godlike", "§5");
        PRESTIGE_COLOR_MAP.put("Celestial", "§b");
        PRESTIGE_COLOR_MAP.put("Divine", "§d");
        PRESTIGE_COLOR_MAP.put("Ascended", "§c");
        PRESTIGE_COLOR_MAP.put("MAX", "§6");
    }

    public static final int[] PRESTIGE_WIN_LIST = {100, // none -> rookie
            200, // rookie -> iron
            500, // iron -> gold
            1000, // gold -> diamond
            2000, // diamond -> master
            4000, // master -> legend
            10000, // legend -> grandmaster
            20000, // grandmaster -> godlike
            50000, // godlike -> celestial
            100000, // celestial -> divine
            200000, // divine -> ascended
            1180000, // ascended -> max (ascended 50)
            Integer.MAX_VALUE // max (ascended 50)
    };

    public static final int[] RANK_WIN_LIST = {100, // none -> rookie
            20, // rookie -> iron
            60, // iron -> gold
            100, // gold -> diamond
            200, // diamond -> master
            400, // master -> legend
            1200, // legend -> grandmaster
            2000, // grandmaster -> godlike
            6000, // godlike -> celestial
            10000, // celestial -> divine
            20000, // divine -> ascended
            20000, // ascended -> max (ascended 50)
            Integer.MAX_VALUE // max (ascended 50)
    };

    public static final String[] PRESTIGE_LIST = {"None", "Rookie", "Iron", "Gold", "Diamond", "Master", "Legend", "Grandmaster", "Godlike", "Celestial", "Divine", "Ascended", "MAX"};
}
