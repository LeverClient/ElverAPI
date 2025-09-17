package com.lcv.elverapi.apis.mojang;

import com.lcv.elverapi.apis.Api;

import java.util.UUID;

public class MojangProfileLookupAPI extends Api {

    private final String playerName;

    private final UUID playerUUID;

    public MojangProfileLookupAPI(String playerName) {
        this.playerName = playerName;
        this.playerUUID = null;

        this.jsonObject = setupJson();
    }

    public MojangProfileLookupAPI(UUID uuid) {
        this.playerName = null;
        this.playerUUID = uuid;

        this.jsonObject = setupJson();
    }

    @Override
    public String getApiEndpoint() {
        if (playerName != null) return "https://api.minecraftservices.com/minecraft/profile/lookup/name/" + playerName;
            else if (playerUUID != null) return "https://api.minecraftservices.com/minecraft/profile/lookup/" + playerUUID;

        throw new RuntimeException("Invalid MojangProfileLookup API");
    }

    public String getName() {
        return (String) internalApiMap.computeIfAbsent("name", (k) -> get("name"));
    }

    public String getUUID() {
        return (String) internalApiMap.computeIfAbsent("uuid", (k) -> get("id"));
    }
}
