package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Pinwheel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderPinwheel extends TileEntitySpecialRenderer {
	private final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_Pinwheel.obj"));
	private final ResourceLocation texture = new ResourceLocation(
			"reallifemod:textures/models/block/texture_Pinwheel.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_180535_8_, int p_180535_9_) {
		TileEntity_Pinwheel tile = (TileEntity_Pinwheel) te;
		if (!te.isInvalid()) {
			glPushMatrix();
			glDisable(GL_CULL_FACE);
			glTranslatef((float) x + 0.5f, (float) y, (float) z + 0.5f);
			GL11.glRotated(tile.rotation, 0, 1, 0);
			bindTexture(texture);
			GL11.glScalef(3, 3, 3);
			model.renderPart("poll");
			glPushMatrix();
			GL11.glRotated(tile.gondlerotation, 0, 1, 0);
			glTranslatef(0, 18, 0);
			model.renderPart("gondle");
			glPopMatrix();

			glPushMatrix();
			glTranslatef(0, 18, 0);
			GL11.glRotated(tile.gondlerotation, 0, 1, 0);
			GL11.glRotated(tile.rotorangle, 1, 0, 0);
			model.renderPart("rotor");
			glPopMatrix();
			glPopMatrix();
		}
	}

	private double euclidianDistance(Vector3f x, Vector3f y) {
		return Math.sqrt(((y.x - x.x) * (y.x - x.x)) + (y.y - x.y) * (y.y - x.y) + (y.z - x.z) * (y.z - x.z));
	}

}
