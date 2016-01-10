package itsamysterious.mods.reallifemod.core.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class RenderUtils {

	
	  public static void renderEntityAtPos(int x, int y, int scale, float rotationyaw, float rotationPitch, EntityLivingBase entity)
	    {
	        GlStateManager.enableColorMaterial();
	        GlStateManager.pushMatrix();
	        GlStateManager.translate((float)x, (float)y, 50.0F);
	        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
	        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
	        float f2 = entity.renderYawOffset;
	        float f3 = entity.rotationYaw;
	        float f4 = entity.rotationPitch;
	        float f5 = entity.prevRotationYawHead;
	        float f6 = entity.rotationYawHead;
	        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
	        RenderHelper.enableStandardItemLighting();
	        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.rotate(-((float)Math.atan((double)(rotationPitch / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
	        entity.renderYawOffset = (float)Math.atan((double)(rotationyaw / 40.0F)) * 20.0F;
	        entity.rotationYaw = (float)Math.atan((double)(rotationyaw / 40.0F)) * 40.0F;
	        entity.rotationPitch = -((float)Math.atan((double)(rotationPitch / 40.0F))) * 20.0F;
	        entity.rotationYawHead = entity.rotationYaw;
	        entity.prevRotationYawHead = entity.rotationYaw;
	        GlStateManager.translate(0.0F, 0.0F, 0.0F);
	        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
	        rendermanager.setPlayerViewY(180.0F);
	        rendermanager.setRenderShadow(false);
	        rendermanager.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
	        rendermanager.setRenderShadow(true);
	        entity.renderYawOffset = f2;
	        entity.rotationYaw = f3;
	        entity.rotationPitch = f4;
	        entity.prevRotationYawHead = f5;
	        entity.rotationYawHead = f6;
	        GlStateManager.popMatrix();
	        RenderHelper.disableStandardItemLighting();
	        GlStateManager.disableRescaleNormal();
	        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GlStateManager.disableTexture2D();
	        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	    }

}
