package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import me.nullicorn.nedit.type.NBTCompound;
import org.json.JSONObject;

public class SBPet
{
    private final String name;
    private final int xp;
    private final String tier;
    private final boolean isSoulbound;
    private final int candyUsed;
    private final String heldItem;
    private final String skin;

    public SBPet(JSONObject json)
    {
        this.name = json.optString("type", null);
        this.xp = json.optInt("exp", -1);
        this.tier = json.optString("tier", null);
        this.isSoulbound = json.optBoolean("petSoulbound", false);
        this.candyUsed = json.optInt("candyUsed", -1);
        this.heldItem = json.optString("heldItem", null);
        this.skin = json.optString("skin", null);
    }

    public String getName()
    {
        return name;
    }
    public int getXp()
    {
        return xp;
    }
    public String getTier()
    {
        return tier;
    }
    public boolean isSoulbound()
    {
        return isSoulbound;
    }
    public int getCandyUsed()
    {
        return candyUsed;
    }
    public String getHeldItem()
    {
        return heldItem;
    }
    public String getSkin()
    {
        return skin;
    }
}
