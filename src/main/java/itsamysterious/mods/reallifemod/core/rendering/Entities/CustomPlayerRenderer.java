package itsamysterious.mods.reallifemod.core.rendering.Entities;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import api.player.render.RenderPlayerAPI;
import api.player.render.RenderPlayerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class CustomPlayerRenderer extends RenderPlayerBase {
	public static AdvancedPlayerModel model = new AdvancedPlayerModel();

	public CustomPlayerRenderer(RenderPlayerAPI renderPlayerAPI) {
		super(renderPlayerAPI);
	}

	@Override
	public void doRender(AbstractClientPlayer entity, double paramDouble1, double paramDouble2, double paramDouble3,
			float paramFloat1, float paramFloat2) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslated(0, 1.5f, 0);
		GL11.glRotated(-entity.rotationYaw, 0, 1, 0);
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minecraft:textures/entity/steve.png"));
		model.render(entity, (float) paramDouble1, (float) paramDouble2, (float) paramDouble3, paramFloat1, paramFloat2,-0.065f);
		GL11.glPopMatrix();
		GL11.glCullFace(GL11.GL_BACK);

	}

	@Override
	public ResourceLocation getEntityTexture(AbstractClientPlayer player) {
		return player.getLocationSkin();
	}

}
