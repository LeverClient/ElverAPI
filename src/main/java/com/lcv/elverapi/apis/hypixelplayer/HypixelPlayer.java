package com.lcv.elverapi.apis.hypixelplayer;

import com.lcv.elverapi.apis.Api;
import org.json.JSONObject;

import java.util.HashMap;

public class HypixelPlayer extends Api {
    public boolean hasStats;

    public String name;

    public String uuid;

    public String apiKey;


    public static HashMap<String, String> colorLookup = new HashMap<>();

    public static HashMap<String, String[]> rankLookup = new HashMap<>();

    @Override
    public String getApiEndpoint() {
        return "https://api.hypixel.net/v2/player?uuid=" + uuid + "&key=" + apiKey;
    }

    static {
        // ranks
        rankLookup.put("NONE", new String[]{"NONE"});
        rankLookup.put("VIP", new String[]{"VIP"});
        rankLookup.put("VIP_PLUS", new String[]{"VIP", "+"});
        rankLookup.put("MVP", new String[]{"MVP"});
        rankLookup.put("MVP_PLUS", new String[]{"MVP", "+"});
        rankLookup.put("SUPERSTAR", new String[]{"MVP", "++"});

        rankLookup.put("STAFF", new String[]{"ዞ"});
        rankLookup.put("YOUTUBER", new String[]{"YOUTUBE"});

        // rank colors
        colorLookup.put("NONE", "GRAY");
        colorLookup.put("VIP", "GREEN");
        colorLookup.put("VIP_PLUS", "GREEN");
        colorLookup.put("MVP", "AQUA");
        colorLookup.put("MVP_PLUS", "AQUA");
        colorLookup.put("SUPERSTAR", "-");
        colorLookup.put("STAFF", "GOLD");
        colorLookup.put("YOUTUBER", "RED");

        // just colors
        colorLookup.put("BLACK", "§0");
        colorLookup.put("DARK_BLUE", "§1");
        colorLookup.put("DARK_GREEN", "§2");
        colorLookup.put("DARK_AQUA", "§3");
        colorLookup.put("DARK_RED", "§4");
        colorLookup.put("DARK_PURPLE", "§5");
        colorLookup.put("GOLD", "§6");
        colorLookup.put("GRAY", "§7");
        colorLookup.put("DARK_GRAY", "§8");
        colorLookup.put("BLUE", "§9");
        colorLookup.put("GREEN", "§a");
        colorLookup.put("AQUA", "§b");
        colorLookup.put("RED", "§c");
        colorLookup.put("LIGHT_PURPLE", "§d");
        colorLookup.put("YELLOW", "§e");
        colorLookup.put("WHITE", "§f");
    }

    public static String getRankFormatted(String rank, String plusColor, String rankColor, String prefix) {
        StringBuilder nameFormatted = new StringBuilder();

        // rank prefix (special ranks use this, like pig+++) these are already formatted for chat for some reason
        if (prefix != null) {
            nameFormatted.append(prefix);
            nameFormatted.append(' ');

            return nameFormatted.toString();
        }

        // set rank color if it's a human name (GOLD), return early if no rank
        rankColor = colorLookup.getOrDefault(rankColor, rankColor);
        if (rank == null || rank.equals("NONE"))
            return nameFormatted.append(rankColor).append(' ').toString();


        String[] rankName = rankLookup.get(rank);
        if (rankName == null) rankName = new String[]{rank};

        // append first color, and opening bracket
        if (rank.equals("YOUTUBER") || rank.equals("STAFF")) nameFormatted.append("§c");
            else nameFormatted.append(rankColor);
        nameFormatted.append('[');

        // apply rank specific stuff (gold + for vip_plus, staff icon is gold but brackets are red)
        switch (rank) {
            case "YOUTUBER" -> nameFormatted.append("§f");
            case "STAFF" -> nameFormatted.append("§6");
            case "VIP_PLUS" -> plusColor = "GOLD";
        }

        nameFormatted.append(rankName[0]);
        if (rankName.length > 1) {
            nameFormatted.append(colorLookup.getOrDefault(plusColor, plusColor));
            nameFormatted.append(rankName[1]);
            nameFormatted.append(rankColor);
        }

        if (rank.equals("YOUTUBER") || rank.equals("STAFF")) nameFormatted.append("§c");
        nameFormatted.append("] ");

        return nameFormatted.toString();
    }

    public HypixelPlayer(String uuid, String apiKey) {
        this.apiKey = apiKey;
        this.uuid = uuid;

        doHttp();

        this.jsonObject = new JSONObject(response.body());

        noData = noData || (this.jsonObject = (JSONObject) get("player")) == null;
        hasStats = get("stats") != null;

        name = (String) get("displayname");
        this.uuid = (String) get("uuid");
    }

    // sub apis
    public Bedwars getBedwarsApi() {
        return (Bedwars) internalApiMap.computeIfAbsent("api/bedwars", (k) -> new Bedwars(this));
    }

    public long getExperience() {
        return (long) internalApiMap.computeIfAbsent("exp", (k) -> {
            Object xp = get("networkExp");

            if (xp instanceof Number num) {
                return num.longValue();
            }

            return xp;
        });
    }

    public double getLevel() {
        long xp = getExperience();

        return (double) internalApiMap.computeIfAbsent("level", (k) -> (1 + ((Math.sqrt(8750 * 8750 + 5000 * ((double) xp)) - 8750) / 2500)));
    }

    public String getRank() {
        return (String) internalApiMap.computeIfAbsent("rank", (k) -> {
            String rank = (String) get("rank", "monthlyPackageRank");
            return andThen(rank == null || rank.equals("NONE") ? get("newPackageRank", "packageRank") : rank, "NONE");
        });
    }

    public String getPlusColor() {
        return (String) internalApiMap.computeIfAbsent("plus_color", (k) -> andThen(get("rankPlusColor"), "Red"));
    }

    public String getRankColor() {
        String rank = getRank();

        return (String) internalApiMap.computeIfAbsent("rank_color", (k) -> {
            String monthlyRank = (String) get("monthlyPackageRank");
            if (monthlyRank != null && !monthlyRank.equals("NONE")) return get("monthlyRankColor");

            return colorLookup.get(rank);
        });
    }

    public String getChatFormattedRank() {
        String rank = getRank();
        String plusCol = getPlusColor();
        String rankCol = getRankColor();

        return (String) internalApiMap.computeIfAbsent("rank_minecraft", (k) -> getRankFormatted(rank, plusCol, rankCol, (String) get("prefix")));
    }

    public String getChatFormattedName() {
        String rankFormat = getChatFormattedRank();

        return (String) internalApiMap.computeIfAbsent("name_minecraft", (k) -> rankFormat + name + ":");
    }
}
