package com.epolixa.bityard.event;

import net.minecraftforge.common.MinecraftForge;
import scala.collection.parallel.ParIterableLike;

public class BityardEventHandlers
{
    public static void init()
    {
        MinecraftForge.EVENT_BUS.register(new BatDropsHandler());
        MinecraftForge.EVENT_BUS.register(new BirchLeavesHarvestDropsHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerSitHandler());
        //MinecraftForge.EVENT_BUS.register(new ElderGuardianDropsHandler());
        MinecraftForge.EVENT_BUS.register(new WitherSkeletonDropsHandler());
        MinecraftForge.EVENT_BUS.register(new CropExpHandler());
    }
}
