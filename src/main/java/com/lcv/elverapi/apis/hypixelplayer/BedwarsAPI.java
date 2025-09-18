package com.lcv.elverapi.apis.hypixelplayer;

import com.lcv.elverapi.apis.SubApi;

import java.util.Arrays;

public class BedwarsAPI extends SubApi {
    public BedwarsAPI(HypixelPlayerAPI parent) {
        super(parent, "stats.Bedwars");
    }

    public int getIron() {
        return (int) internalApiMap.computeIfAbsent("iron", (k) -> get("iron_resources_collected_bedwars"));
    }

    public int getGold() {
        return (int) internalApiMap.computeIfAbsent("gold", (k) -> get("gold_resources_collected_bedwars"));
    }

    public int getDiamond() {
        return (int) internalApiMap.computeIfAbsent("diamond", (k) -> get("diamond_resources_collected_bedwars"));
    }

    public int getEmerald() {
        return (int) internalApiMap.computeIfAbsent("emerald", (k) -> get("emerald_resources_collected_bedwars"));
    }

    public int getWins() {
        return (int) internalApiMap.computeIfAbsent("wins", (k) -> get("wins_bedwars"));
    }

    public int getLosses() {
        return (int) internalApiMap.computeIfAbsent("losses", (k) -> get("losses_bedwars"));
    }

    public double getWLR() {
        double wlr = (double) getWins() / getLosses();
        return (double) internalApiMap.computeIfAbsent("wlr", (k) -> wlr);
    }

    public int getFinalKills() {
        return (int) internalApiMap.computeIfAbsent("final_kills", (k) -> get("final_kills_bedwars"));
    }

    public int getFinalDeaths() {
        return (int) internalApiMap.computeIfAbsent("final_deaths", (k) -> get("final_deaths_bedwars"));
    }

    public double getFKDR() {
        double fkdr = (double) getFinalKills() / getFinalDeaths();
        return (double) internalApiMap.computeIfAbsent("fkdr", (k) -> fkdr);
    }

    public int getKills() {
        return (int) internalApiMap.computeIfAbsent("kills", (k) -> get("kills_bedwars"));
    }

    public int getDeaths() {
        return (int) internalApiMap.computeIfAbsent("deaths", (k) -> get("deaths_bedwars"));
    }

    public double getKDR() {
        double kdr = (double) getKills() / getDeaths();
        return (double) internalApiMap.computeIfAbsent("kdr", (k) -> kdr);
    }

    public int getBedsBroken() {
        return (int) internalApiMap.computeIfAbsent("beds_broken", (k) -> get("beds_broken_bedwars"));
    }

    public int getBedsLost() {
        return (int) internalApiMap.computeIfAbsent("beds_lost", (k) -> get("beds_lost_bedwars"));
    }

    public double getBBLR() {
        double bblr = (double) getBedsBroken() / getBedsLost();
        return (double) internalApiMap.computeIfAbsent("bblr", (k) -> bblr);
    }

    private static final int XP_PER_PRESTIGE = 500 + 1000 + 2000 + 3500 + (5000 * 96); //487000

    public int getXp() {
        return (int) internalApiMap.computeIfAbsent("xp", (k) -> get("Experience"));
    }

    public int getLevel() {
        int xp = getXp();
        return (int) internalApiMap.computeIfAbsent("level", (k) -> calculateLevel(xp));
    }

    public String getLevelFormatted() {
        int level = getLevel();
        return (String) internalApiMap.computeIfAbsent("level_formatted", (k) -> formatRank(level));
    }

    public double getLevelPercentage() {
        int level = getLevel();
        int nextLevel = level + 1;
        int xp = getXp();
        int overflowXp = xp - calculateXp(level);
        int xpRequirement = switch (nextLevel % 100) {
            case 1 -> 500;
            case 2 -> 1000;
            case 3 -> 2000;
            case 4 -> 3500;
            default -> 5000;
        };
        double percentage = (double) overflowXp / xpRequirement * 100;
        return (double) internalApiMap.computeIfAbsent("level_percentage", (k) -> percentage);
    }

    public static int calculateLevel(int xp) {
        int level = (xp / XP_PER_PRESTIGE) * 100;
        int overflowXp = xp - calculateXp(level);
        while (true) {
            int xpReq = switch (level % 100) {
                case 0 -> 500;
                case 1 -> 1000;
                case 2 -> 2000;
                case 3 -> 3500;
                default -> 5000;
            };
            if (xpReq > overflowXp)
                break;
            overflowXp -= xpReq;
            level++;
        }
        return level;
    }

    public static int calculateXp(int level) {
        int prestige = level / 100;
        int xp = prestige * XP_PER_PRESTIGE;
        int remainingLevels = level - (prestige * 100);
        while (remainingLevels > 0) {
            xp += switch (remainingLevels) {
                case 1 -> 500;
                case 2 -> 1000;
                case 3 -> 2000;
                case 4 -> 3500;
                default -> 5000;
            };
            remainingLevels--;
        }
        return xp;
    }

