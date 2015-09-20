package itsamysterious.mods.reallifemod.core.rendering.Entities;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.entities.EntityPylon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderPylon extends Render{
	private final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_Pylon.obj"));
	private final ResourceLocation texture = new ResourceLocation("reallifemod:textures/models/block/texture_Pylon.png");
	
	public RenderPylon() {
		super(Minecraft.getMinecraft().getRenderManager());
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float yaw) {
		EntityPylon e = (EntityPylon) entity;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glScaled(0.5, 0.5, 0.5);
		GL11.glRotated(yaw, 0, 1, 0);
		GL11.glRotated(e.rotationPitch, 1, 0, 0);
		GL11.glRotated(e.rotationRoll, 0, 0, 1);
		bindTexture(texture);
		model.renderAll();
		GL11.glPopMatrix();

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
