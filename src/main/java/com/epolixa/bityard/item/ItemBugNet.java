package com.epolixa.bityard.item;

import com.epolixa.bityard.gameplay.BityardLootTables;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class ItemBugNet extends ItemBase implements ItemModelProvider
{
    public ItemBugNet()
    {
        super("bug_net");
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        worldIn.playSound(
            playerIn,
            playerIn.getPosition(),
            SoundEvents.ENTITY_BOBBER_THROW,
            SoundCategory.NEUTRAL,
            0.5F,
            0.4F / (itemRand.nextFloat() * 0.4F + 0.8F)
        );

        playerIn.swingArm(handIn);

        return new ActionResult(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        worldIn.playSound(
                player,
                player.getPosition(),
                SoundEvents.ENTITY_BOBBER_THROW,
                SoundCategory.NEUTRAL,
                0.5F,
                0.4F / (itemRand.nextFloat() * 0.4F + 0.8F)
        );

        player.swingArm(hand);

        IBlockState state = worldIn.getBlockState(pos);

        if (state.getBlock() == Blocks.TALLGRASS)
        {
            if (!worldIn.isRemote)
            {
                LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)worldIn);
                lootcontext$builder.withLuck(player.getLuck());

                Random random = new Random();

                ResourceLocation lootTableLocation;
                Biome biome = worldIn.getBiome(pos);

                lootTableLocation = BityardLootTables.BUGS_FIELD;

                for (ItemStack stack : worldIn.getLootTableManager().getLootTableFromLocation(lootTableLocation).generateLootForPools(itemRand, lootcontext$builder.build()))
                {
                    if (stack.getItem() == BityardItems.FISH)
                    {
                        NBTTagCompound tagcompound = new NBTTagCompound();
                        tagcompound.setString("size", String.valueOf(ItemFish.FishType.byMetadata(stack.getMetadata()).getRandomizedSize()));
                        stack.setTagCompound(tagcompound);
                    }
                    EntityItem entityitem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                    double d0 = player.posX - pos.getX();
                    double d1 = player.posY - pos.getY();
                    double d2 = player.posZ - pos.getZ();
                    double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    double d4 = 0.1D;
                    entityitem.motionX = d0 * 0.1D;
                    entityitem.motionY = d1 * 0.1D + (double)MathHelper.sqrt(d3) * 0.08D;
                    entityitem.motionZ = d2 * 0.1D;
                    worldIn.spawnEntity(entityitem);
                    worldIn.spawnEntity(new EntityXPOrb(worldIn, player.posX, player.posY + 0.5D, player.posZ + 0.5D, itemRand.nextInt(6) + 1));
                    //player.addStat(StatList.BUGS_CAUGHT, 1);
                }
            }

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.PASS;
        }
    }

}