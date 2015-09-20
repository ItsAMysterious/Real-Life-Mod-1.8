package itsamysterious.mods.reallifemod.core.blocks;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Computer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Drawer;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import itsamysterious.mods.reallifemod.core.tiles.RLM_StorageTileEntity;

public class BlockComputer extends RLMFurnitureBlock{

	public BlockComputer() {
		super("Computer", new TileEntity_Computer());
	}

}
