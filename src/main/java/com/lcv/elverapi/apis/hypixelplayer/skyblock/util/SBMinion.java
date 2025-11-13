package com.lcv.elverapi.apis.hypixelplayer.skyblock.util;

public class SBMinion
{
    public final String name;
    public final int highestLevel;
    private final boolean[] tiersUnlocked;

    public SBMinion(String name, boolean[] tiersUnlocked)
    {
        this.name = name;
        this.tiersUnlocked = tiersUnlocked;
        this.highestLevel = tiersUnlocked.length;
    }
}
