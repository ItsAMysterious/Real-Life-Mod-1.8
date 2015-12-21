package itsamysterious.mods.reallifemod.core.gui.phone.apps;

import java.io.IOException;

import itsamysterious.mods.reallifemod.core.gui.phone.IApp;
import net.minecraft.client.gui.GuiButton;

public class Phone extends IApp {
	private String currentnumber;
	
	public Phone() {
		super("Phone");
	}

	@Override
	public void initGui() {
		super.initGui();
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				buttonList.add(new GuiButton(x*y, this.offsetX + 18 + x * 25, this.offsetY + 105 + y * 20, 25, 20,
						String.valueOf(y*3+x)));
			}

		}
	}
	
	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		super.drawScreen(x, y, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if(button.id<=9){
			currentnumber=currentnumber+button.displayString;
		}
	}

}
