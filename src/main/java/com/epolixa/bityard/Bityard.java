/**
 *  Created by @epolixa
 *  as part of the Bityard mod for the Bityard server
 */

package com.epolixa.bityard;

import com.epolixa.bityard.block.BityardBlocks;
import com.epolixa.bityard.client.renderer.BityardFishHookRenderFactory;
import com.epolixa.bityard.client.renderer.RenderGuardianEyeBeam;
import com.epolixa.bityard.entity.EntityBityardFishHook;
import com.epolixa.bityard.event.BityardEventHandlers;
import com.epolixa.bityard.gameplay.BityardKeyBinds;
import com.epolixa.bityard.gameplay.BityardLootTables;
import com.epolixa.bityard.item.BityardItems;
import com.epolixa.bityard.item.ItemRing;
import com.epolixa.bityard.item.RingColor;
import com.epolixa.bityard.proxy.CommonProxy;
import com.epolixa.bityard.recipe.BityardRecipes;
import com.epolixa.bityard.world.BityardWorldGenerator;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Bityard.modid, name = Bityard.name, version = Bityard.version, acceptedMinecraftVersions = "[1.11.2]")
public class Bityard
{

    @SidedProxy(serverSide = "com.epolixa.bityard.proxy.ServerProxy", clientSide = "com.epolixa.bityard.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static final String modid = "bityard";
    public static final String name = "Bityard";
    public static final String version = "1.0.0_2017.05.31";
    public static final String mcversion = "1.11.2";
    private static boolean debug = true;

    @Mod.Instance(modid)
    public static Bityard instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println(name + " is pre-initializing...");
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        System.out.println(name + " is initializing...");
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        System.out.println(name + " is post-initializing...");
        proxy.postInit(event);
    }

    public static void log(String className, String msg)
    {
        if (debug)
        {
            System.out.println("[" + name + "] [" + className + "] : " + msg);
        }
    }

}
