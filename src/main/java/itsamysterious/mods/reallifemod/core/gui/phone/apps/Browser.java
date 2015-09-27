package itsamysterious.mods.reallifemod.core.gui.phone.apps;

import java.io.IOException;

import itsamysterious.mods.reallifemod.core.gui.phone.IApp;
import net.minecraft.client.gui.GuiButton;

public class Browser extends IApp {

	public Browser() {
		super("Browser");
	}

	public Browser(int xOffset, int yOffset) {
		this();
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButton(0, this.offsetX+19, this.offsetY+48,70,20, "YouTube"));
		buttonList.add(new GuiButton(1,  this.offsetX+19, this.offsetY+68,70,20, "Website"));
		buttonList.add(new GuiButton(2,  this.offsetX+19, this.offsetY+88,70,20, "Twitter"));
		buttonList.add(new GuiButton(3,  this.offsetX+19,  this.offsetY+108,70,20, "Forum"));
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
