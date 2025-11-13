package com.lcv.elverapi.apis.hypixelplayer.skyblock.util;

public class SBSlayer
{
    public final String name;
    public final int xp;
    public final int level;
    public final int overflowXp;
    public final int tier1;
    public final int tier2;
    public final int tier3;
    public final int tier4;
    public final int tier5;
    public final int total;

    private final boolean exists;

    public SBSlayer(String name, int xp, int tier1, int tier2, int tier3, int tier4, int tier5)
    {
        this.name = name;
        this.xp = xp;
        this.level = calculateLevel(xp);
        this.overflowXp = calculateOverflowXp(xp);
        this.tier1 = tier1;
        this.tier2 = tier2;
        this.tier3 = tier3;
        this.tier4 = tier4;
        this.tier5 = tier5;
        this.total = tier1 + tier2 + tier3 + tier4 + tier5;

        this.exists = (xp | tier1 | tier2 | tier3 | tier4 | tier5) != -1;
    }

    public boolean exists()
    {
        return exists;
    }

    public static int calculateLevel(int xp)
    {
        return -1;
    }
    public static int calculateOverflowXp(int xp)
    {
        return -1;
    }
}
