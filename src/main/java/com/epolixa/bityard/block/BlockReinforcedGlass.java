package com.epolixa.bityard.block;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.item.ItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockReinforcedGlass extends BlockBase implements ItemModelProvider
{
    public BlockReinforcedGlass()
    {
        super(Material.GLASS, MapColor.GRAY, "reinforced_glass");
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setHardness(4.0F);
        this.setResistance(9.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setSoundType(SoundType.GLASS);
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(item, 0, "reinforced_glass");
    }

    @Override
    public String getHarvestTool(IBlockState state)
    {
        return "pickaxe";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
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
