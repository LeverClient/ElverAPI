package com.lcv.elverapi.apis.hypixelplayer.skyblock.util;

public class SBDungeon
{
    private final int runs;
    private final int tier;
    private final int milestone;
    private final int mobs;
    private final int score;
    private final int watcher;
    private final int time;
    private final int timeS;
    private final int timeSPlus;
    private final int healing;
    private final int damage;
    private final String damageClass;

    public SBDungeon(int runs, int tier, int milestone, int mobs, int score, int watcher, int time, int timeS, int timeSPlus, int healing, int damage, String damageClass)
    {
        this.runs = runs;
        this.tier = tier;
        this.milestone = milestone;
        this.mobs = mobs;
        this.score = score;
        this.watcher = watcher;
        this.time = time;
        this.timeS = timeS;
        this.timeSPlus = timeSPlus;
        this.healing = healing;
        this.damage = damage;
        this.damageClass = damageClass;
    }

    public int getRunsCompleted()
    {
        return runs;
    }
    public int getTierCompletions()
    {
        return tier;
    }
    public int getMilestoneCompletions()
    {
        return milestone;
    }
    public int getMobsKilled()
    {
        return mobs;
    }
    public int getBestScore()
    {
        return score;
    }
    public int getWatcherKills()
    {
        return watcher;
    }
    public int getFastestTime()
    {
        return time;
    }
    public int getFastestTimeS()
    {
        return timeS;
    }
    public int getFastestTimeSPlus()
    {
        return timeSPlus;
    }
    public int getMostHealing()
    {
        return healing;
    }
    public int getMostDamage()
    {
        return damage;
    }
    public String getMostDamageClass()
    {
        return damageClass;
    }
}
