package com.lcv.elverapi.apis.hypixelplayer.skyblock.util;

public class SBDungeon
{
    public final int runs;
    public final int tier;
    public final int milestone;
    public final int mobs;
    public final int score;
    public final int watcher;
    public final int mostMobs;
    public final int time;
    public final int timeS;
    public final int timeSPlus;
    public final int healing;
    public final int damage;
    public final String damageClass;

    public SBDungeon(int runs, int tier, int milestone, int mobs, int score, int watcher, int mostMobs, int time, int timeS, int timeSPlus, int healing, int damage, String damageClass)
    {
        this.runs = runs;
        this.tier = tier;
        this.milestone = milestone;
        this.mobs = mobs;
        this.score = score;
        this.watcher = watcher;
        this.mostMobs = mostMobs;
        this.time = time;
        this.timeS = timeS;
        this.timeSPlus = timeSPlus;
        this.healing = healing;
        this.damage = damage;
        this.damageClass = damageClass;
    }
}
