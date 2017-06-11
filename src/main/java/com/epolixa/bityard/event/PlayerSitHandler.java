package com.epolixa.bityard.event;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.gameplay.BityardKeyBinds;
import com.epolixa.bityard.network.MessageSit;
import com.epolixa.bityard.network.NetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class PlayerSitHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerPressSit(InputEvent.KeyInputEvent event)
    {
        if (BityardKeyBinds.SIT.isPressed())
        {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();

            if (FMLClientHandler.instance().getClient().inGameHasFocus && player != null) {
                NetworkHandler.INSTANCE.sendToServer(new MessageSit());
            }
        }
    }

    public static boolean canSitOn(Block block)
    {
        return block != Blocks.AIR ||
               block != Blocks.WATER || block != Blocks.FLOWING_WATER ||
               block != Blocks.LAVA || block != Blocks.FLOWING_LAVA;
    }

    public static void playerSit(EntityPlayer player)
    {
        if (player != null)
        {
            if (player.isRiding())
            {
                if (player.getRidingEntity() instanceof Seat)
                {
                    player.dismountRidingEntity();
                }
            }
            else if (canSitOn(player.world.getBlockState(player.getPosition().add(0,-1,0)).getBlock()))
            {
                Seat seat = new Seat(player);
                player.world.spawnEntity(seat);
            }
        }
    }

    public static class Seat extends Entity {

        public Seat(EntityPlayer player)
        {
            this(player.world);

            this.setPosition(player.posX, player.posY, player.posZ);

            player.startRiding(this);
        }

        public Seat(World par1World)
        {
            super(par1World);

            this.setSize(0F, 0F);
        }

        @Override
        public void onUpdate()
        {
            super.onUpdate();

            List<Entity> passengers = getPassengers();
            if(passengers.isEmpty())
            {
                setDead();
            }
        }

        @Override
        public void updatePassenger(Entity passenger)
        {
            if (this.isPassenger(passenger))
            {
                EntityPlayer player = (EntityPlayer) passenger;
                player.setPosition(this.posX, this.posY - 0.6, this.posZ);
                if (player.isSprinting())
                {
                    player.setSprinting(false);
                }
            }
        }

        @Override protected void entityInit() { }
        @Override public void readEntityFromNBT(NBTTagCompound nbttagcompound) { }
        @Override public void writeEntityToNBT(NBTTagCompound nbttagcompound) { }
    }
}
