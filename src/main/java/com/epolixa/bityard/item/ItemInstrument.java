package com.epolixa.bityard.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemInstrument extends ItemBase implements ItemModelProvider
{
    protected SoundEvent soundEvent;

    public ItemInstrument (String name)
    {
        super(name);
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxStackSize(1);
        switch(name)
        {
            case "bass_guitar":
                soundEvent = SoundEvents.BLOCK_NOTE_BASS; break;
            case "snare_drum":
                soundEvent = SoundEvents.BLOCK_NOTE_SNARE; break;
            case "click_sticks":
                soundEvent = SoundEvents.BLOCK_NOTE_HAT; break;
            case "bass_drum":
                soundEvent = SoundEvents.BLOCK_NOTE_BASEDRUM; break;
            default:
                soundEvent = SoundEvents.BLOCK_NOTE_HARP;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        worldIn.playSound(
            playerIn,
            playerIn.getPosition(),
            soundEvent,
            SoundCategory.PLAYERS,
            1,
            1.25f - (playerIn.rotationPitch / 120)
        );
        if (!worldIn.isRemote)
        {
            WorldServer worldServer = (WorldServer) worldIn;
            worldServer.spawnParticle(
                    EnumParticleTypes.NOTE,
                    playerIn.posX + ((itemRand.nextInt(8) - 4)*0.1),
                    playerIn.posY + 2.2,
                    playerIn.posZ + ((itemRand.nextInt(8) - 4)*0.1),
                    1,
                    0,
                    0,
                    0,
                    0,
                    new int[] {0}
            );
        }

        playerIn.swingArm(handIn);

        return new ActionResult(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

}
