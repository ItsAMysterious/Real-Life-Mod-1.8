package itsamysterious.mods.reallifemod.core.gui.lifesystem;

import java.awt.Color;
import java.io.IOException;

import itsamysterious.mods.reallifemod.core.gui.RLMCustomGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiCharacterMenu extends RLMCustomGui{

	
	public GuiCharacterMenu() {
	}
	
	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawBackground(0);
		drawVerticalLine(40, 0, height, Color.black.getRGB());
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
	}
	
}
