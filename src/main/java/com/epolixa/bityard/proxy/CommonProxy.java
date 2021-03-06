package com.epolixa.bityard.proxy;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.block.BityardBlocks;
import com.epolixa.bityard.entity.EntityBityardFishHook;
import com.epolixa.bityard.entity.EntityBugSwarm;
import com.epolixa.bityard.event.BityardEventHandlers;
import com.epolixa.bityard.event.PlayerSitHandler;
import com.epolixa.bityard.gameplay.BityardLootTables;
import com.epolixa.bityard.item.BityardItems;
import com.epolixa.bityard.network.BityardMessages;
import com.epolixa.bityard.recipe.BityardRecipes;
import com.epolixa.bityard.world.BityardWorldGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void registerItemRenderer(Item item, int meta, String id) {}

    public void preInit(FMLPreInitializationEvent event)
    {
        GameRegistry.registerWorldGenerator(new BityardWorldGenerator(), 10);

        BityardBlocks.init();
        BityardItems.init();

        BityardLootTables.init();

        int entityId = 0;
        EntityRegistry.registerModEntity(new ResourceLocation("minecraft:textures/particle/particles.png"), EntityBityardFishHook.class, "fish_hook", entityId++, Bityard.instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation("minecraft:textures/particle/particles.png"), PlayerSitHandler.Seat.class, "seat", entityId++, Bityard.instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation("minecraft:textures/particle/particles.png"), EntityBugSwarm.class, "bug_swarm", entityId++, Bityard.instance, 64, 10, true);
        //EntityRegistry.addSpawn(EntityBugSwarm.class, 6, 1, 5, EnumCreatureType.AMBIENT, Biomes.FOREST);

        // Events
        BityardEventHandlers.init();

        // Crafting/Smelting/Brewing recipes
        BityardRecipes.init();

        // Messages
        BityardMessages.init();
    }

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {}
}
