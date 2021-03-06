package uk.gaz492.framland;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.gaz492.framland.blocks.BlockDrit;
import uk.gaz492.framland.blocks.BlockFramland;

public class ModBlocks {

    @GameRegistry.ObjectHolder("framland:framland")
    public static BlockFramland blockFramland;

    @GameRegistry.ObjectHolder("framland:drit")
    public static BlockDrit blockDrit;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockFramland.initModel();
        blockDrit.initModel();
    }
}
