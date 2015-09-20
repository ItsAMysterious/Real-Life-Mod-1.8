package itsamysterious.mods.reallifemod.core.blocks.sanitary;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import itsamysterious.mods.reallifemod.core.blocks.RLMFurnitureBlock;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Toilet;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;

public class BlockToilet extends RLMFurnitureBlock {

	public BlockToilet() {
		super("RLMToilet", new TileEntity_Toilet());
	}
	
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, 
    		EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if(RLMPlayerProps.get(playerIn)!=null){
        	RLMPlayerProps.get(playerIn).setPeeing();
    	}
        return true;
    }

}
