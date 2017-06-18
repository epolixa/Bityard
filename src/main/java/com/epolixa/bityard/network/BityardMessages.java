package com.epolixa.bityard.network;

import net.minecraftforge.fml.relauncher.Side;

public class BityardMessages
{
    public static void init() {
        NetworkHandler.register(MessageSit.class, Side.SERVER);
    }

}
