package itsamysterious.mods.reallifemod.core.blocks.sanitary;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMFurnitureBlock;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Toilet;
import itsamysterious.mods.reallifemod.core.entities.EntitySit;
import itsamysterious.mods.reallifemod.core.packets.DefecatePacket;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Block_Toilet extends RLMFurnitureBlock {

	public Block_Toilet() {
		super("Block_Toilet", new TileEntity_Toilet());
	}
	/*@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		RealLifeMod.network.sendToServer(new DefecatePacket(true, true));
		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}*/
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		RLMTileEntity tile = (RLMTileEntity) worldIn.getTileEntity(pos);
		if (worldIn.isRemote) {
			//worldIn.spawnEntityInWorld(new EntitySit(worldIn, pos));
		}
	}

}
