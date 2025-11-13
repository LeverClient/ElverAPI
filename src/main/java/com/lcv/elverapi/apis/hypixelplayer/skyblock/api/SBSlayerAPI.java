package com.lcv.elverapi.apis.hypixelplayer.skyblock.api;

import com.lcv.elverapi.apis.SubApi;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.SkyblockAPI;
import com.lcv.elverapi.apis.hypixelplayer.skyblock.util.SBSlayer;

import java.util.Arrays;

public class SBSlayerAPI extends SubApi
{
    public SBSlayerAPI(SkyblockAPI parent)
    {
        super(parent, "members." + parent.getUuid() + ".slayer.slayer_bosses");
    }

    public int getSlayerXp()
    {
        return (int) internalApiMap.computeIfAbsent("slayer_xp", k -> Arrays
                .stream(new String[]{"zombie", "wolf", "spider", "enderman", "blaze", "vampire"})
                .mapToInt(s -> get(0, s + ".xp"))
                .sum());
    }

    public SBSlayer[] getSlayers()
    {
        return (SBSlayer[]) internalApiMap.computeIfAbsent("slayers", k -> Arrays
                .stream(new String[]{"zombie", "wolf", "spider", "enderman", "blaze", "vampire"})
                .map(s -> {
                    String prefix = s + ".boss_kills_tier_";
                    String name = switch(s)
                    {
                        case "zombie" -> "Revenant Horror";
                        case "wolf" -> "Sven Packmaster";
                        case "spider" -> "Tarantula Broodfather";
                        case "enderman" -> "Voidgloom Seraph";
                        case "blaze" -> "Inferno Demonlord";
                        case "vampire" -> "Riftstalker Bloodfiend";
                        default -> s;
                    };
                    int xp = get(-1, s + ".xp");
                    int tier1 = get(0, prefix + 0);
                    int tier2 = get(0, prefix + 1);
                    int tier3 = get(0, prefix + 2);
                    int tier4 = get(0, prefix + 3);
                    int tier5 = get(0, prefix + 4);
                    return new SBSlayer(name, xp, tier1, tier2, tier3, tier4, tier5);
                })
                .toArray(SBSlayer[]::new));
    }
    public SBSlayer getRevenant()
    {
        SBSlayer[] slayers = getSlayers();
        return (SBSlayer) internalApiMap.computeIfAbsent("revenant", k -> slayers[0]);
    }
    public SBSlayer getSven()
    {
        SBSlayer[] slayers = getSlayers();
        return (SBSlayer) internalApiMap.computeIfAbsent("sven", k -> slayers[1]);
    }
    public SBSlayer getTarantula()
    {
        SBSlayer[] slayers = getSlayers();
        return (SBSlayer) internalApiMap.computeIfAbsent("tarantula", k -> slayers[2]);
    }
    public SBSlayer getVoidgloom()
    {
        SBSlayer[] slayers = getSlayers();
        return (SBSlayer) internalApiMap.computeIfAbsent("voidgloom", k -> slayers[3]);
    }
    public SBSlayer getInferno()
    {
        SBSlayer[] slayers = getSlayers();
        return (SBSlayer) internalApiMap.computeIfAbsent("inferno", k -> slayers[4]);
    }
    public SBSlayer getRiftstalker()
    {
        SBSlayer[] slayers = getSlayers();
        return (SBSlayer) internalApiMap.computeIfAbsent("riftstalker", k -> slayers[5]);
    }
}
