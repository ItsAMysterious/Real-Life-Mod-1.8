package itsamysterious.mods.reallifemod.core.blocks;

import itsamysterious.mods.reallifemod.core.RLMFurnitureBlock;
import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasPump;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Block_PetrolPump extends RLMFurnitureBlock {

	public Block_PetrolPump() {
		super("petrolPump",new TileEntity_GasPump());
		setUnlocalizedName("petrolPump");
	}

}
