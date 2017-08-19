package com.epolixa.bityard.event;

import net.minecraftforge.common.MinecraftForge;

public class BityardEventHandlers
{
    public static void init()
    {
        MinecraftForge.EVENT_BUS.register(new LootTableHandler());
        MinecraftForge.EVENT_BUS.register(new BatDropsHandler());
        MinecraftForge.EVENT_BUS.register(new BirchLeavesHarvestDropsHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerSitHandler());
        MinecraftForge.EVENT_BUS.register(new ElderGuardianDropsHandler());
        MinecraftForge.EVENT_BUS.register(new GuardianDropsHandler());
        MinecraftForge.EVENT_BUS.register(new PolarBearDropsHandler());
        MinecraftForge.EVENT_BUS.register(new WitherSkeletonDropsHandler());
        MinecraftForge.EVENT_BUS.register(new ZombieDropsHandler());
        MinecraftForge.EVENT_BUS.register(new CropExpHandler());
        MinecraftForge.EVENT_BUS.register(new NameColorHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerCriticalHealthHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerTossItemHandler());
    }
}
