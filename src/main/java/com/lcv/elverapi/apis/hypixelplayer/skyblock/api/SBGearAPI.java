package com.lcv.elverapi.apis.hypixelplayer.skyblock.api;

import com.lcv.elverapi.apis.SubApi;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.SkyblockAPI;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.util.SBArmor;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.util.SBItem;
import me.nullicorn.nedit.NBTReader;
import me.nullicorn.nedit.type.NBTCompound;
import me.nullicorn.nedit.type.NBTList;

import java.io.IOException;
import java.util.stream.IntStream;

@SuppressWarnings("ConstantConditions")
public class SBGearAPI extends SubApi
{
    public SBGearAPI(SkyblockAPI parent)
    {
        super(parent, "members." + parent.getUuid() + ".inventory");
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

    public SBArmor getArmor()
    {
        return (SBArmor) internalApiMap.computeIfAbsent("armor", k -> {
            NBTList list = parseBase64(get(null, "inv_armor.data")).getList("i");
            return new SBArmor(IntStream
                    .range(0, list.size())
                    .mapToObj(i -> new SBItem(list.getCompound(3 - i)))
                    .toArray(SBItem[]::new));
        });
    }
    public SBItem[] getEquipment()
    {
        return (SBItem[]) internalApiMap.computeIfAbsent("equipment", k -> {
            NBTList list = parseBase64(get(null, "equipment_contents.data")).getList("i");
            return IntStream.range(0, list.size())
                    .mapToObj(i -> new SBItem(list.getCompound(3 - i)))
                    .toArray(SBItem[]::new);
        });
    }
    public SBArmor[] getWardrobe()
    {
        return (SBArmor[]) internalApiMap.computeIfAbsent("wardrobe", k ->
        {
            NBTList list = parseBase64(get(null, "wardrobe_contents.data")).getList("i");
            return IntStream
                    .range(0, 18)
                    .mapToObj(i -> new SBArmor(IntStream
                            .range(0, 4)
                            .mapToObj(j -> new SBItem(list.getCompound((i / 9) * 36 + (i % 9) + (j * 9))))
                            .toArray(SBItem[]::new)))
                    .toArray(SBArmor[]::new);
        });
    }
}
