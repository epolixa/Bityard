package com.epolixa.bityard.event;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.item.BityardItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ZombieDropsHandler
{
    @SubscribeEvent
    public void onEvent(LivingDropsEvent event)
    {
        if (event.getEntity() instanceof EntityZombie)
        {
            if (event.getEntity().isBurning())
            {
                int numFlesh = 0;
                for (int i = 0; i < event.getDrops().size(); i++)
                {
                    EntityItem entityItem = event.getDrops().get(i);
                    if (entityItem.getEntityItem().getItem() == Items.ROTTEN_FLESH)
                    {
                        event.getDrops().remove(i--);
                        numFlesh++;
                    }
                }
                event.getDrops().add(
                        new EntityItem(
                                event.getEntity().world,
                                event.getEntity().posX,
                                event.getEntity().posY,
                                event.getEntity().posZ,
                                new ItemStack(
                                        BityardItems.ZOMBIE_JERKY,
                                        numFlesh
                                )
                        )
                );
            }
        }
    }
}
