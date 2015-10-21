package itsamysterious.mods.reallifemod.core.rendering.Entities;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.entities.EntityPylon;
import itsamysterious.mods.reallifemod.core.vehicles.EntityVehicle;
import itsamysterious.mods.reallifemod.core.vehicles.EntityWheel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderWheel extends Render {

	public RenderWheel() {
		super(Minecraft.getMinecraft().getRenderManager());
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float yaw) {
		GL11.glPushMatrix();
		EntityWheel wheel = (EntityWheel) entity;
		GL11.glTranslated(x, y, z);
		GL11.glRotated(wheel.rotationYaw, 0, 1, 0);
		GL11.glRotated(wheel.rotationPitch, 1, 0, 0);
		GL11.glRotated(wheel.rotationRoll, 0, 0, 1);
		bindTexture(wheel.parent.getFile().texture);
		wheel.parent.getFile().model.renderPart(wheel.parent.getFile().wheelsName);
		GL11.glPopMatrix();

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
