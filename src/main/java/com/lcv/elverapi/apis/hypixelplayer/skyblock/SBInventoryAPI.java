package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import com.lcv.elverapi.apis.SubApi;
import me.nullicorn.nedit.NBTReader;
import me.nullicorn.nedit.type.NBTCompound;
import org.json.JSONObject;

import java.io.IOException;

public class SBInventoryAPI extends SubApi
{

    public SBInventoryAPI(SkyblockAPI parent)
    {
        super(parent, "members." + parent.getUuidNoHyphen() + ".inventory");
        System.out.println(this.exists());
    }

    private static String parseBase64(String s)
    {
        try
        {
            System.out.println(s);
            return NBTReader.readBase64(s).toString();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public String getInventory()
    {
        return (String) internalApiMap.computeIfAbsent("inventory", (k) -> parseBase64(get(null, "inv_contents.data")));
    }
}
