package itsamysterious.mods.reallifemod.core;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.BorderUIResource.TitledBorderUIResource;

import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class RLMBlockContainer extends Block implements ITileEntityProvider {
	public int[] dimensions = new int[] { 0, 0, 0, 1, 1, 1 };

	public RLMBlockContainer(Material materialIn) {
		super(materialIn);
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		RLMTileEntity tile = (RLMTileEntity) worldIn.getTileEntity(pos);
		int i = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if (i == 0 || i == 2) {
			tile.rotation = i + 2 % 3;
		} else
			tile.rotation = i;
		worldIn.markBlockForUpdate(pos);
	}

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
    	if (worldIn.getTileEntity(pos) instanceof TileEntity_Electric) {
			TileEntity_Electric t = (TileEntity_Electric) worldIn.getTileEntity(pos);
			BlockPos p = t.storedPos;
			t.getTo().storedPosFrom = null;
			worldIn.markBlockForUpdate(pos);
		}
    	super.breakBlock(worldIn, pos, state);
    }


	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.SOLID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isFullCube() {
		return true;
	}

}