    public int getNextPrestige() {
        int nextPrestige = ((getLevel() / 100) + 1) * 100;
        return (int) internalApiMap.computeIfAbsent("next_prestige", (k) -> nextPrestige);
    }

    public String getNextPrestigeFormatted() {
        int nextPrestige = getNextPrestige();
        return (String) internalApiMap.computeIfAbsent("next_prestige_formatted", (k) -> formatRank(nextPrestige));
    }

    public double getPrestigePercentage() {
        int xp = getXp();
        int level = getLevel();
        int prestige = (level / 100) * 100;
        int overflowXp = xp - calculateXp(prestige);
        double percentage = (double) overflowXp / XP_PER_PRESTIGE * 100;
        return (double) internalApiMap.computeIfAbsent("prestige_percentage", (k) -> percentage);
    }

    public String[] getQuickbuy() {
        return (String[]) internalApiMap.computeIfAbsent("quickbuy", (k) -> {
            String favorites = (String) get("favourites_2");

            if (favorites == null) return new String[0];

            String[] quickbuy = favorites.split(",");

            // change some names around
            for (int i = 0; i < quickbuy.length; i++) {
                String item = quickbuy[i];
                String newName = switch (item) {
                    // blocks
                    case "wool" -> "wool";
                    case "hardened_clay" -> "clay";
                    case "wood", "oak_wood_planks" -> "wood";
                    case "end_stone" -> "end_stone";
                    case "blast-proof_glass" -> "glass";
                    case "ladder" -> "ladder";
                    case "obsidian" -> "obsidian";

                    // melee
                    case "stone_sword" -> "stone_sword";
                    case "iron_sword" -> "iron_sword";
                    case "diamond_sword" -> "diamond_sword";
                    case "stick_(knockback_i)" -> "kb_stick";

                    // armor
                    case "chainmail_boots" -> "chainmail_armor";
                    case "iron_boots" -> "iron_armor";
                    case "diamond_boots" -> "diamond_armor";

                    // tools
                    case "wooden_axe" -> "axe";
                    case "wooden_pickaxe" -> "pickaxe";
                    case "shears" -> "shears";

                    // ranged
                    case "arrow" -> "arrow";
                    case "bow" -> "bow";
                    case "bow_(power_i)" -> "power_bow";
                    case "bow_(power_i__punch_i)" -> "punch_bow";

                    // potions
                    case "speed_ii_potion_(45_seconds)" -> "speed_potion";
                    case "jump_v_potion_(45_seconds)" -> "jump_potion";
                    case "invisibility_potion_(30_seconds)" -> "invisibility_potion";

                    // utility
                    case "golden_apple" -> "golden_apple";
                    // bedbug uh oh
                    case "dream_defender" -> "dream_defender";
                    case "fireball" -> "fireball";
                    case "tnt" -> "tnt";
                    case "ender_pearl" -> "ender_pearl";
                    case "water_bucket" -> "water_bucket";
                    case "bridge_egg" -> "bridge_egg";
                    case "magic_milk" -> "magic_milk";
                    case "sponge" -> "sponge";
                    case "compact_pop-up_tower" -> "pop-up_tower";

                    case "", " ", "null" -> "none";
                    default -> item;
                };
                quickbuy[i] = newName;
            }
            return quickbuy;
        });
    }

    public String[] getHotbar() {
        return (String[]) internalApiMap.computeIfAbsent("hotbar", (k) -> {
            String favorites = (String) get("favorite_slots");
            if (favorites == null) return new String[0];
            return favorites.split(",");
        });
    }

