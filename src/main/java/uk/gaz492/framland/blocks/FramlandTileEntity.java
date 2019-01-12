package uk.gaz492.framland.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import uk.gaz492.framland.ModBlocks;

import java.util.Random;

public class FramlandTileEntity extends TileEntity implements ITickable {

    private int tickCount = 0;

    @Override
    public void update() {
        if (!world.isRemote) {
            int growthTick = new Random().nextInt((120 - 20) + 1) + 20;
            if (tickCount >= growthTick) {
                tickCount = 0;

                world.getBlockState(pos).getBlock().updateTick(world, pos, ModBlocks.blockFramland.getDefaultState(), new Random());
                world.markBlockRangeForRenderUpdate(pos, pos);
            }
            tickCount++;
        }
    }

    private void updateCounter() {
    }
}
