package itsamysterious.mods.reallifemod.core.enhanceminecraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DoubleSlabBlock extends BlockContainer{
	
	protected DoubleSlabBlock(Block b1, Block b2) {
		super(null);
	}
	
	public void onBlockAdded(World worldIn, net.minecraft.util.BlockPos pos, net.minecraft.block.state.IBlockState state) {
		createTileEntity(worldIn, state);
	};

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDoubleSlab();
	}

}
