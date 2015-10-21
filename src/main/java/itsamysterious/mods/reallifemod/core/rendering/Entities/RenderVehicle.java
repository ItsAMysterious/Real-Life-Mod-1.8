package itsamysterious.mods.reallifemod.core.rendering.Entities;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.vehicles.EntityDriveable;
import itsamysterious.mods.reallifemod.core.vehicles.VehicleFile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderVehicle extends Render {
	public static final Map<String, IModelCustom> models = new HashMap<String, IModelCustom>();
	public static final Map<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();

	public RenderVehicle() {
		super(Minecraft.getMinecraft().getRenderManager());
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
		renderVehicle((EntityDriveable) entity, x, y, z, p_76986_8_, partialTicks);
	};

	private void renderVehicle(EntityDriveable entity, double x, double y, double z, float p_76986_8_,
			float partialTicks) {
		VehicleFile f = entity.getFile();
		if (f == null)
			return;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		Minecraft.getMinecraft().renderEngine.bindTexture(f.texture);
		GL11.glTranslated(x, y, z);
		GL11.glRotated(-p_76986_8_ + 90, 0, 1, 0);
		GL11.glRotated(-entity.rotationPitch, 0, 0, 1);
		GlStateManager.pushMatrix();
		GL11.glPushMatrix();
		f.model.renderPart(f.modelName);
		GL11.glPopMatrix();
		// Rearaxis

		GL11.glPushMatrix();
		// GL11.glTranslated(f.steeringwheelpos.x, f.steeringwheelpos.y,
		// f.steeringwheelpos.z);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GlStateManager.enableBlend();
		if (f.transparentpartsname != null) {
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
			f.model.renderPart(f.transparentpartsname);
		}
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		;
		/*
		 * GlStateManager.pushAttrib(); GlStateManager.pushMatrix();
		 * WorldRenderer render = Tessellator.getInstance().getWorldRenderer();
		 * GlStateManager.disableLighting(); GL11.glBlendFunc(GL11.GL_ONE,
		 * GL11.GL_ONE); render.startDrawing(GL11.GL_POLYGON);
		 * render.setColorRGBA(255, 255, 0, 100); for (float i = 180f; i < 360;
		 * i += 22.5) { render.setNormal((float) Math.sin(Math.toRadians(i +
		 * 20)) * 10, (float) 0.01, (float) Math.cos(Math.toRadians(i + 20)) *
		 * 10); render.addVertex(Math.sin(Math.toRadians(i + 20)) * 10, 0.01,
		 * Math.cos(Math.toRadians(i + 20)) * 10); }
		 * 
		 * Tessellator.getInstance().draw(); GlStateManager.enableLighting();
		 * GlStateManager.popMatrix(); GlStateManager.popAttrib();
		 */
		GL11.glPopMatrix();

	}

	private IModelCustom getmodel(VehicleFile f) {
		return models.get(f.vehicleName);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
