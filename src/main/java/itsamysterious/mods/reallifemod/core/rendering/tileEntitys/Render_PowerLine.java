package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import itsamysterious.mods.reallifemod.api.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_PowerLine;
import itsamysterious.mods.reallifemod.core.utils.MathUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class Render_PowerLine extends TileEntitySpecialRenderer {
	private final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_PowerLine.obj"));
	private final ResourceLocation texture = new ResourceLocation(
			"reallifemod:textures/models/block/texture_PowerLine.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double posX, double posY, double posZ, float p_180535_8_,
			int p_180535_9_) {
		TileEntity_PowerLine tile = (TileEntity_PowerLine) te;
		glPushMatrix();
		glDisable(GL_CULL_FACE);
		glTranslatef((float) posX + 0.5f, (float) posY, (float) posZ + 0.5f);

		GL11.glRotated(tile.rotation, 0, 1, 0);
		GL11.glScaled(0.5, 0.5, 0.5);
		bindTexture(texture);
		model.renderAll();
		glPopMatrix();
		GL11.glPushMatrix();
		GL11.glLineWidth(2.5F);
		WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
		
		if (tile.storedPos != null) {
			BlockPos pos = tile.storedPos;
			BlockPos pos2 = tile.getPos();
			Vector3f normalized1 = MathUtils.blockPosVec(pos).normalise(null);
			normalized1.setY(0);	
			Vector3f normalized2 = MathUtils.blockPosVec(pos2).normalise(null);
			normalized2.setY(0);

			double distance = euclidianDistance(MathUtils.blockPosVec(pos), MathUtils.blockPosVec(pos2));
			double scalefactor = distance * 5.81;
			double stepvalue=0;

			float angle = Vector3f.angle(normalized1, normalized2 );
			if(Math.abs(normalized1.x)<Math.abs(normalized2.x)){
			}


			// Drawing
			GlStateManager.pushAttrib();
			GlStateManager.pushMatrix();
			worldRenderer.startDrawing(GL11.GL_LINE_STRIP);
			worldRenderer.setColorOpaque(0, 0, 0);

			for (double i = -distance / 2; i <= distance / 2; i += 1D) {
				double cosh = Math.cosh(i / distance / 2.0) * scalefactor;
				double offsetY = (Math.cosh(distance / 2.0 / distance / 2) * scalefactor);
				worldRenderer.addVertex(posX + 0.5 + 7 - (i+distance/2)*stepvalue/2 , posY + 21.98 - offsetY + cosh,
						posZ + 0.5 + distance / 2 + i );

			}
			Tessellator.getInstance().draw();

			worldRenderer.startDrawing(GL11.GL_LINE_STRIP);
			worldRenderer.setColorOpaque(0, 0, 0);
			for (double i = -distance / 2; i <= distance / 2; i += 1D) {
				double cosh = Math.cosh(i / distance / 2.0) * scalefactor;
				double offsetY = (Math.cosh(distance / 2 / distance / 2) * scalefactor);
				worldRenderer.addVertex(posX - 7 - (i+distance/2)*stepvalue/2, posY + 21.98 - offsetY + cosh,
						posZ + 0.5 + distance / 2 +i);

			}
			Tessellator.getInstance().draw();

			worldRenderer.startDrawing(GL11.GL_LINE_STRIP);
			worldRenderer.setColorOpaque(0, 0, 0);
			for (double i = -distance / 2; i <= distance / 2; i += 1D) {
				double cosh = Math.cosh(i / distance / 2.0) * scalefactor;
				double offsetY = (Math.cosh(distance / 2 / distance / 2) * scalefactor);
				worldRenderer.addVertex(posX + 0.5 + 3.5 - (i+distance/2)*stepvalue/2, posY + 21.98 - offsetY + cosh,
						posZ + 0.5 + distance / 2 + i );

			}
			Tessellator.getInstance().draw();

			worldRenderer.startDrawing(GL11.GL_LINE_STRIP);
			worldRenderer.setColorOpaque(0, 0, 0);
			for (double i = -distance / 2; i <= distance / 2; i += 1D) {
				double cosh = Math.cosh(i / distance / 2.0) * scalefactor;
				double offsetY = (Math.cosh(distance / 2 / distance / 2) * scalefactor);
				worldRenderer.addVertex(posX + 0.5 - 3.5 - (i+distance/2)*stepvalue/2, posY + 21.98 - offsetY + cosh,
						posZ + 0.5 + distance / 2 + i );

			}
			Tessellator.getInstance().draw();

			worldRenderer.startDrawing(GL11.GL_LINE_STRIP);
			worldRenderer.setColorOpaque(0, 0, 0);
			for (double i = -distance / 2; i <= distance / 2; i += 1D) {
				double cosh = Math.cosh(i / distance / 2.0) * scalefactor;
				double offsetY = (Math.cosh(distance / 2 / distance / 2) * scalefactor);
				worldRenderer.addVertex(posX + 0.5 - (i+distance/2)*stepvalue/2, posY + 29.98 - offsetY + cosh,
						posZ + 0.5 + distance / 2 + i );

			}
			Tessellator.getInstance().draw();

			GlStateManager.popMatrix();
			GlStateManager.popAttrib();
		}
		GL11.glPopMatrix();

	}
	


	private double euclidianDistance(Vector3f x, Vector3f y) {
		return Math.sqrt(((y.x - x.x) * (y.x - x.x)) + (y.y - x.y) * (y.y - x.y) + (y.z - x.z) * (y.z - x.z));
	}

}
