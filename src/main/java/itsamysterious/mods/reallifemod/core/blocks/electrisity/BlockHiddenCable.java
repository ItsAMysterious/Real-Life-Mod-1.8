package itsamysterious.mods.reallifemod.core.blocks.electrisity;

import java.util.Iterator;

import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_HiddenCable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockHiddenCable extends RLMBlockContainer{

	private Block theBlock;

	public  BlockHiddenCable() {
		super(Material.rock);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_HiddenCable(this.theBlock);
	}

}
