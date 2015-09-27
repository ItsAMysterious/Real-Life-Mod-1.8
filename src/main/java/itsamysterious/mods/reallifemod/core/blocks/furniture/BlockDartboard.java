package itsamysterious.mods.reallifemod.core.blocks.furniture;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Materials;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DartBoard;
import itsamysterious.mods.reallifemod.core.entities.EntityDart;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockDartboard extends RLMBlockContainer{

	public BlockDartboard() {
		super(RealLifeMod_Materials.plastic);
		setUnlocalizedName("blockDartboard");
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
		setCreativeTab(RealLifeMod.RLMDeco);
	}
	@Override
	public int getRenderType() {
		return 3;
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_DartBoard();
	}
	
    public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entityIn) {
    	if(entityIn instanceof EntityDart){
    	}
    }


}
