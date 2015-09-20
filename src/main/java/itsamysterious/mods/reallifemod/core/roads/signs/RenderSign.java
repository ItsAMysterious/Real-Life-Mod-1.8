package itsamysterious.mods.reallifemod.core.roads.signs;

import org.apache.http.impl.cookie.IgnoreSpecFactory;
import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Sign;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderSign extends TileEntitySpecialRenderer {
	private final IModelCustom signmodel = AdvancedModelLoader
			.loadModel(new ResourceLocation(Reference.ID + ":models/obj/block/model_Sign.obj"));
	private final ResourceLocation polltexture = new ResourceLocation(
			Reference.ID + ":textures/models/block/texture_Sign.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f, int i) {
		if (!te.isInvalid()) {
			TileEntity_Sign tile = (TileEntity_Sign) te;
			if (tile.file != null) {
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_CULL_FACE);
				double height = 1
						- te.getWorld().getBlockState(te.getPos().add(0, -0.6, 0)).getBlock().getBlockBoundsMaxY();
				GL11.glTranslated(x + 0.5, y - height, z + 0.5);
				GL11.glScaled(0.95, 0.95, 0.95);
				GL11.glRotated(tile.rotation * 90, 0, 1, 0);
				GL11.glPushMatrix();
				bindTexture(polltexture);
				signmodel.renderPart("poll");
				GL11.glPopMatrix();
				GL11.glTranslated(0, 2.5, 0);
				// Rendering the rest
				bindTexture(tile.file.texture);
				signmodel.renderPart(tile.file.type.toString());
				GL11.glPopMatrix();
			}
		}

	}

}
