package itsamysterious.mods.reallifemod.core.blocks.electrisity;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Blocks;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DigitalFrame;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Block_PictureFrame extends RLMBlockContainer{

	public Block_PictureFrame(Material materialIn) {	
		super(materialIn);
		setUnlocalizedName("blockDigitalFrame");
		setCreativeTab(RealLifeMod.Technologie);
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_DigitalFrame();
	}
	
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    	super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    	 TileEntity tileentity = worldIn.getTileEntity(pos);
         if (tileentity!=null&&tileentity instanceof RLMTileEntity){
        	 RLMTileEntity tile = (RLMTileEntity)tileentity;
        	 tile.rotation=MathHelper.wrapAngleTo180_float(placer.rotationYawHead);
         }
    }
    

}
