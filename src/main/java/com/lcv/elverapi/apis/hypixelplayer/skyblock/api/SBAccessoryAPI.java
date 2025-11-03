package com.lcv.elverapi.apis.hypixelplayer.skyblock.api;

import com.lcv.elverapi.apis.SubApi;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.SkyblockAPI;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.util.SBItem;
import me.nullicorn.nedit.NBTReader;
import me.nullicorn.nedit.type.NBTCompound;

import java.io.IOException;

public class SBAccessoryAPI extends SubApi
{
    public SBAccessoryAPI(SkyblockAPI parent)
    {
        super(parent, "members." + parent.getUuid());
    }
    private static NBTCompound parseBase64(String s)
    {
        try
        {
            return NBTReader.readBase64(s);
        }
        catch (IOException e)
        {
            return null;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public SBItem[] getTalismans()
    {
        return (SBItem[]) internalApiMap.computeIfAbsent("talismans", k -> parseBase64(get(null, "inventory.bag_contents.talisman_bag.data"))
                .getList("i")
                .stream()
                .map(nbt -> new SBItem((NBTCompound) nbt))
                .toArray(SBItem[]::new));
    }
    public String getPower()
    {
        return (String) internalApiMap.computeIfAbsent("power", k -> get(null, "accessory_bag_storage.selected_power"));
    }
}
