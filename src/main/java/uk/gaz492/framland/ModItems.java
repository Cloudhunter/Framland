package uk.gaz492.framland;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.gaz492.framland.item.ItemThunderHoe;

public class ModItems {

    @GameRegistry.ObjectHolder("framland:thunderhoe")
    public static ItemThunderHoe itemThunderHoe;

    @SideOnly(Side.CLIENT)
    public static void initModels(){
        itemThunderHoe.initModel();
    }
}
