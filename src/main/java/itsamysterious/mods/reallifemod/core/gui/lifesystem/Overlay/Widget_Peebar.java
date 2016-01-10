package itsamysterious.mods.reallifemod.core.gui.lifesystem.Overlay;

import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class Widget_Peebar extends GuiWidget {

	public Widget_Peebar(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void drawWidget(int x, int y, float partialTicks) {
		super.drawWidget(x, y, partialTicks);
		double scale_pee = 32 / 100;

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/peedrop_Empty.png"));
		drawModalRectWithCustomSizedTexture(positionX, positionY, 0, 0, width, height, 32, 32);
		//drawModalRectWithCustomSizedTexture(0, 20, 2, 0, 32, 32, 32, 32);

		
		bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/peedrop_Full.png"));
		//drawModalRectWithCustomSizedTexture(positionX, (positionY+height) - (int) ((scale_pee * props.pee_value) % 32), 0,
//				32 - (int) (scale_pee * props.pee_value % 32), 32, (int) (scale_pee * props.pee_value % 32), 32, 32);
		drawModalRectWithCustomSizedTexture(positionX, positionY, 0, 0, 32, 32, 32, 32);
		//drawModalRectWithCustomSizedTexture(-2, 52 - (int) ((scalePoop * props.poop_value) % 32), 0,
				//32 - (int) (scalePoop * props.poop_value % 32), 32, (int) (scalePoop * props.poop_value % 32), 32, 32);
	}
}
