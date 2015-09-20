package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import itsamysterious.mods.reallifemod.core.models.ModelToilet;
import net.minecraft.tileentity.TileEntity;

public class render_Toilet extends RLMBaseRenderer{
	public render_Toilet() {
		super("sanitary/toilet.obj", "tiles/sanitary/texture_Toilet.png", new ModelToilet(), "tiles/sanitary/texture_ToiletMC.png");
	}	
	
	@Override
	public void renderTileEntityAt(TileEntity p_180535_1_, double posX,
			double posY, double posZ, float p_180535_8_, int p_180535_9_) {
		super.renderTileEntityAt(p_180535_1_, posX, posY, posZ, p_180535_8_, p_180535_9_);
	}


}
