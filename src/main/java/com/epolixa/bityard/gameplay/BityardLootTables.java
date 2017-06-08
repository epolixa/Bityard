package com.epolixa.bityard.gameplay;

import com.epolixa.bityard.Bityard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class BityardLootTables
{
    public static ResourceLocation BUGS_FIELD;

    public static ResourceLocation FISHING_OCEAN;
    public static ResourceLocation FISHING_DEEP_OCEAN_NIGHT;
    public static ResourceLocation FISHING_JUNGLE;
    public static ResourceLocation FISHING_SWAMP;
    public static ResourceLocation FISHING_ARID;
    public static ResourceLocation FISHING_COLD;
    public static ResourceLocation FISHING_RIVER;
    public static ResourceLocation FISHING_FRESHWATER;
    public static ResourceLocation FISHING_FRESHWATER_THUNDER;

    public static void init()
    {
        BUGS_FIELD = register("gameplay/bugs/field");

        FISHING_OCEAN = register("gameplay/fishing/ocean");
        FISHING_DEEP_OCEAN_NIGHT = register("gameplay/fishing/deep_ocean_night");
        FISHING_JUNGLE = register("gameplay/fishing/jungle");
        FISHING_SWAMP = register("gameplay/fishing/swamp");
        FISHING_ARID = register("gameplay/fishing/arid");
        FISHING_COLD = register("gameplay/fishing/cold");
        FISHING_RIVER = register("gameplay/fishing/river");
        FISHING_FRESHWATER = register("gameplay/fishing/freshwater");
        FISHING_FRESHWATER_THUNDER = register("gameplay/fishing/freshwater_thunder");
    }

    private static ResourceLocation register(String id)
    {
        return LootTableList.register(new ResourceLocation(Bityard.modid, id));
    }
}
