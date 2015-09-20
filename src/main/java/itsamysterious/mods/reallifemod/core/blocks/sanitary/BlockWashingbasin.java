package itsamysterious.mods.reallifemod.core.blocks.sanitary;

import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Basin;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWashingbasin extends BlockContainer{

	public BlockWashingbasin() {
		super(Material.rock);
		this.setUnlocalizedName("washbasin");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_Basin();
	}

}
