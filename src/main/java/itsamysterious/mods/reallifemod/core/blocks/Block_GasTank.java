package itsamysterious.mods.reallifemod.core.blocks;

import itsamysterious.mods.reallifemod.core.RLMFurnitureBlock;
import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasTank;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class Block_GasTank extends RLMFurnitureBlock {

	public Block_GasTank() {
		super("gasTank",new TileEntity_GasTank());
		setUnlocalizedName("petrolPump");
	}
	


}
