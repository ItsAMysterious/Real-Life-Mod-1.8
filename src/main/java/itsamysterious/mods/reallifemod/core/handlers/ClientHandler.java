package itsamysterious.mods.reallifemod.core.handlers;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.gui.lifesystem.GuiMarriageProposal;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ClientHandler {

	public boolean openMainMenu = true;
	private boolean addedLayer;

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e) {
		if (e.gui instanceof GuiMainMenu) {
		}
	}

	@SubscribeEvent
	public void onDisplaygui(GuiScreenEvent.DrawScreenEvent e) {
	}
	
	@SubscribeEvent
	public void onKeyPressed(InputEvent e) {
		if (Keybindings.CharacterKey.isPressed()) {
			System.out.println("Test");
		}

	}

	@SubscribeEvent
	public void onInteract(EntityInteractEvent e) {
		if (e.target instanceof EntityPlayer) {
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiMarriageProposal((EntityPlayer) e.target));
		}

	}
	
	@SubscribeEvent
	public void setupCamera(EntityViewRenderEvent.CameraSetup e){
		GL11.glScaled(1, 1, 1);
	}

	@SubscribeEvent
	public void onBlockHighlight(DrawBlockHighlightEvent e) {
		GL11.glPushMatrix();
		if (e.player.getCurrentEquippedItem() != null) {
			Item i = e.player.getCurrentEquippedItem().getItem();
			if (Block.getBlockFromItem(i) instanceof RLMBlockContainer && e.target.getBlockPos() != null) {
				if (e.player.worldObj.getBlockState(e.target.getBlockPos()).getBlock() != Blocks.air) {
					if (TileEntityRendererDispatcher.instance != null) {
						RLMBlockContainer b = (RLMBlockContainer) Block.getBlockFromItem(i);
						if (b != null) {
							BlockPos p = e.target.getBlockPos();
							GL11.glTranslated(p.getX(), p.getY(), p.getZ());
							// if
							// (b.createNewTileEntity(Minecraft.getMinecraft().theWorld,
							// 0) != null);
							// TileEntityRendererDispatcher.instance.renderTileEntity(b.createNewTileEntity(Minecraft.getMinecraft().theWorld,
							// 0),0.0f,1);
						}
					}
				}
			}
		}
		GL11.glPopMatrix();
	}

	public void onRenderPlayer(RenderPlayerEvent e) {
		final ModelPlayer clothesmodel = new ModelPlayer(1, false);
		if (addedLayer = false) {
			e.renderer.addLayer(new LayerRenderer() {

				@Override
				public boolean shouldCombineTextures() {
					return true;
				}

				@Override
				public void doRenderLayer(EntityLivingBase e, float x, float y, float z, float p_177141_5_,
						float p_177141_6_, float p_177141_7_, float p_177141_8_) {
					clothesmodel.render(e, x, y, z, p_177141_5_, p_177141_6_, p_177141_7_);

				}
			});
			addedLayer = true;
		}
	}

}
