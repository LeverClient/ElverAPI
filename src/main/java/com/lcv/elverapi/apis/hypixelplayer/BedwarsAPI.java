package com.lcv.elverapi.apis.hypixelplayer;

import com.lcv.elverapi.apis.SubApi;

import java.util.Arrays;

public class BedwarsAPI extends SubApi {
    public BedwarsAPI(HypixelPlayerAPI parent) {
        super(parent, "stats.Bedwars");
    }

    public int getExperience() {
        return (int) internalApiMap.computeIfAbsent("exp", (k) -> {
            Object xp = get("Experience");

            if (xp instanceof Number num) {
                return num.intValue();
            }

            return xp;
        });
    }

    public double getLevel() {
        int xp = getExperience();

        return (double) internalApiMap.computeIfAbsent("level", (k) -> getLevelForExp(xp));
    }

    public String getChatFormattedLevel() {
        double level = getLevel();

        return (String) internalApiMap.computeIfAbsent("chat_level", (k) -> getFormattedLevel((int) level));
    }

    public String[] getQuickbuy() {
        return (String[]) internalApiMap.computeIfAbsent("quickbuy", (k) -> {
            String favorites = (String) get("favourites_2"); // TODO: are there more api fields for this?

            if (favorites == null) return new String[0];

            String[] quickbuy = favorites.split(",");

            // change some names around
            for (int i = 0; i < quickbuy.length; i++) {
                String item = quickbuy[i];
                String newName = switch(item) {
                    case "stick_(knockback_i)" -> "kb_stick";
                    case "bow_(power_i)" -> "power_bow";
                    case "bow_(power_i__punch_i)" -> "punch_bow";

                    case "wooden_pickaxe" -> "wood_pickaxe";
                    case "wooden_axe" -> "wood_axe";

                    case "blast-proof_glass" -> "glass";
                    case "hardened_clay" -> "clay";

                    case "speed_ii_potion_(45_seconds)" -> "speed_pot";
                    case "jump_v_potion_(45_seconds)" -> "jump_pot";
                    case "invisibility_potion_(30_seconds)" -> "invis_pot";

                    case "water_bucket" -> "water";
                    case "magic_milk" -> "milk";
                    case "golden_apple" -> "gapple";
                    case "dream_defender" -> "golem";
                    case "compact_pop-up_tower" -> "popup_tower";

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
    public final static String[] bedwarsPrestigeColors = {
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
    public final static String[] bedwarsPrestigeStars = {
            "✫",
            "✪",
            "⚝",
            "✥"
    };

    /**
     * @return a pretty bedwars level! colors and everything!
     */
    public static String getFormattedLevel(int level) {
        int prestiges = level / 100;
        int bigger_prestiges = (level - 100) / 1000; // what are these called anyway?

        // get star color & suffix
        String color = bedwarsPrestigeColors[Math.min(prestiges, bedwarsPrestigeColors.length - 1)];
        String suffix = bedwarsPrestigeStars[Math.min(bigger_prestiges, bedwarsPrestigeStars.length - 1)];
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

    public static int getXpForPrestige(double level) {
        int levelInt = (int) level;
        int respectPrestige = levelInt % 100;
        int levelXp = (int) ((level - levelInt)*5000);

        if (respectPrestige > 4) {
            return (100 - levelInt) * 5000 - levelXp;
        }

        int extraXpNeeded = 0;

        for (int i = respectPrestige; i < 5; i++) {
            extraXpNeeded += getBWExpForLevel(i);
        }

        return (100 - 4) * 5000 + extraXpNeeded - levelXp;
    }

    public static final double XP_PER_PRESTIGE = 500 + 1000 + 2000 + 3500 + 5000*96; //487000;

    // stole this code from plancke's github. i don't know how it works
    // actually i might've found it on the hypixel forums, but it's originally from here. i don't remember if i ported it to java myself
    // https://github.com/Plancke/hypixel-php/blob/2303c4bdedb650acc8315393885284dba59fdd79/src/util/games/bedwars/ExpCalculator.php
    public static int getBWExpForLevel(int level) {
        return switch (level % 100) {
            case 0 -> 0;
            case 1 -> 500;
            case 2 -> 1000;
            case 3 -> 2000;
            case 4 -> 3500;
            default -> 5000;
        };
    }

    public static double getLevelForExp(int exp) {
        int prestiges = (int) (exp / XP_PER_PRESTIGE);
        int level = prestiges * 100;
        int expWithoutPrestiges = (int) (exp - (prestiges * XP_PER_PRESTIGE));
        int xpThisLevel = 5000;
        for (int i = 1; i <= 4; i++) {
            int expForEasyLevel = getBWExpForLevel(i);
            if (expWithoutPrestiges < expForEasyLevel) {
                xpThisLevel = expForEasyLevel;
                break;
            }

            level++;
            expWithoutPrestiges -= expForEasyLevel;
        }

        return level + (double) expWithoutPrestiges / xpThisLevel;
    }

    // this method hurts me
    public static int getBedwarsLevel(int xp) {
        int level = (xp / 487000) * 100;
        for (int[] easyXP = {500, 1000, 2000, 3500};(xp %= 487000) >= (level % 100 < 4 ? easyXP[level % 100] : 5000); xp -= level % 100 < 4 ? easyXP[level % 100] : 5000, level++);
        return level;
    }
}
