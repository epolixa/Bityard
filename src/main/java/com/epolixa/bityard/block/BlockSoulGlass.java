package com.epolixa.bityard.block;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.item.ItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockSoulGlass extends BlockBase implements ItemModelProvider
{
    Random random = new Random();
    int particleDelay = 0;

    public BlockSoulGlass()
    {
        super(Material.GLASS, MapColor.BROWN, "soul_glass");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(0.5F);
        setSoundType(SoundType.GLASS);
        setLightOpacity(3);
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(item, 0, "soul_glass");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    @Deprecated
    protected boolean canSilkHarvest()
    {
        return true;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    @Deprecated
    public boolean isBlockNormalCube(IBlockState state)
    {
        return false;
    }

    @Override
    @Deprecated
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, java.util.List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean bool)
    {
        if (entityIn instanceof EntityCreature)
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getCollisionBoundingBox(worldIn, pos));
        }
    }

    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn instanceof EntityPlayer)
        {
            entityIn.motionX *= 0.4D;
            if (entityIn.motionY < 0)
            {
                entityIn.motionY *= 0.4D;
            }
            entityIn.motionZ *= 0.4D;

            if (particleDelay <= 0)
            {
                worldIn.spawnParticle(EnumParticleTypes.FALLING_DUST, entityIn.posX + ((random.nextDouble() * 1) - 0.5), entityIn.posY + 1 + ((random.nextDouble() * 2) - 1), entityIn.posZ + ((random.nextDouble() * 1) - 0.5), 0, 0, 0, 88);
                particleDelay = 1 + random.nextInt(4);
            }
            else
            {
                particleDelay--;
            }
        }
    }

    @Override
    @Deprecated
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();

        if (block == this)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
