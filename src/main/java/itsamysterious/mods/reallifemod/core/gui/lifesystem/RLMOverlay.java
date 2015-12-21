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

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import itsamysterious.mods.reallifemod.core.gui.GuiGamestart;
import itsamysterious.mods.reallifemod.core.items.itemControlDevice;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.lifesystem.TemperatureHelper;
import itsamysterious.mods.reallifemod.core.vehicles.EntityDriveable;
import itsamysterious.mods.reallifemod.core.vehicles.EntityVehicle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
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
	private static final ResourceLocation rimTexture = new ResourceLocation("reallifmod:textures/gui/overlay/rim.png");
	private static final ResourceLocation bottleEmpty = new ResourceLocation(
			"reallifmod: textures/items/bottleEmpty.png");
	private static final ResourceLocation bottleFilled = new ResourceLocation(
			"reallifmod: textures/items/bottleFilled.png");
	private static final ResourceLocation energyBar = new ResourceLocation(
			"reallifmod: textures/gui/overlay/energyBar.png");
	private static final ResourceLocation tachoTexture = new ResourceLocation(
			"reallifemod: textures/gui/overlay/tacho.png");

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

		if (Minecraft.getMinecraft().currentScreen != null) {
			return;
		}
		glPushMatrix();
		if (!Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode
				|| Minecraft.getMinecraft().currentScreen instanceof GuiGamestart) {
			glEnable(GL_BLEND);
			glDepthMask(false);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			/*
			 * mc.getTextureManager().bindTexture(temperatureGuiEmpty);
			 * drawTexturedModalRect(0, 0, 0, 0, 17, 68);
			 * mc.getTextureManager().bindTexture(temperatureGui);
			 * drawTexturedModalRect(0, 64 - 64, 0, 64 - 64, 17, 68);
			 */
			glEnable(GL12.GL_RESCALE_NORMAL);
			drawBars();
			int time = ((int) Minecraft.getMinecraft().thePlayer.worldObj.getWorldTime());
			int hours = time / 1200;
			int minutes = time / 20 - hours * 60;
			int seconds = time % 20;
			// drawString(Minecraft.getMinecraft().fontRendererObj,
			// String.valueOf("Time: "+ hours+":"+minutes+":"+seconds), 15, 15,
			// Color.white.getRGB());
			if (mc.thePlayer.ridingEntity != null && mc.thePlayer.ridingEntity instanceof EntityVehicle) {
				glPushMatrix();
				glPopMatrix();
			}

			glColor3f(1.0F, 1.0F, 1.0F);
			glDisable(GL_BLEND);
			glDepthMask(true);
		}

		if (Minecraft.getMinecraft().objectMouseOver != null) {
			BlockPos pos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
			if (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null
					&& Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem()
							.getItem() instanceof itemControlDevice) {
				if (Minecraft.getMinecraft().theWorld != null && pos != null) {
					if (Minecraft.getMinecraft().theWorld.getTileEntity(pos) instanceof TileEntity_Electric) {
						TileEntity_Electric t = (TileEntity_Electric) Minecraft.getMinecraft().theWorld
								.getTileEntity(pos);
						drawString(Minecraft.getMinecraft().fontRendererObj, "Current Voltage: " + t.clientVoltage, 5,
								5, Color.white.getRGB());
						if (t.getTo() != null) {
							String s = t.getTo().getClass().getSimpleName();
							drawString(Minecraft.getMinecraft().fontRendererObj,
									"Gives energy to : " + EnumChatFormatting.GREEN + s + " at: " + t.getTo().getPos(),
									5, 15, Color.white.getRGB());
							if (!t.isPowerSource() && t.getFrom() != null) {
								String s2 = t.getFrom().getClass().getSimpleName();
								drawString(
										Minecraft.getMinecraft().fontRendererObj, "Gets energy from : "
												+ EnumChatFormatting.GREEN + s2 + " at: " + t.getFrom().getPos(),
										5, 25, Color.white.getRGB());
							}

						}
					}
				}
			}

		}
		if (Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntityDriveable) {
			EntityDriveable v = (EntityDriveable) Minecraft.getMinecraft().thePlayer.ridingEntity;
			bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/dashboard.png"));
			drawScaledCustomSizeModalRect(k - k / 5, (int) (l - k / 5), 0, 0, 512, 512, k / 5, k / 5, 512, 512);
			drawString(Minecraft.getMinecraft().fontRendererObj, String.valueOf("Driving a ") + v.getFile().vehicleName,
					5, 5, Color.orange.getRGB());
			GL11.glPushMatrix();
			drawString(Minecraft.getMinecraft().fontRendererObj,
					String.valueOf(Math.round(v.motorspeed * 100) / 100 + " km/h"), k / 2 - 25,
					(int) (l - (l / 11.5)) - 7, Color.orange.getRGB());
			GL11.glPopMatrix();
			drawString(Minecraft.getMinecraft().fontRendererObj, String.valueOf("[Max Speed]: " + v.getFile().maxSpeed),
					5, 25, Color.orange.getRGB());
			drawString(Minecraft.getMinecraft().fontRendererObj, String.valueOf("[Mass]: " + v.getFile().mass + " K"),
					5, 35, Color.orange.getRGB());
			drawString(Minecraft.getMinecraft().fontRendererObj,
					String.valueOf("[Current throttle]: " + v.throttle + " m/s²"), 5, 45, Color.white.getRGB());

			double x = Math.sin(v.motorspeed + 100 * Math.PI / 180) * 10;
			double y = Math.cos(v.motorspeed + 100 * Math.PI / 180) * 10;
			GL11.glColor3f(1.0f, 1.0f, 1.0f);

			GL11.glPushMatrix();
			GL11.glTranslated(0, l - 80, 0);
			// bindTexture(new
			// ResourceLocation("reallifemod:textures/gui/overlay/dashboard.png"));
			// drawScaledCustomSizeModalRect(0, 10, 0, 0, 256, 256, l / 4, l /
			// 4, 256, 256);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glTranslated(k - (k / 5) / 2, l - l / 5, 0);
			double scalefactor = 4.7058823529411764705882352941176D;// 1920/408/2
																	// (/2=radius)
			double scale = (((2 * Math.PI) * ((k / 5 / 2) / k)));
			GL11.glRotated(12 + scale * v.actualspeed, 0, 0, 1);
			drawRect(-3, 10, 4, 1, Color.red.getRGB());
			drawRect(-2, 20, 3, 1, Color.red.getRGB());
			drawRect(-1, 35, 2, 1, Color.red.getRGB());
			drawRect(0, 50, 1, 1, Color.red.getRGB());

			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(k / 5.2, l - l / 10, 0);
			GL11.glRotated(270 - ((270 * Math.abs(v.throttle))), 0, 0, -1);
			drawRect(0, 25, 2, 1, Color.red.getRGB());
			GL11.glPopMatrix();

			// Steeringwheel
			int steeringWheelWidth = k / 2;
			float translatedX = (float) (-(Math.cos((v.steeringAngle * 0.0001) * Math.PI / 180) * steeringWheelWidth
					/ 2));
			float translatedY = (float) (-(Math.cos((v.steeringAngle * 0.0001) * Math.PI / 180) * steeringWheelWidth
					/ 2));

			GL11.glPushMatrix();
			GL11.glScaled(0.75, 0.75, 0);
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			GL11.glTranslated(k * 9.5, l * 11 - 40, 0);
			GL11.glPushMatrix();
			GL11.glRotated((v.steeringAngle * 4), 0, 0, 1);
			GL11.glTranslated(translatedX, translatedY, 0);
			bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/steeringwheel.png"));
			drawScaledCustomSizeModalRect(0, 0, 0, 0, 256, 256, steeringWheelWidth, steeringWheelWidth, 256, 256);
			GL11.glPopMatrix();
			GL11.glPopMatrix();

		}

		if (Minecraft.getMinecraft().objectMouseOver.entityHit instanceof EntityDriveable) {
			EntityDriveable e = (EntityDriveable) Minecraft.getMinecraft().objectMouseOver.entityHit;
			drawCenteredString(Minecraft.getMinecraft().fontRendererObj, e.getFile().vehicleName, k / 2, l / 2 + 10,
					Color.orange.getRGB());
			drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Rightclick to mount!", k / 2, l / 2 + 20,
					Color.white.getRGB());

			GL11.glPushMatrix();
			// GL11.glTranslated(e.posX, e.posY, e.posZ);
			// Minecraft.getMinecraft().getRenderManager().getFontRenderer().drawString("Rightclick
			// to mount!", 0, 0, Color.white.getRGB());
			GL11.glPopMatrix();
			// drawCenteredString(Minecraft.getMinecraft().fontRendererObj,
			// "Press
			// '"+Keyboard.getKeyName(Keybindings.EnterVehicleKey.getKeyCode())+"'"+EnumChatFormatting.RESET+"to
			// mount!", k/2, l/2+20, Color.white.getRGB());
		}

		glPopMatrix();

	}

	public void drawBars() {
		if (RLMPlayerProps.get(Minecraft.getMinecraft().thePlayer) != null) {
			bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/rim.png"));
			drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 256, 256, 256, 256);

			// bindTexture(emptybar);
			// drawModalRectWithCustomSizedTexture(20, 5, 0, 0, 49, 6, 49, 6);

			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			RLMPlayerProps props = RLMPlayerProps.get(player);

			bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/poopbar_empty.png"));
			drawModalRectWithCustomSizedTexture(0, 20, 2, 0, 32, 32, 32, 32);
			/* Full poop bar */
			bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/poopbar_full.png"));
			drawModalRectWithCustomSizedTexture(0, 20, 2, 0, 32, 32, 32, 32);
			bindTexture(new ResourceLocation("reallifemod:textures/items/bottleEmpty.png"));
			drawModalRectWithCustomSizedTexture(25, 21, 0, 0, 32, 32, 32, 32);
			/* Full poop bar */
			bindTexture(new ResourceLocation("reallifemod:textures/items/bottleFilled.png"));
			drawModalRectWithCustomSizedTexture(25, 21, 0, 0, 32, 32, 32, 32);

			bindTexture(new ResourceLocation("reallifemod:textures/gui/overlay/lucky.png"));
			drawScaledCustomSizeModalRect(65, 1, 0, 0, 31, 31, 20, 20, 32, 32);
			// drawModalRectWithCustomSizedTexture(55, 0, 0, 0, 32, 32, 32, 32);

			/*
			 * int value = (int) (props.getWater() * 0.49); bindTexture(new
			 * ResourceLocation(
			 * "reallifemod:textures/gui/overlay/waterbar_full.png"));
			 * drawModalRectWithCustomSizedTexture(20, 5, 0, 0, value, 6, 49,
			 * 6);
			 */

			drawString(Minecraft.getMinecraft().fontRendererObj, String.valueOf("Cash: " + props.money + "$"), 2, 2,
					Color.white.getRGB());
			float temperature = TemperatureHelper
					.getTemperaturesForBiomes(player.worldObj.getBiomeGenForCoords(player.getPosition()), player);
			drawString(Minecraft.getMinecraft().fontRendererObj, String.valueOf("Temp: " + temperature + "°"), 2, 13,
					TemperatureHelper.getColorForTemperature(temperature));

		}
	}

	private void bindTexture(ResourceLocation loc) {
		mc.getTextureManager().bindTexture(loc);
	}

}
