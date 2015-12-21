package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.api.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Lantern;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderLantern extends TileEntitySpecialRenderer {
	private final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_Lantern.obj"));
	private final ResourceLocation texture = new ResourceLocation(
			"reallifemod:textures/models/block/texture_Lantern.png");
	private final ResourceLocation textureOn = new ResourceLocation(
			"reallifemod:textures/models/block/texture_LightOn.png");
	private final ResourceLocation textureOff = new ResourceLocation(
			"reallifemod:textures/models/block/texture_LightOff.png");

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f, int i) {
		TileEntity_Lantern tile = (TileEntity_Lantern) t;
		if (!tile.isInvalid()) {

			GL11.glPushMatrix();
			double height = 1- tile.getWorld().getBlockState(tile.getPos().add(0, -0.6, 0)).getBlock().getBlockBoundsMaxY();
			GL11.glTranslated(x + 0.5, y - height, z + 0.5);
			GL11.glRotated(-tile.rotation*90, 0, 1, 0);
			bindTexture(texture);
			model.renderPart("lantern");
			if (tile.isActive)
				bindTexture(textureOn);
			else
				bindTexture(textureOff);
			model.renderPart("light");
			GL11.glPopMatrix();

		}
	}

}
