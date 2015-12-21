package itsamysterious.mods.reallifemod.core.blocks;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMFurnitureBlock;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Drawer;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import itsamysterious.mods.reallifemod.core.tiles.RLM_StorageTileEntity;

public class Block_Drawer extends RLMFurnitureBlock{

	public Block_Drawer() {
		super("RLMDrawer", new TileEntity_Drawer());
	}

}
