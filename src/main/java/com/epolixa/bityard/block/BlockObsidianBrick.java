package com.epolixa.bityard.block;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.item.ItemModelProvider;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockObsidianBrick extends BlockBase implements ItemModelProvider
{
    public static final PropertyEnum<BlockObsidianBrick.EnumType> VARIANT = PropertyEnum.<BlockObsidianBrick.EnumType>create("variant", BlockObsidianBrick.EnumType.class);
    public static final int BRICK_META = BlockObsidianBrick.EnumType.BRICK.getMetadata();
    public static final int CARVED_META = BlockObsidianBrick.EnumType.CARVED.getMetadata();

    public BlockObsidianBrick()
    {
        super(Material.ROCK, MapColor.BLACK, "obsidian_brick");
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockObsidianBrick.EnumType.BRICK));
        this.setHardness(40.0F);
        this.setResistance(2000.0F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(item, 0, "obsidian_brick");
        Bityard.proxy.registerItemRenderer(item, 1, "carved_obsidian");
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BityardBlocks.OBSIDIAN_BRICK);
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        return ((BlockObsidianBrick.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (BlockObsidianBrick.EnumType BlockObsidianBrick$enumtype : BlockObsidianBrick.EnumType.values())
        {
            list.add(new ItemStack(itemIn, 1, BlockObsidianBrick$enumtype.getMetadata()));
        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockObsidianBrick.EnumType.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockObsidianBrick.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

    public static enum EnumType implements IStringSerializable
    {
        BRICK(0, "obsidian_brick", "brick"),
        CARVED(1, "carved_obsidian", "carved");

        private static final BlockObsidianBrick.EnumType[] META_LOOKUP = new BlockObsidianBrick.EnumType[values().length];
        private final int meta;
        private final String name;
        private final String unlocalizedName;

        private EnumType(int meta, String name, String unlocalizedName)
        {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        public String toString()
        {
            return this.name;
        }

        public static BlockObsidianBrick.EnumType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        public String getName()
        {
            return this.name;
        }

        public String getUnlocalizedName()
        {
            return this.unlocalizedName;
        }

        static
        {
            for (BlockObsidianBrick.EnumType BlockObsidianBrick$enumtype : values())
            {
                META_LOOKUP[BlockObsidianBrick$enumtype.getMetadata()] = BlockObsidianBrick$enumtype;
            }
        }
    }
}
