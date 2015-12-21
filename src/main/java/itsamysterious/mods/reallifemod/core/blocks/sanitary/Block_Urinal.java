package itsamysterious.mods.reallifemod.core.blocks.sanitary;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import itsamysterious.mods.reallifemod.core.RLMFurnitureBlock;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Toilet;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Urinal;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;

public class Block_Urinal extends RLMFurnitureBlock {

	public Block_Urinal() {
		super("RLMUrinal", new TileEntity_Urinal());
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (RLMPlayerProps.get(playerIn) != null) {
			RLMPlayerProps.get(playerIn).peeing = true;
		}
		return true;
	}

}
