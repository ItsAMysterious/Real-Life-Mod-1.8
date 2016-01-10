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
import net.minecraft.util.EnumChatFormatting;

public class RLMMenu extends RLMTabbedScreen {

	public RLMMenu() {
		tabs.clear();
		tabs.add(new LifeTab());
		tabs.add(new CarsTab());
		tabs.add(new MiscTab());
		tabs.add(new OptionsTab());
		tabs.add(new JobTab());

		tabs.get(0).isSelected = true;
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
		private Minecraft mc;

		public LifeTab() {
			super(0, EnumChatFormatting.GOLD + "Life");
			this.mc = Minecraft.getMinecraft();
		}

		public void initTab() {
			buttonList.clear();
			buttonList.add(new GuiButton(0, width / 2 + 70, height / 2 + 45, 50, 20, "Options"));
		}

		@Override
		public void drawTab(int mouseX, int mouseY, float partialTicks) {
			super.drawTab(mouseX, mouseY, partialTicks);
			if (isSelected) {
				EntityPlayer p = Minecraft.getMinecraft().thePlayer;
				RLMPlayerProps props = RLMPlayerProps.get(p);
				drawString(fontRendererObj, "Name: " + props.getName() + " " + props.getSurname(), width / 2 - 115,
						height / 2 - 70, Color.white.getRGB());
				// drawString(fontRendererObj,"Gender: " +
				// (props.gender.toString()), width / 2 - 115,height / 2 - 60,
				// Color.white.getRGB());
				drawString(fontRendererObj, "Age: " + (p.getAge()), width / 2 - 115, height / 2 - 50,
						Color.white.getRGB());
				drawString(fontRendererObj, "Money: " + (props.cash) + "€", width / 2 - 115, height / 2 - 40,
						Color.white.getRGB());
				drawString(fontRendererObj, "-----------------------", width / 2 - 115, height / 2 - 30,
						Color.white.getRGB());
				drawString(
						fontRendererObj, EnumChatFormatting.BLUE + "Waterlevel: " + EnumChatFormatting.WHITE
								+ props.WaterLevel * 100 + "ml",
						width / 2 - 115, height / 2 - 20, Color.white.getRGB());
				drawString(fontRendererObj,
						EnumChatFormatting.DARK_GREEN + "Mood: " + EnumChatFormatting.WHITE + props.feeling,
						width / 2 - 115, height / 2 - 10, Color.white.getRGB());

				GL11.glColor3f(1, 1, 1);
				GL11.glTranslated(0, 0, 10);
				RenderUtils.renderEntityAtPos(width / 2 + 75, height / 2 + 15, 0, -90 + mouseX, 0, this.mc.thePlayer);

			}

		}

		public void actionPerformed(GuiButton b) {

		}
	}

	public void renderEntityAtPos(Entity e, int x, int y, float rotationYaw, float rotationPitch, float rotationRoll,
			float scaleX, float scaleY) {

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

	class JobTab extends ScreenTab {
		public JobTab() {
			super(4, "My Job");
		}

		@Override
		public void drawTab(int mouseX, int mouseY, float partialTicks) {
			if (isSelected) {
				drawCenteredString(fontRendererObj,
						"You are a: " + RLMPlayerProps.get(Minecraft.getMinecraft().thePlayer).profession.name(),
						width / 2, height / 2 - 80, Color.white.getRGB());
			}
			super.drawTab(mouseX, mouseY, partialTicks);
		}

	}

}
