package itsamysterious.mods.reallifemod.core.gui.lifesystem.Overlay;

import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class Widget_PoopBar extends GuiWidget {

	public Widget_PoopBar(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void drawWidget(int x, int y, float partialTicks) {
		super.drawWidget(x, y, partialTicks);
		double scalePoop = 32 / 100D;

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		RLMPlayerProps props = RLMPlayerProps.get(player);
		
		bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/poopbar_empty.png"));
		drawModalRectWithCustomSizedTexture(positionX, positionY, 2, 0, width, height, 32, 32);
		//drawModalRectWithCustomSizedTexture(0, 20, 2, 0, 32, 32, 32, 32);

		
		bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/poopbar_full.png"));
		drawModalRectWithCustomSizedTexture(positionX-2, (positionY+height) - (int) ((scalePoop * props.poop_value) % 32), 0,
				32 - (int) (scalePoop * props.poop_value % 32), 32, (int) (scalePoop * props.poop_value % 32), 32, 32);
		//drawModalRectWithCustomSizedTexture(-2, 52 - (int) ((scalePoop * props.poop_value) % 32), 0,
				//32 - (int) (scalePoop * props.poop_value % 32), 32, (int) (scalePoop * props.poop_value % 32), 32, 32);
	}
}
