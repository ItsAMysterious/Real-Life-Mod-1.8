package itsamysterious.mods.reallifemod.core.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.packets.SetPropertiesPackage;
import itsamysterious.mods.reallifemod.core.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class RLMMenu extends RLMTabbedScreen {

	public RLMMenu() {
		tabs.clear();
		tabs.add(new LifeTab());
		tabs.add(new CarsTab());
		tabs.add(new MiscTab());
		tabs.add(new OptionsTab());
	}

	@Override
	public void initGui() {
		super.initGui();

	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	class LifeTab extends ScreenTab {
		public LifeTab() {
			super(0, "Life");
		}

		public void initTab() {
			buttonList.clear();
			buttonList.add(new GuiButton(0, width / 2 + 70, height / 2 + 45, 50, 20, "Options"));
		}

		@Override
		public void drawTab(int mouseX, int mouseY, float partialTicks) {
			super.drawTab(mouseX, mouseY, partialTicks);
			if (isSelected) {
				RLMPlayerProps props = RLMPlayerProps.get(Minecraft.getMinecraft().thePlayer);
				if (props != null) {
					drawString(fontRendererObj, "Name: " + props.getName() + " " + props.getSurname(), width / 2 - 115,
							height / 2 - 70, Color.white.getRGB());
				} else {
					RLMPlayerProps.register(Minecraft.getMinecraft().thePlayer);
					RealLifeMod.network.sendToServer(new SetPropertiesPackage(
							Minecraft.getMinecraft().thePlayer.getEntityId(), "Test", "Test", "male"));

				}

				GL11.glDisable(GL11.GL_LIGHTING);
				EntityPlayer p = Minecraft.getMinecraft().thePlayer;
				Minecraft.getMinecraft().entityRenderer.disableLightmap();
				renderEntityAtPos(p, width / 2 + 75, height / 2 + 15, -90, 0, 0, 4, 4);
				GL11.glColor3f(1, 1, 1);
			}
		}
	}

	public void renderEntityAtPos(Entity e, int x, int y, float rotationYaw, float rotationPitch, float rotationRoll,
			float scaleX, float scaleY) {
		Entity entity = e;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef(x, y, 5.0F);
		GL11.glRotatef(rotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rotationRoll, 0.0F, 0.0F, 1.0F);

		GL11.glScalef(scaleX * 10, scaleY * 10, scaleX * 10);
		GL11.glPushMatrix();
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		GL11.glPushMatrix();
		Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(e, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		GL11.glPopMatrix();

	}

	class MiscTab extends ScreenTab {
		public MiscTab() {
			super(2, "Shop");
		}

		@Override
		public void drawTab(int mouseX, int mouseY, float partialTicks) {
			super.drawTab(mouseX, mouseY, partialTicks);
		}

	}

	class OptionsTab extends ScreenTab {
		public OptionsTab() {
			super(3, "Options");
		}

		@Override
		public void drawTab(int mouseX, int mouseY, float partialTicks) {
			super.drawTab(mouseX, mouseY, partialTicks);
		}

	}

}
