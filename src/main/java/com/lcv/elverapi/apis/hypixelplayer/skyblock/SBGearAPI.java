package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import com.lcv.elverapi.apis.SubApi;
import me.nullicorn.nedit.NBTReader;
import me.nullicorn.nedit.type.NBTCompound;
import me.nullicorn.nedit.type.NBTList;

import java.io.IOException;
import java.util.stream.IntStream;

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

    @SuppressWarnings("ConstantConditions")
    public SBItem[] getArmor()
    {
        return (SBItem[]) internalApiMap.computeIfAbsent("armor", k -> {
            NBTList list = parseBase64(get(null, "inv_armor.data")).getList("i");
            return IntStream.range(0, list.size())
                    .mapToObj(i -> new SBItem(list.getCompound(3 - i)))
                    .toArray(SBItem[]::new);
        });
    }
    @SuppressWarnings("ConstantConditions")
    public SBItem[] getEquipment()
    {
        return (SBItem[]) internalApiMap.computeIfAbsent("equipment", k -> {
            NBTList list = parseBase64(get(null, "equipment_contents.data")).getList("i");
            return IntStream.range(0, list.size())
                    .mapToObj(i -> new SBItem(list.getCompound(3 - i)))
                    .toArray(SBItem[]::new);
        });
    }
    @SuppressWarnings("ConstantConditions")
    public SBItem[][] getWardrobe()
    {
        return (SBItem[][]) internalApiMap.computeIfAbsent("wardrobe", k -> {
            NBTList list = parseBase64(get(null, "wardrobe_contents.data")).getList("i");
            return IntStream.range(0, 18)
                    .mapToObj(i -> IntStream.range(0, 4)
                            .mapToObj(j -> new SBItem(list.getCompound((i / 9) * 36 + (i % 9) + (j * 9))))
                            .toArray(SBItem[]::new))
                    .toArray(SBItem[][]::new);
        });
    }
}
