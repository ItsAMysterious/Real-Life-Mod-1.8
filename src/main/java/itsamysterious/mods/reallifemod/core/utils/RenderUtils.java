package itsamysterious.mods.reallifemod.core.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

public class RenderUtils {
	public static void renderEntityAtPos(Entity e, int x, int y, float rotationYaw, float rotationPitch,
			float rotationRoll, float scaleX, float scaleY) {
		Entity entity=e;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef(x, y, 5.0F);
		GL11.glRotatef(rotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rotationRoll, 0.0F, 0.0F, 1.0F);

		GL11.glScalef(scaleX*10, scaleY*10, scaleX*10);
		GL11.glPushMatrix();
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		GL11.glPushMatrix();
		Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(e, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		GL11.glPopMatrix();

	}

}
