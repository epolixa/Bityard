package com.epolixa.bityard.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class PlayerTossItemHandler
{
    @SubscribeEvent
    public void onPlayerTossItem(ItemTossEvent event)
    {
        plop(event.getPlayer());
    }

    @SubscribeEvent
    public void onPlayerTossItemHotbar(InputEvent.KeyInputEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.gameSettings.keyBindDrop.isKeyDown() && !mc.player.getHeldItem(EnumHand.MAIN_HAND).isEmpty())
        {
            plop(mc.player);
        }
    }

    private static void plop(EntityPlayer player)
    {
        World world = player.world;

        world.playSound(
                player,
                player.getPosition(),
                SoundEvents.ENTITY_ITEM_PICKUP,
                SoundCategory.PLAYERS,
                0.2F,
                ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 0.75F
        );
    }
}
