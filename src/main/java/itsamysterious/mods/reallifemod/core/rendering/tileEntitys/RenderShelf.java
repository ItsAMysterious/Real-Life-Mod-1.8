package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Shelf;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderShelf extends TileEntitySpecialRenderer {
	private final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_Shelf.obj"));
	private final ResourceLocation texture = new ResourceLocation("reallifemod:textures/models/block/texture_Wood.png");

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f, int i) {
		TileEntity_Shelf tile = (TileEntity_Shelf) t;
		if (!tile.isInvalid()) {

			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y, z + 0.5);
			GL11.glRotated(-tile.rotation * 90, 0, 1, 0);
			bindTexture(texture);
			model.renderAll();
			GL11.glPopMatrix();

		}
	}

}
