package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_HiddenCable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ItemModelMesherForge;

public class RenderHiddenCable extends TileEntitySpecialRenderer{

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z,
			float f, int i) {
		TileEntity_HiddenCable tile = (TileEntity_HiddenCable) t;
		//ResourceLocation blockTexture = Block.getBlockFromName(tile.block).
		
	}

}
