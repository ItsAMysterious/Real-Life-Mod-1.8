package itsamysterious.mods.reallifemod.core.gui.phone;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class IApp extends GuiScreen {
	public String name;
	public int ID, offsetX, offsetY;
	public GuiPhone parent;

	public IApp(String name) {
		this.name = name;
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		super.drawScreen(x, y, partialTicks);
		if (this.parent != null) {
			this.parent.drawCase(x, y, partialTicks);
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			Minecraft.getMinecraft().displayGuiScreen(parent);
		}
	}

}
