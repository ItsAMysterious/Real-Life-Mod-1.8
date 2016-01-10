package itsamysterious.mods.reallifemod.core.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.RealLifeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class ScreenTab extends Gui {

	public List<GuiButton> buttonList = new ArrayList<GuiButton>();
	public int id;
	public String tabText;
	public boolean isSelected;
	public int xPos, yPos;
	private int tabwidth = 40;

	public ScreenTab(int i, String text) {
		id = i;
		tabText = text;
		isSelected = false;
	}

	public void updateTab() {

	}

	public void initTab() {

	}

	private void onMouseClicked(int mouseX, int mouseY, int mouseID) {
		if (isSelected) {
			for (GuiButton b : buttonList) {
				if (b.isMouseOver()&&b.enabled) {
					b.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY);
					actionPerformed(b);
				}
			}
		}

	}

	public void drawTab(int mouseX, int mouseY, float partialTicks) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		int left = res.getScaledWidth() / 2 - ((40 * RLMMenu.tabs.size()) / 2);
		Minecraft.getMinecraft().getRenderManager().renderEngine
				.bindTexture(new ResourceLocation("reallifemod:textures/gui/Tabs.png"));
		if (isSelected) {
			// Draw the whole stuff
			for (GuiButton b : buttonList) {
				b.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
			}
			GL11.glColor3f(1, 1, 1);
		} else {
			// Draw the unselected tab at the propper position
			int position = tabwidth * id;
		}

		drawCenteredString(mc.fontRendererObj, tabText, left + id * 40 + 20, res.getScaledHeight() / 2 - 95,
				Color.white.getRGB());
		GL11.glColor3f(1, 1, 1);

	}

	public void actionPerformed(GuiButton b) {

	}

}
