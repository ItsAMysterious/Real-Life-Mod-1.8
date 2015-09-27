package itsamysterious.mods.reallifemod.core.blocks.furniture;

import java.util.ArrayList;
import java.util.List;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Table;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockTable extends RLMBlockContainer implements ITileEntityProvider {
	public BlockTable() {
		super(Material.wood);
		setUnlocalizedName("BlockTable");
		setCreativeTab(RealLifeMod.RLMFurniture);
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		TileEntity_Table tile = (TileEntity_Table) world.getTileEntity(pos);
		if (player.getCurrentEquippedItem() != null) {
			if (tile.placeItemOnTable(player.getCurrentEquippedItem())) {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			}
		} else {
			tile.removeItemFromTable();
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
		return new TileEntity_Table();
	}
}
