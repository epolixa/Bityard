package com.epolixa.bityard.gameplay;

import com.epolixa.bityard.Bityard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class BityardLootTables
{
    // VANILLA INJECT
    public static ResourceLocation ABANDONED_MINESHAFT;
    public static ResourceLocation DESERT_PYRAMID;
    public static ResourceLocation END_CITY_TREASURE;
    public static ResourceLocation IGLOO_CHEST;
    public static ResourceLocation JUNGLE_TEMPLE;
    public static ResourceLocation JUNGLE_TEMPLE_DISPENSER;
    public static ResourceLocation NETHER_BRIDGE;
    public static ResourceLocation SIMPLE_DUNGEON;
    public static ResourceLocation STRONGHOLD_CORRIDOR;
    public static ResourceLocation STRONGHOLD_CROSSING;
    public static ResourceLocation STRONGHOLD_LIBRARY;
    public static ResourceLocation VILLAGE_BLACKSMITH;
    public static ResourceLocation WOODLAND_MANSION;

    // HELPER
    public static ResourceLocation ATTRIBUTED_DIAMOND_GEAR;
    public static ResourceLocation ATTRIBUTED_GOLD_GEAR;
    public static ResourceLocation ATTRIBUTED_IRON_GEAR;
    public static ResourceLocation ATTRIBUTED_LEATHER_GEAR;
    public static ResourceLocation ATTRIBUTED_STONE_GEAR;
    public static ResourceLocation MOB_EGGS_END;
    public static ResourceLocation MOB_EGGS_NETHER;
    public static ResourceLocation MOB_EGGS_OVERWORLD;
    public static ResourceLocation RANDOM_RECORDS;
    public static ResourceLocation SUPER_POTIONS;

    // BUGS
    public static ResourceLocation BUGS_FIELD;

    // FISHING
    public static ResourceLocation FISHING_OCEAN;
    public static ResourceLocation FISHING_DEEP_OCEAN;
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
        ABANDONED_MINESHAFT = register("inject/chests/abandoned_mineshaft");
        DESERT_PYRAMID = register("inject/chests/desert_pyramid");
        END_CITY_TREASURE = register("inject/chests/end_city_treasure");
        IGLOO_CHEST = register("inject/chests/igloo_chest");
        JUNGLE_TEMPLE = register("inject/chests/jungle_temple");
        JUNGLE_TEMPLE_DISPENSER = register("inject/chests/jungle_temple_dispenser");
        NETHER_BRIDGE = register("inject/chests/nether_bridge");
        SIMPLE_DUNGEON = register("inject/chests/simple_dungeon");
        STRONGHOLD_CORRIDOR = register("inject/chests/stronghold_corridor");
        STRONGHOLD_CROSSING = register("inject/chests/stronghold_crossing");
        STRONGHOLD_LIBRARY = register("inject/chests/stronghold_library");
        VILLAGE_BLACKSMITH = register("inject/chests/village_blacksmith");
        WOODLAND_MANSION = register("inject/chests/woodland_mansion");

        MOB_EGGS_END = register("helper/mob_eggs_end");
        MOB_EGGS_NETHER = register("helper/mob_eggs_nether");
        MOB_EGGS_OVERWORLD = register("helper/mob_eggs_overworld");
        RANDOM_RECORDS = register("helper/random_records");
        SUPER_POTIONS = register("helper/super_potions");

        BUGS_FIELD = register("gameplay/bugs/field");

        FISHING_OCEAN = register("gameplay/fishing/ocean");
        FISHING_DEEP_OCEAN = register("gameplay/fishing/deep_ocean");
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
