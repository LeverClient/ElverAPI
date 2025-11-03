package com.lcv.elverapi.apis.hypixelplayer.skyblock.api;

import com.lcv.elverapi.apis.SubApi;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.SkyblockAPI;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.util.SBContainer;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.util.SBItem;
import me.nullicorn.nedit.NBTReader;
import me.nullicorn.nedit.type.NBTCompound;
import me.nullicorn.nedit.type.NBTList;
import org.json.JSONObject;

import java.io.IOException;
import java.util.stream.IntStream;

@SuppressWarnings("ConstantConditions")
public class SBInventoryAPI extends SubApi
{
    public SBInventoryAPI(SkyblockAPI parent)
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

    public SBContainer getInventory()
    {
        return (SBContainer) internalApiMap.computeIfAbsent("inventory", k -> new SBContainer(parseBase64(get(null, "inv_contents.data"))
                .getList("i")
                .stream()
                .map(nbt -> new SBItem((NBTCompound) nbt))
                .toArray(SBItem[]::new)));
    }
    public SBContainer[] getBackpacks()
    {
        return (SBContainer[]) internalApiMap.computeIfAbsent("backpacks", k ->
                IntStream
                        .range(0, 18)
                        .mapToObj(i -> new SBContainer(parseBase64(get(new JSONObject(), "backpack_contents")
                                .optJSONObject(String.valueOf(i))
                                .optString("data"))
                                .getList("i")
                                .stream()
                                .map(o -> new SBItem((NBTCompound) o))
                                .toArray(SBItem[]::new)))
                        .toArray(SBContainer[]::new));
    }
    public SBContainer[] getEnderChest()
    {
        return (SBContainer[]) internalApiMap.computeIfAbsent("ender_chest", k -> {
            NBTList list = parseBase64(get(null, "ender_chest_contents.data")).getList("i");
            return IntStream
                    .range(0, list.toArray().length / 45)
                    .mapToObj(i -> new SBContainer(
                            IntStream
                                    .range(0, 45)
                                    .mapToObj(j -> new SBItem(list.getCompound(i * 45 + j)))
                                    .toArray(SBItem[]::new)))
                    .toArray(SBContainer[]::new);
        });
    }
    public SBContainer getPersonalVault()
    {
        return (SBContainer) internalApiMap.computeIfAbsent("personal_vault", k -> new SBContainer(parseBase64(get(null, "personal_vault_contents.data"))
                .getList("i")
                .stream()
                .map(nbt -> new SBItem((NBTCompound) nbt))
                .toArray(SBItem[]::new)));
    }
    public SBContainer getTalismanBag()
    {
        return (SBContainer) internalApiMap.computeIfAbsent("talisman_bag", k -> new SBContainer(parseBase64(get(null, "bag_contents.talisman_bag.data"))
                .getList("i")
                .stream()
                .map(nbt -> new SBItem((NBTCompound) nbt))
                .toArray(SBItem[]::new)));
    }
    public SBContainer getPotionBag()
    {
        return (SBContainer) internalApiMap.computeIfAbsent("potion_bag", k -> new SBContainer(parseBase64(get(null, "bag_contents.potion_bag.data"))
                .getList("i")
                .stream()
                .map(nbt -> new SBItem((NBTCompound) nbt))
                .toArray(SBItem[]::new)));
    }
    public SBContainer getFishingBag()
    {
        return (SBContainer) internalApiMap.computeIfAbsent("fishing_bag", k -> new SBContainer(parseBase64(get(null, "bag_contents.fishing_bag.data"))
                .getList("i")
                .stream()
                .map(nbt -> new SBItem((NBTCompound) nbt))
                .toArray(SBItem[]::new)));
    }
    public SBContainer getQuiver()
    {
        return (SBContainer) internalApiMap.computeIfAbsent("quiver", k -> new SBContainer(parseBase64(get(null, "bag_contents.quiver.data"))
                .getList("i")
                .stream()
                .map(nbt -> new SBItem((NBTCompound) nbt))
                .toArray(SBItem[]::new)));
    }
}
