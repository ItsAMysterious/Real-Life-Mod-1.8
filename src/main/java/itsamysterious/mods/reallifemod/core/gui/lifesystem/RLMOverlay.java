package itsamysterious.mods.reallifemod.core.gui.lifesystem;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL12;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import itsamysterious.mods.reallifemod.core.gui.GuiGamestart;
import itsamysterious.mods.reallifemod.core.handlers.Keybindings;
import itsamysterious.mods.reallifemod.core.items.itemControlDevice;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.utils.MathUtils;
import itsamysterious.mods.reallifemod.core.vehicles.EntityVehicle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RLMOverlay extends Gui {
	// Constants
	private static final ResourceLocation temperatureGuiEmpty = new ResourceLocation(
			"reallifemod:textures/gui/overlay/guiThermometer_Empty.png");
	private static final ResourceLocation temperatureGui = new ResourceLocation(
			"reallifemod:textures/gui/overlay/guiThermometer_Full.png");
	private static final ResourceLocation emptybar = new ResourceLocation(
			"reallifemod:textures/gui/overlay/bar_empty.png");

	protected Minecraft mc;
	public static float voltage;

	public RLMOverlay(Minecraft mc) {
		this.mc = mc;
	}

	@SubscribeEvent
	public void showOverlay(RenderGameOverlayEvent event) {
		ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int k = scaledresolution.getScaledWidth();
		int l = scaledresolution.getScaledHeight();
		if (event.type != ElementType.ALL) {
			return;
		}
		glPushMatrix();
		if (!Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode||Minecraft.getMinecraft().currentScreen instanceof GuiGamestart) {
			glEnable(GL_BLEND);
			glDepthMask(false);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			mc.getTextureManager().bindTexture(temperatureGuiEmpty);
			drawTexturedModalRect(0, 0, 0, 0, 17, 68);
			mc.getTextureManager().bindTexture(temperatureGui);
			drawTexturedModalRect(0, 64 - 64, 0, 64 - 64, 17, 68);
			glEnable(GL12.GL_RESCALE_NORMAL);
			drawBars();
			int time = ((int)  Minecraft.getMinecraft().thePlayer.worldObj.getWorldTime());
			int hours = time/1200;
			int minutes = time/20-hours*60;
			int seconds = time%20;
		  //  drawString(Minecraft.getMinecraft().fontRendererObj, String.valueOf("Time: "+ hours+":"+minutes+":"+seconds), 15, 15, Color.white.getRGB());
			if (mc.thePlayer.ridingEntity != null && mc.thePlayer.ridingEntity instanceof EntityVehicle) {
				glPushMatrix();
				glPopMatrix();
			}

			glColor3f(1.0F, 1.0F, 1.0F);
			glDisable(GL_BLEND);
			glDepthMask(true);
		}
		
		if(Minecraft.getMinecraft().objectMouseOver!=null){
		BlockPos pos=Minecraft.getMinecraft().objectMouseOver.getBlockPos();
		if(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem()!=null&&
				Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem()instanceof itemControlDevice){
		if(Minecraft.getMinecraft().theWorld!=null&&pos!=null){
			if(Minecraft.getMinecraft().theWorld.getTileEntity(pos) instanceof TileEntity_Electric){
				TileEntity_Electric t = (TileEntity_Electric) Minecraft.getMinecraft().theWorld.getTileEntity(pos);
				drawString(Minecraft.getMinecraft().fontRendererObj, "Current Voltage: "+t.clientVoltage, 5, 5, Color.white.getRGB());
				if(t.getTo()!=null){
					String s=t.getTo().getClass().getSimpleName();
					drawString(Minecraft.getMinecraft().fontRendererObj, "Gives energy to : "+EnumChatFormatting.GREEN+s+ " at: "+ t.getTo().getPos(), 5, 15, Color.white.getRGB());
					if(!t.isPowerSource()&&t.getFrom()!=null){
						String s2=t.getFrom().getClass().getSimpleName();
						drawString(Minecraft.getMinecraft().fontRendererObj, "Gets energy from : "+EnumChatFormatting.GREEN+s2+ " at: "+ t.getFrom().getPos(), 5, 25, Color.white.getRGB());
					}

				}
			}
		}
		}

		}
		if(Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntityVehicle){
			EntityVehicle v= (EntityVehicle)Minecraft.getMinecraft().thePlayer.ridingEntity;
			drawString(Minecraft.getMinecraft().fontRendererObj, String.valueOf("Speed:")+Math.round(v.speed*100)/100, 0, 0, Color.white.getRGB());
		}
		
		if(Minecraft.getMinecraft().objectMouseOver.entityHit instanceof EntityVehicle){
			EntityVehicle e=(EntityVehicle) Minecraft.getMinecraft().objectMouseOver.entityHit;
			drawCenteredString(Minecraft.getMinecraft().fontRendererObj, e.getFile().vehicleName, 0, 0, Color.white.getRGB());
			drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Press '"+Keyboard.getKeyName(Keybindings.EnterVehicleKey.getKeyCode())+"'"+EnumChatFormatting.RESET+"to mount!", 0, 0, Color.white.getRGB());
		}
		
		
		glPopMatrix();

	}
	

	public void drawBars() {
		if (RLMPlayerProps.get(Minecraft.getMinecraft().thePlayer) != null) {	
			bindTexture(emptybar);
			drawModalRectWithCustomSizedTexture(20, 5, 0, 0, 49, 6, 49, 6);
			bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/poopbar_empty.png"));
			drawModalRectWithCustomSizedTexture(70, -5, 0, 0, 32, 32, 32, 32);
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			RLMPlayerProps props = RLMPlayerProps.get(player);
			int value = (int) (props.getWater()*0.49);
			bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/waterbar_full.png"));
			drawModalRectWithCustomSizedTexture(20, 5, 0, 0, value, 6, 49, 6);
			bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/poopbar_full.png"));
			drawModalRectWithCustomSizedTexture(70, -5, 0,0, 32, 32, 32, 32);
			
		    drawString(Minecraft.getMinecraft().fontRendererObj, String.valueOf("Cash: "+ props.money+"$"), 20, 15, Color.white.getRGB());

		}
	}
	

	private void bindTexture(ResourceLocation loc) {
		mc.getTextureManager().bindTexture(loc);
	}
	
}
