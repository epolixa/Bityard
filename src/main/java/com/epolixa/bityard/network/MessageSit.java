package com.epolixa.bityard.network;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.event.PlayerSitHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSit extends NetworkMessage
{
    public MessageSit() {}

    @Override
    public IMessage handleMessage(MessageContext context)
    {
        PlayerSitHandler.playerSit(context.getServerHandler().playerEntity);
        return null;
    }
}
