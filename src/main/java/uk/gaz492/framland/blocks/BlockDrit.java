package uk.gaz492.framland.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.gaz492.framland.Framland;
import uk.gaz492.framland.tileentities.TileDrit;
import uk.gaz492.framland.util.ModInformation;

import java.util.Random;

public class BlockDrit extends Block implements ITileEntityProvider {

    public static final ResourceLocation DRIT = new ResourceLocation(ModInformation.MOD_ID, "drit");

    public BlockDrit() {
        super(Material.GROUND);
        setRegistryName(DRIT);
        setUnlocalizedName(ModInformation.MOD_ID + ".drit");
        setSoundType(SoundType.GROUND);
        setCreativeTab(Framland.creativeTab);
        setHardness(0.6f);
        setHarvestLevel("shovel", 0);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        BlockPos plantPos = pos.up(1);
        EnumPlantType plantType = plantable.getPlantType(world, plantPos);
        return plantType == EnumPlantType.Crop;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        super.getItemDropped(state, rand, fortune);
        return Blocks.DIRT.getItemDropped(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, world, pos, blockIn, fromPos);

        if (world.getBlockState(pos.up(1)).getMaterial().isSolid()) {
            world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 3);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileDrit();
    }

}
