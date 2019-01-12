package uk.gaz492.framland.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import uk.gaz492.framland.ConfigHandler;
import uk.gaz492.framland.ModBlocks;

import java.util.Random;

public class FramlandTileEntity extends TileEntity implements ITickable {

    private int tickCount = 0;

    @Override
    public void update() {
        if (!world.isRemote) {
            if (world.getBlockState(pos.up()).getBlock() instanceof BlockCrops || world.getBlockState(pos.up()).getBlock() instanceof BlockStem) {
                int growthTick = new Random().nextInt((ConfigHandler.framlandGeneral.maxGrowthTicks - ConfigHandler.framlandGeneral.minGrowthTicks) + 1) + ConfigHandler.framlandGeneral.minGrowthTicks;
                if (tickCount >= growthTick) {
                    tickCount = 0;

                    world.getBlockState(pos).getBlock().updateTick(world, pos, ModBlocks.blockFramland.getDefaultState(), new Random());
                    world.markBlockRangeForRenderUpdate(pos, pos);
                }
                tickCount++;
            }

        }
    }
}
