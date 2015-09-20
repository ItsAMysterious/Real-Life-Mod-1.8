package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.Screenshotspack;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntityDigitalFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderPictureFrame extends TileEntitySpecialRenderer {
	private IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_DigitalFrame.obj"));
	private final ResourceLocation texture = new ResourceLocation(
			"reallifemod:textures/models/block/texture_DigitalFrame.png");
	private static List<BufferedImage> images = new ArrayList<BufferedImage>();
	private static List<ResourceLocation> imagesr = new ArrayList<ResourceLocation>();

	public RenderPictureFrame() {
		if(Screenshotspack.filenames.size()>0)
		for (int i = 0; i < Screenshotspack.filenames.size() - 1; i++) {
			try {
				ResourceLocation r = new ResourceLocation("screenshots:" + Screenshotspack.filenames.get(i));
				System.out.println(Minecraft.getMinecraft().mcDataDir+ "/screenshots/" + r.getResourcePath());
				BufferedImage image = ImageIO.read(new File(Minecraft.getMinecraft().mcDataDir + "/screenshots/" + r.getResourcePath()));
				images.add(image);
				imagesr.add(r);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f, int i) {
		if (t!=null&&t instanceof TileEntityDigitalFrame) {
			TileEntityDigitalFrame tile = (TileEntityDigitalFrame) t;
			if (!tile.isInvalid()) {
				glPushMatrix();
				glDisable(GL_CULL_FACE);
				glTranslatef((float) x + 0.5f, (float) y, (float) z + 0.5f);
				GL11.glRotated(-tile.rotation, 0, 1, 0);
				GL11.glScaled(0.75, 0.75, 0.75);
				WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
				bindTexture(texture);
				model.renderPart("model");
				GlStateManager.pushAttrib();
				GlStateManager.pushMatrix();
				if (!imagesr.isEmpty()&&imagesr.get(tile.image) != null) {
					bindTexture(imagesr.get(tile.image));
				}else
				{
					bindTexture(new ResourceLocation("reallifemod:textures/models/block/EmptyFrame.png"));
				}
					renderer.startDrawing(GL11.GL_QUADS);
					GL11.glDisable(GL11.GL_CULL_FACE);
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glRotated(180, 0, 1, 0);
					GL11.glRotated(20, -1, 0, 0);
					GL11.glTranslated(0, 0, 0.01);
					renderer.addVertexWithUV(-0.423000, 0.585464, 0.088828, 0.0, 0.0);
					renderer.addVertexWithUV(0.423000, 0.585464, 0.088828, 1, 0.0);
					renderer.addVertexWithUV(0.423000d, 0.026376d, -0.009754d, 1, 1);
					renderer.addVertexWithUV(-0.423000, 0.026376, -0.009754, 0.0, 1);
					Tessellator.getInstance().draw();
					GlStateManager.popAttrib();
					GlStateManager.popMatrix();
				glPopMatrix();
			}
		}
	}

}
