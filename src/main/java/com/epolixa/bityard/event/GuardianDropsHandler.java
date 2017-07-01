package com.epolixa.bityard.event;

import com.epolixa.bityard.item.BityardItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuardianDropsHandler
{
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(LivingDropsEvent event)
    {
        if (event.getEntity() instanceof EntityGuardian)
        {
            int numFish = 0;
            for (int i = 0; i < event.getDrops().size(); i++)
            {
                EntityItem entityItem = event.getDrops().get(i);
                if (entityItem.getEntityItem().getItem() == Items.FISH)
                {
                    event.getDrops().remove(i--);
                    numFish++;
                }
            }
            event.getDrops().add(
                    new EntityItem(
                            event.getEntity().world,
                            event.getEntity().posX,
                            event.getEntity().posY,
                            event.getEntity().posZ,
                            new ItemStack(BityardItems.RAW_FISH, numFish)
                    )
            );
        }
    }
}
