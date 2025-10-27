package com.lcv.elverapi.apis.hypixelplayer.skyblock;

import com.lcv.elverapi.apis.SubApi;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.stream.IntStream;

public class SBPetAPI extends SubApi
{
    public SBPetAPI(SkyblockAPI parent)
    {
        super(parent, "members." + parent.getUuid());
    }

    public SBPet getActivePet()
    {
        return (SBPet) internalApiMap.computeIfAbsent("active_pet", k ->
        {
            for (Object o : get(new JSONArray(), "pets_data.pets"))
                if (((JSONObject) o).optBoolean("active"))
                    return new SBPet((JSONObject) o);
            return null;
        });
    }
    public SBPet[] getPets()
    {
        return (SBPet[]) internalApiMap.computeIfAbsent("pets", k -> IntStream.range(0, get(new JSONArray(), "pets_data.pets").length())
                .mapToObj(i -> new SBPet(get(new JSONArray(), "pets_data.pets").getJSONObject(i)))
                .toArray(SBPet[]::new));
    }
    public int getPetScore()
    {
        return (int) internalApiMap.computeIfAbsent("pet_score", k -> get(-1, "leveling.highest_pet_score"));
    }
}
