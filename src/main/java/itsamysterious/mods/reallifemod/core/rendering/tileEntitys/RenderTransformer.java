package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Transformer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderTransformer extends TileEntitySpecialRenderer{
	//private IModel model ; 
	private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_Transformer.obj"));
	private final ResourceLocation texture = new ResourceLocation("reallifemod:textures/models/block/texture_Transformer.png");

	public RenderTransformer() {
		//try {
			//model= B3DLoader.instance.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_Transformer.obj"));
		//} catch (IOException e) {
			//e.printStackTrace();
		//}	}
	}
	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z,
			float f, int i) {
		TileEntity_Transformer tile = (TileEntity_Transformer)t;
			glPushMatrix();
			glDisable(GL_CULL_FACE);
			glTranslatef((float) x + 0.5f, (float) y, (float) z + 0.5f);
			GL11.glRotated(tile.rotation, 0, 1, 0);
			bindTexture(texture);
			model.renderAll();
			GL11.glPushMatrix();
			GL11.glDisable(GL_CULL_FACE);
			glTranslatef(0.55f,2.9f,-2.1f);
			GL11.glScaled(0.015, 0.015, 0.015);
			GL11.glRotated(180,0,0,1);
			Minecraft.getMinecraft().fontRendererObj.drawString(String.valueOf("Voltage: "+tile.getVoltage()), 0, 0, Color.yellow.getRGB());
			Minecraft.getMinecraft().fontRendererObj.drawString(String.valueOf("Status: "+(tile.isUsuable?"Active":"Broken")), 0, 10, Color.yellow.darker().darker().getRGB());
			Minecraft.getMinecraft().fontRendererObj.drawString("Age: "+String.valueOf(tile.age/60/20)+"h", 0, 20, Color.yellow.darker().darker().getRGB());

			GL11.glPopMatrix();
		glPopMatrix();
	}

}
