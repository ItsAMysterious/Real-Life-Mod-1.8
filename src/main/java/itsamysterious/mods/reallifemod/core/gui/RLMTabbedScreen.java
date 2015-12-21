package itsamysterious.mods.reallifemod.core.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.RealLifeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;

public class RLMTabbedScreen extends GuiScreen {
	public static List<ScreenTab> tabs = new ArrayList<ScreenTab>();

	public RLMTabbedScreen() {

	}

	@Override
	public void initGui() {
		super.initGui();
		for (ScreenTab t : tabs) {
			t.initTab();
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		int left = width / 2 - ((40 * tabs.size() - 1) / 2);
		GL11.glColor3f(1, 1, 1);
		for (int i = 0; i < tabs.size(); i++) {
			ScreenTab tab = tabs.get(i);
			Minecraft.getMinecraft().getRenderManager().renderEngine
					.bindTexture(new ResourceLocation("reallifemod:textures/gui/Tabs.png"));
			if (tab.isSelected) {
				drawModalRectWithCustomSizedTexture(width / 2 - 125, height / 2 - 90, 0, 14, 250, 160, 256, 256);
				drawModalRectWithCustomSizedTexture(left + i * 40, height / 2 - 100, 0, 0, 40, 12, 256, 256);

			} else {
				drawModalRectWithCustomSizedTexture(left + i * 40, height / 2 - 100, 40, 0, 40, 12, 256, 256);

			}
			tab.drawTab(mouseX, mouseY, partialTicks);
		}

		drawString(fontRendererObj, "X:" + mouseX + ", Y:" + mouseY, mouseX, mouseY, Color.green.getRGB());

	}

	@Override
	protected void mouseClicked(int x, int y, int mouseButton) throws IOException {
		super.mouseClicked(x, y, mouseButton);
		int allTabsWith = 20 * tabs.size();
		int leftside = (width / 2) - allTabsWith;
		if (x > leftside && x < width / 2 + allTabsWith)
			for (ScreenTab t : tabs) {
				t.isSelected = false;
				boolean isHoovering = (x > leftside + (t.id * 40) && x < leftside + (t.id * 40) + 40);
				if (isHoovering && mouseButton == 0) {
					t.isSelected = isHoovering;
				}
			}
	}
}
