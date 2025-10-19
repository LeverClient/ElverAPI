package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import com.lcv.elverapi.apis.SubApi;
import me.nullicorn.nedit.NBTReader;
import me.nullicorn.nedit.type.NBTCompound;
import me.nullicorn.nedit.type.NBTList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

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
            System.out.println(s);
            return NBTReader.readBase64(s);
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public NBTCompound getInventoryNBT()
    {
        return (NBTCompound) internalApiMap.computeIfAbsent("inventory", k -> parseBase64(get(null, "inv_contents.data")));
    }
    public NBTCompound getEnderChestNBT()
    {
        return (NBTCompound) internalApiMap.computeIfAbsent("ender_chest", k -> parseBase64(get(null, "ender_chest_contents.data")));
    }
    public NBTCompound[] getStorageNBT()
    {
        JSONObject json = get(new JSONObject(), "backpack_contents");
        return (NBTCompound[]) internalApiMap.computeIfAbsent("storage", k -> IntStream.range(0, json.length()).mapToObj(i -> parseBase64(get(null, "backpack_contents." + i + ".data"))).toArray(NBTCompound[]::new));
    }
    public NBTCompound getArmorNBT()
    {
        return (NBTCompound) internalApiMap.computeIfAbsent("armor", k -> parseBase64(get(null, "inv_armor.data")));
    }
    public NBTCompound getEquipmentNBT()
    {
        return (NBTCompound) internalApiMap.computeIfAbsent("equipment", k -> parseBase64(get(null, "equipment_contents.data")));
    }
    public NBTCompound getWardrobeNBT()
    {
        return (NBTCompound) internalApiMap.computeIfAbsent("wardrobe", k -> parseBase64(get(null, "wardrobe_contents.data")));
    }
    public NBTCompound getPersonalVaultNBT()
    {
        return (NBTCompound) internalApiMap.computeIfAbsent("personal_vault", k -> parseBase64(get(null, "personal_vault_contents.data")));
    }
    public NBTCompound getTalismanBagNBT()
    {
        return (NBTCompound) internalApiMap.computeIfAbsent("talisman_bag", k -> parseBase64(get(null, "bag_contents.talisman_bag.data")));
    }

    public SBItem[] getHotbar()
    {
        NBTList list = getInventoryNBT().getList("i");
        return IntStream.range(0, 9).mapToObj(i -> new SBItem(list.getCompound(i))).toArray(SBItem[]::new);
    }
    public SBItem[] getInventory()
    {
        NBTList list = getInventoryNBT().getList("i");
        return IntStream.range(9, 36).mapToObj(i -> new SBItem(list.getCompound(i))).toArray(SBItem[]::new);
    }
    public SBItem[][] getEnderChest()
    {
        NBTList list = getEnderChestNBT().getList("i");
        return IntStream.range(0, list.toArray().length / 45).mapToObj(i -> IntStream.range(0, 45).mapToObj(j -> new SBItem(list.getCompound(i * 45 + j))).toArray(SBItem[]::new)).toArray(SBItem[][]::new);
    }
    public SBItem[][] getStorage()
    {
        NBTCompound[] arr = getStorageNBT();
        return Arrays.stream(arr).map(nbt -> nbt.getList("i").stream().map(o -> new SBItem((NBTCompound) o)).toArray(SBItem[]::new)).toArray(SBItem[][]::new);
    }
    public SBItem[] getArmor()
    {
        NBTList list = getArmorNBT().getList("i");
        return IntStream.range(0, list.size()).mapToObj(i -> new SBItem(list.getCompound(3 - i))).toArray(SBItem[]::new);
    }
    public SBItem[] getEquipment()
    {
        NBTList list = getEquipmentNBT().getList("i");
        return IntStream.range(0, list.size()).mapToObj(i -> new SBItem(list.getCompound(3 - i))).toArray(SBItem[]::new);
    }
    public SBItem[][] getWardrobe()
    {
        NBTList list = getWardrobeNBT().getList("i");
        return IntStream.range(0, 18)
                .mapToObj(i -> IntStream.range(0, 4)
                        .mapToObj(j -> new SBItem(list.getCompound((i / 9) * 36 + (i % 9) + (j * 9))))
                        .toArray(SBItem[]::new))
                .toArray(SBItem[][]::new);
    }
    public SBItem[] getPersonalVault()
    {
        NBTList list = getPersonalVaultNBT().getList("i");
        return list.stream()
                .map(nbt -> new SBItem((NBTCompound) nbt))
                .toArray(SBItem[]::new);
    }
    public SBItem[] getTalismanBag()
    {
        NBTList list = getTalismanBagNBT().getList("i");
        return list.stream()
                .map(nbt -> new SBItem((NBTCompound) nbt))
                .toArray(SBItem[]::new);
    }
}
