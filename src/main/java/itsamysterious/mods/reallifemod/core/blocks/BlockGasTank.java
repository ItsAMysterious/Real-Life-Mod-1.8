package itsamysterious.mods.reallifemod.core.blocks;

import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasTank;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockGasTank extends RLMFurnitureBlock {

	public BlockGasTank() {
		super("gasTank",new TileEntity_GasTank());
		setUnlocalizedName("petrolPump");
	}
	


}
