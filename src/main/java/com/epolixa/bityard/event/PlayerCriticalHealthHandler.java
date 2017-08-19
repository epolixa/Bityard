package com.epolixa.bityard.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class PlayerCriticalHealthHandler
{
    Random random = new Random();

    @SubscribeEvent
    public void onPlayerCriticalHealth(TickEvent.PlayerTickEvent event)
    {
        EntityPlayer player = event.player;

        if (player.getHealth() <= 6 && player.getHealth() > 0 && !player.world.isRemote)
        {
            WorldServer worldServer = (WorldServer) player.world;
            if (random.nextInt(Math.max(3, (int)Math.ceil(player.getHealth() * 2.4))) == 0)
            {
                worldServer.spawnParticle(
                        EnumParticleTypes.BLOCK_CRACK,
                        player.posX + ((random.nextInt(8) - 4)*0.1),
                        player.posY + 1 + ((random.nextInt(12) - 6)*0.1),
                        player.posZ + ((random.nextInt(8) - 4)*0.1),
                        1,
                        0,
                        0,
                        0,
                        0,
                        new int[] {214}
                );
            }
        }
    }
}
