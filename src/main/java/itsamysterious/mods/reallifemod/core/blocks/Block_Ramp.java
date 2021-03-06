package itsamysterious.mods.reallifemod.core.blocks;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Ramp;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Tarmac;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Block_Ramp extends RLMBlockContainer{
	
	public Block_Ramp() {
		super(Material.rock);
		setUnlocalizedName("blockRamp");
		setCreativeTab(RealLifeMod.Cars);
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
		setBlockBounds(0, 0, 0, 0.95f, 0.25f, 0.95f);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
		TileEntity_Ramp t = (TileEntity_Ramp) worldIn.getTileEntity(pos);
		entityIn.posY=(float)t.getHeight((float)entityIn.posX+0.001f%1,(float)entityIn.posZ+0.001f%1);
		t.entities.clear();
		t.entities.add(entityIn);
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
    
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_Ramp();
	}
	
}