    // bedwars stars. %x$s = (x+3)th character of the level. including star. 0 = full string, 1 = full level, 2 = star. (i think)
    public final static String[] BEDWARS_PRESTIGE_COLORS = {
            "§7 [ %1$s %2$s", // stone 0
            "§f [ %1$s %2$s", // iron 100
            "§6 [ %1$s %2$s", // gold 200
            "§b [ %1$s %2$s", // diamond 300
            "§2 [ %1$s %2$s", // emerald 400
            "§3 [ %1$s %2$s", // sapphire 500
            "§4 [ %1$s %2$s", // ruby 600
            "§d [ %1$s %2$s", // crystal 700
            "§9 [ %1$s %2$s", // opal 800
            "§5 [ %1$s %2$s", // amethyst 900
            "§c [ §6 %3$s §e %4$s §a %5$s §b %6$s §d %2$s §5", // rainbow 1000

            "§7 [ §f %1$s §7 %2$s §7", // iron prime 1100
            "§7 [ §e %1$s §6 %2$s §7", // gold prime 1200
            "§7 [ §b %1$s §3 %2$s §7", // diamond prime 1300
            "§7 [ §a %1$s §2 %2$s §7", // emerald prime 1400
            "§7 [ §3 %1$s §9 %2$s §7", // sapphire prime 1500
            "§7 [ §c %1$s §4 %2$s §7", // ruby prime 1600
            "§7 [ §d %1$s §5 %2$s §7", // crystal prime 1700
            "§7 [ §9 %1$s §1 %2$s §7", // opal prime 1800
            "§7 [ §5 %1$s §8 %2$s §7", // amethyst prime 1900

            "§8 [ §7 %3$s §f %4$s %5$s §7 %6$s %2$s §8", // mirror 2000
            "§f [ %3$s §e %4$s %5$s §6 %6$s %2$s", // light 2100
            "§6 [ %3$s §7 %4$s %5$s §b %6$s §3 %2$s", // dawn 2200
            "§5 [ %3$s §d %4$s %5$s §6 %6$s §e %2$s", // dusk 2300
            "§b [ %3$s §f %4$s %5$s §7 %6$s %2$s", // air 2400
            "§f [ %3$s §a %4$s %5$s §2 %6$s %2$s", // wind 2500
            "§4 [ %3$s §c %4$s %5$s §d %6$s %2$s", // nebula 2600
            "§e [ %3$s §f %4$s %5$s §8 %6$s %2$s", // thunder 2700
            "§a [ %3$s §2 %4$s %5$s §6 %6$s %2$s", // earth 2800
            "§b [ %3$s §3 %4$s %5$s §9 %6$s %2$s", // water 2900
            "§e [ %3$s §6 %4$s %5$s §c %6$s %2$s", // fire 3000

            "§9 [ %3$s §3 %4$s %5$s §6 %6$s %2$s §e", // 3100
            "§c [ §4 %3$s §7 %4$s %5$s §4 %6$s §c %2$s", // 3200
            "§9 [ %3$s %4$s §d %5$s §c %6$s %2$s §4", // 3300
            "§2 [ §a %3$s §d %4$s %5$s §5 %6$s %2$s §2", // 3400
            "§c [ %3$s §4 %4$s %5$s §2 %6$s §a %2$s", // 3500
            "§a [ %3$s %4$s §b %5$s §9 %6$s %2$s §1", // 3600
            "§4 [ %3$s §c %4$s %5$s §b %6$s §3 %2$s", // 3700
            "§1 [ %3$s §9 %4$s §5 %5$s %6$s §d %2$s §1", // 3800
            "§c [ %3$s §a %4$s %5$s §3 %6$s §9 %2$s", // 3900
            "§5 [ %3$s §c %4$s %5$s §6 %6$s %2$s §e", // 4000
            "§e [ %3$s §6 %4$s §c %5$s §d %6$s %2$s §5", // 4100
            "§1 [ §9 %3$s §3 %4$s §b %5$s §f %6$s §7 %2$s", // 4200
            "§0 [ §5 %3$s §8 %4$s %5$s §5 %6$s %2$s §0", // 4300
            "§2 [ %3$s §a %4$s §e %5$s §6 %6$s §5 %2$s §d", // 4400
            "§f [ %3$s §b %4$s %5$s §3 %6$s %2$s", // 4500
            "§3 [ §b %3$s §e %4$s %5$s §6 %6$s §d %2$s §5", // 4600
            "§f [ §4 %3$s §c %4$s %5$s §9 %6$s §1 %2$s §9", // 4700
            "§5 [ %3$s §c %4$s §6 %5$s §e %6$s §b %2$s §3", // 4800
            "§2 [ §a %3$s §f %4$s %5$s §a %6$s %2$s §2", // 4900
            "§4 [ %3$s §5 %4$s §9 %5$s %6$s §1 %2$s §0", // 5000
    };

    // bedwars stars. each index in the array corresponds to 1000 stars higher
    public final static String[] BEDWARS_PRESTIGE_STARS = {
            "✫",
            "✪",
            "⚝",
            "✥"
    };

    public static String formatRank(int level) {
        int prestige = level / 100;
        int bigger_prestige = (level - 100) / 1000;

        // get star color & suffix
        String color = BEDWARS_PRESTIGE_COLORS[Math.min(prestige, BEDWARS_PRESTIGE_COLORS.length - 1)];
        String suffix = BEDWARS_PRESTIGE_STARS[Math.min(bigger_prestige, BEDWARS_PRESTIGE_STARS.length - 1)];
        String levelStr = Integer.toString(level);
        int levelLen = levelStr.length();

        // list of only numbers
        // groups[0] is the entire number
        // groups[1] is the star
        // after that it's just each character of the number

        Object[] groups = new String[levelLen + 2];
        groups[0] = levelStr;
        groups[1] = suffix;
        for (int i = 0; i < levelLen; i++) {
            groups[i + 2] = levelStr.substring(i, i + 1);
        }

        // sob sob 5 digit sob
        if (levelLen > 4) {
            groups[3 + 2] = levelStr.substring(3);
        }

        // format the correct regex with each character.
        try {
            return String.format(color, groups).replace(" ", "") + "]";
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            return "§c[ERR]";
        }
    }
}
