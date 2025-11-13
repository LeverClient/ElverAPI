package com.lcv.elverapi.apis.hypixelplayer.skyblock.api;

import com.lcv.elverapi.apis.SubApi;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.SkyblockAPI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class SBMinionsAPI extends SubApi {
    public SBMinionsAPI(SkyblockAPI parent) {
        super(parent, "members");
    }

    public void test()
    {
        List<String> minions = get(new JSONObject(), "")
                .toMap()
                .keySet()
                .stream()
                .flatMap(s -> get(new JSONArray(), s + ".player_data.crafted_generators")
                        .toList()
                        .stream()
                        .map(Object::toString))
                .sorted()
                .toList();

        for (int i = 0; i < minions.size(); i++)
        {
            String type = minions.get(i).substring(0, minions.get(i).lastIndexOf("_"));
            boolean[] unlocked = new boolean[MAXIMUM_LEVEL.get(type.toString())];
            for (int j = i; j < minions.size(); j++)
            {
                if (!minions.get(j).substring(0, minions.get(j).lastIndexOf("_")).equals(type))
                {
                    i = j;
                    break;
                }
                if (!minions.get(j).split("_")[0].equals(type.toString()))
                {
                    i = j;
                    break;
                }
                unlocked[Integer.parseInt(minions.get(j).substring(minions.get(j).lastIndexOf("_") + 1)) - 1] = true;
            }
            System.out.println(type + " " + Arrays.toString(unlocked));
        }
    }

    public static final Map<String, Integer> MAXIMUM_LEVEL = Map.ofEntries(
            Map.entry("ACACIA", 11)
    );
}
