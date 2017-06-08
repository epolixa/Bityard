package com.epolixa.bityard.proxy;

import com.epolixa.bityard.block.BityardBlocks;
import com.epolixa.bityard.entity.EntityBityardFishHook;
import com.epolixa.bityard.event.BityardEventHandlers;
import com.epolixa.bityard.gameplay.BityardLootTables;
import com.epolixa.bityard.item.BityardItems;
import com.epolixa.bityard.recipe.BityardRecipes;
import com.epolixa.bityard.world.BityardWorldGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ServerProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
}
