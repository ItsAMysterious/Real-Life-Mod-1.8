package itsamysterious.mods.reallifemod.core.rendering.Entities;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.core.vehicles.EntityWheel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderWheel extends Render {

	public RenderWheel() {
		super(Minecraft.getMinecraft().getRenderManager());
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float yaw) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		EntityWheel wheel = (EntityWheel) entity;
		if (wheel.parent != null && wheel.parent.getFile() != null) {

			GL11.glTranslated(x, y, z);
			GL11.glRotated(wheel.rotationYaw, 0, 1, 0);
			GL11.glRotated(wheel.rotationPitch, 0, 0, 1);
			bindTexture(wheel.parent.getFile().texture);
			wheel.parent.getFile().model.renderPart(wheel.parent.getFile().wheelsName);
			GL11.glPushMatrix();
			GL11.glScaled(-0.25, -0.25, 0.25);
			/*if (wheel.ID == 0)
				Minecraft.getMinecraft().getRenderManager().getFontRenderer().drawString(String.valueOf(wheel.ID), 0,
						-8, Color.white.getRGB());
			if (wheel.ID == 1)
				Minecraft.getMinecraft().getRenderManager().getFontRenderer().drawString(String.valueOf(wheel.ID), 1,
						-8, Color.gray.getRGB());
			if (wheel.ID == 2)
				Minecraft.getMinecraft().getRenderManager().getFontRenderer().drawString(String.valueOf(wheel.ID), 5,
						-10, Color.green.getRGB());
			if (wheel.ID == 3)
				Minecraft.getMinecraft().getRenderManager().getFontRenderer().drawString(String.valueOf(wheel.ID), 0,
						-8, Color.orange.getRGB());
						*/

			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
