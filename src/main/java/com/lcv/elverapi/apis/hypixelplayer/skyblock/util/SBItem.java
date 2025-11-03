package com.lcv.elverapi.apis.hypixelplayer.skyblock.util;

import me.nullicorn.nedit.type.NBTCompound;

public class SBItem
{
    private final String name;
    private final int id;
    private final boolean exists;
    private final String[] lore;
    private final NBTCompound attributes;
    private final NBTCompound nbt;

    public SBItem(NBTCompound nbt)
    {
        this.name = nbt.getString("tag.display.Name", "EMPTY");
        this.id = nbt.getInt("id", -1);
        this.exists = !this.name.equals("EMPTY");
        this.lore = (exists) ? nbt.getList("tag.display.Lore").toArray(String[]::new) : null;
        this.attributes = (exists) ? nbt.getCompound("tag.ExtraAttributes") : null;
        this.nbt = nbt;
    }

    public String getName()
    {
        return name;
    }
    public int getId()
    {
        return id;
    }
    public boolean exists()
    {
        return exists;
    }
    public String[] getLore()
    {
        return lore;
    }
    public NBTCompound getAttributes()
    {
        return attributes;
    }
    public NBTCompound getNbt()
    {
        return nbt;
    }
    @Override
    public String toString()
    {
        return name + " " + id;
    }
}
