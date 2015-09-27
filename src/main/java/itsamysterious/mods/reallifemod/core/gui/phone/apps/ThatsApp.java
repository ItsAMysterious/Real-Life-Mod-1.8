package itsamysterious.mods.reallifemod.core.gui.phone.apps;

import java.io.IOException;

import itsamysterious.mods.reallifemod.core.gui.phone.IApp;
import net.minecraft.client.gui.GuiButton;

public class ThatsApp extends IApp {

	public ThatsApp() {
		super("Browser");
	}

	public ThatsApp(int xOffset, int yOffset) {
		this();
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButton(0, this.offsetX+19, this.offsetY+48,70,20, "Send Message"));
	}
	
	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		super.drawScreen(x, y, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
	}
	
	
}
