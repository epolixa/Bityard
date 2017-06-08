package com.epolixa.bityard.network;

import com.epolixa.bityard.Bityard;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Bityard.modid);

    private static int i = 0;

    public static void register(Class clazz, Side handlerSide) {
        INSTANCE.registerMessage(clazz, clazz, i++, handlerSide);
    }

}