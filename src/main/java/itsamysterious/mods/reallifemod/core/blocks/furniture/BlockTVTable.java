package itsamysterious.mods.reallifemod.core.blocks.furniture;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_TVTable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockTVTable extends RLMBlockContainer implements ITileEntityProvider{
	public BlockTVTable() {
		super(Material.wood);
		setUnlocalizedName("TVTable");
		setCreativeTab(RealLifeMod.RLMFurniture);
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_TVTable();
	}
}
