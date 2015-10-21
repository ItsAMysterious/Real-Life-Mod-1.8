package itsamysterious.mods.reallifemod.core.gui;

import itsamysterious.mods.reallifemod.RealLifeMod;
import net.minecraft.client.gui.GuiScreen;

public class RLMCustomGui extends GuiScreen {
	public static int ID;

	public RLMCustomGui() {
		int[] ids = RealLifeMod.GUIIDs;
		this.ID = RealLifeMod.GUIIDs[ids.length] + 1;
		RealLifeMod.GUIIDs = new int[ids.length + 1];
		for (int i = 0; i < ids.length; i++) {
			RealLifeMod.GUIIDs[i] = ids[i];
		}
		RealLifeMod.GUIIDs[ids.length + 1] = this.ID;

	}
}
