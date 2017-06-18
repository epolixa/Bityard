/**
 *  Created by @epolixa
 *  as part of the Bityard mod for the Bityard server
 */

package com.epolixa.bityard;

import com.epolixa.bityard.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Bityard.modid, name = Bityard.name, version = Bityard.version, acceptedMinecraftVersions = "[1.11.2]")
public class Bityard
{

    @SidedProxy(serverSide = "com.epolixa.bityard.proxy.ServerProxy", clientSide = "com.epolixa.bityard.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static final String modid = "bityard";
    public static final String name = "Bityard";
    public static final String version = "2017.06.15_1.11.2";
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
