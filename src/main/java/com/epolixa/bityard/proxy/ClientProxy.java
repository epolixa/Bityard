package com.epolixa.bityard.proxy;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.client.renderer.BityardFishHookRenderFactory;
import com.epolixa.bityard.entity.EntityBityardFishHook;
import com.epolixa.bityard.gameplay.BityardKeyBinds;
import com.epolixa.bityard.item.BityardItems;
import com.epolixa.bityard.item.RingColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Bityard.modid + ":" + id, "inventory"));
    }

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

        RenderingRegistry.registerEntityRenderingHandler(EntityBityardFishHook.class, new BityardFishHookRenderFactory());
        //RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderGuardianEyeBeam(Minecraft.getMinecraft().getRenderManager()));

        // Keybindings
        BityardKeyBinds.init();
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
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new RingColor(), new Item[] {BityardItems.RING});
    }

}
