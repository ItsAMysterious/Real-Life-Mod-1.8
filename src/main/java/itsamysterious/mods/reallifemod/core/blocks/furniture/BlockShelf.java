package itsamysterious.mods.reallifemod.core.blocks.furniture;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Shelf;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Table;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockShelf extends RLMBlockContainer implements ITileEntityProvider {
	public BlockShelf() {
		super(Material.wood);
		setUnlocalizedName("Shelf");
		setCreativeTab(RealLifeMod.RLMFurniture);
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
		TileEntity_Shelf tile = (TileEntity_Shelf) world.getTileEntity(pos);
		if (tile.rotation == 0 || tile.rotation == 2) {
			setBlockBounds(-0.5f, 0.0f, 0.0f, 1.5f, 4.0f, 1.0f);
		} else {
			setBlockBounds(0.0f, 0.0f, -0.5f, 1.0f, 4.0f, 1.5f);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entity) {
		if (entity.posX < pos.getX() + getBlockBoundsMaxX())
			entity.posX = pos.getX() + getBlockBoundsMaxX();
		if (entity.posX > pos.getX() + getBlockBoundsMinX()&&entity.posX<pos.getX())
			entity.posX = pos.getX() + getBlockBoundsMinX();
		if (entity.posZ < pos.getZ() + getBlockBoundsMaxZ())
			entity.posZ = pos.getZ() + getBlockBoundsMaxZ();
		if (entity.posZ > pos.getZ() + getBlockBoundsMinZ()&&entity.posZ<pos.getZ())
			entity.posZ = pos.getZ() + getBlockBoundsMinZ();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		TileEntity_Shelf tile = (TileEntity_Shelf) world.getTileEntity(pos);
		if (player.getCurrentEquippedItem() != null) {
			if (tile.placeItemOnshelf(player.getCurrentEquippedItem(), hitX, hitY, hitZ)) {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			}
		} else {
			tile.removeItemFromShelf();
		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity_Table tile = (TileEntity_Table) world.getTileEntity(pos);
		if (tile != null) {
			tile.dropContents();
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_Shelf();
	}
}
