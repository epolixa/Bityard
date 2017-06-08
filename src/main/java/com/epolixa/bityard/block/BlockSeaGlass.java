package com.epolixa.bityard.block;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.item.ItemBlockSeaGlass;
import com.epolixa.bityard.item.ItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSeaGlass extends BlockBase implements ItemModelProvider
{
    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.<EnumDyeColor>create("color", EnumDyeColor.class);

    public BlockSeaGlass()
    {
        super(Material.GLASS, MapColor.GRAY, "sea_glass");
        setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(0.5F);
        setSoundType(SoundType.GLASS);
        setLightOpacity(3);
    }

    @Override
    public void registerItemModel(Item item)
    {
        for (int i = 0; i < EnumDyeColor.values().length; i++)
        {
            Bityard.proxy.registerItemRenderer(item, i, "sea_glass/sea_glass_" + EnumDyeColor.byMetadata(i).getName());
        }
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumDyeColor enumdyecolor : EnumDyeColor.values())
        {
            list.add(new ItemStack(itemIn, 1, enumdyecolor.getMetadata()));
        }
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    @Override
    @Deprecated
    public MapColor getMapColor(IBlockState state)
    {
        return ((EnumDyeColor)state.getValue(COLOR)).getMapColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
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
        return true;
    }
    @Override
    @Deprecated
    public boolean isFullBlock(IBlockState state)
    {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {COLOR});
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumDyeColor)state.getValue(COLOR)).getMetadata();
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
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
