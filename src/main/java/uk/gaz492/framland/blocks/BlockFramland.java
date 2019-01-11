package uk.gaz492.framland.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
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
import uk.gaz492.framland.ModBlocks;
import uk.gaz492.framland.util.ModInformation;

import java.util.Random;

public class BlockFramland extends Block {

    public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
    public static final ResourceLocation FRAMLAND = new ResourceLocation(ModInformation.MOD_ID, "framland");

    public BlockFramland() {
        super(Material.GROUND);
        setRegistryName(FRAMLAND);
        setUnlocalizedName(ModInformation.MOD_ID + ".framland");
        setCreativeTab(Framland.creativeTab);
        setHardness(0.6f);
        setTickRandomly(true);
        setHarvestLevel("shovel", 0);

        setDefaultState(blockState.getBaseState().withProperty(MOISTURE, 0));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public static void startGrowth(World world, BlockPos pos) {
        System.out.println("Growth Start");
        BlockFramland blockFramland = ModBlocks.blockFramland;
        IBlockState blockFramlandState = blockFramland.getDefaultState();
        world.setBlockState(pos, blockFramlandState);
        world.scheduleUpdate(pos.toImmutable(), blockFramland, 10);
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
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);

        System.out.println("Framland Update");
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(MOISTURE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MOISTURE);
    }

}