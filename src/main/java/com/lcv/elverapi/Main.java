package com.lcv.elverapi;


import com.lcv.elverapi.apis.hypixelplayer.BedwarsAPI;
import com.lcv.elverapi.apis.hypixelplayer.HypixelPlayerAPI;
import com.lcv.elverapi.apis.mojang.MojangProfileLookupAPI;

public class Main {

    final public static String UUID_SYL = "ddf13e436ccc4790bb49912913bf7d77";

    final public static String UUID_LCV = "f7a757d51d6d408eb54ea7230b95ae15";

    final public static String UUID_HYPIXEL = "f7c77d999f154a66a87dc4a51ef30d19";

    final public static String UUID_DS = "c57dd50dab654726a5e3cbae94cc7b53";

    final public static String UUID_WAROG = "3b76b69ae5134296a730ed49171ad6f8";

    final public static String UUID_BOO = "3e80dcea2cc7458a8ff06343aebceaca";

    public static void main(String[] args) {
        String key = "aab88717-7038-498a-a008-58a2bb92b98d";

        MojangProfileLookupAPI mApi = new MojangProfileLookupAPI("syl127");

        HypixelPlayerAPI hypixelApi = new HypixelPlayerAPI(mApi.getId(), key);
        BedwarsAPI bwApi = hypixelApi.getBedwarsApi();

        System.out.println(hypixelApi.getExperience());
        System.out.println(hypixelApi.getLevel());
        System.out.println(hypixelApi.getRank());
        System.out.println(hypixelApi.getChatFormattedRank());

        System.out.println(bwApi.getExperience());
        System.out.println(bwApi.getLevel());
        System.out.println(bwApi.getChatFormattedLevel());

        System.out.println("meow");
    }
}