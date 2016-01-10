package itsamysterious.mods.reallifemod.core.eventhandlers;

import java.util.List;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.api.IControllable;
import itsamysterious.mods.reallifemod.core.gui.GuiModInit;
import itsamysterious.mods.reallifemod.core.gui.RLMMenu;
import itsamysterious.mods.reallifemod.core.gui.phone.GuiPhone;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.vehicles.EntityVehicle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonHandler {
	private int tickrun = 0;

	public CommonHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		tickrun = 0;
	}

	@SubscribeEvent
	public void updateRealLifeProps(PlayerTickEvent event) {
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			RLMPlayerProps.register((EntityPlayer) event.entity);
		}

	}

	@SubscribeEvent
	public void showTheGui(PlayerLoggedInEvent event) {
		System.out.println("PlayerLoggedInEvent has been called - the RLMPlayerProps are"
				+ (RLMPlayerProps.get(event.player) == null ? "null" : "not null"));
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		System.out.println("Cloning player");
		EntityPlayer old = event.original;
		EntityPlayer player = event.entityPlayer;
		RLMPlayerProps.get(player).copy(old);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyPressed(ClientTickEvent e) {
		if (e.phase == e.phase.END) {
			if (KeyHandler.CharacterKey.isKeyDown()) {
			} else if (KeyHandler.EnterVehicleKey.isPressed()) {
				if (getClosestEntity() != null) {
					EntityVehicle v = getClosestEntity();
				}
			}

			if (KeyHandler.PhoneKey.isPressed()) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiPhone());
			}

			if (KeyHandler.RLMMenu.isPressed()) {
				Minecraft.getMinecraft().displayGuiScreen(new RLMMenu());
			}

			Minecraft mc = Minecraft.getMinecraft();
			if (FMLClientHandler.instance().isGUIOpen(GuiChat.class) || mc.currentScreen != null)
				return;

			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			Entity ridingEntity = player.ridingEntity;

			if (ridingEntity instanceof IControllable) {
				IControllable riding = (IControllable) ridingEntity;

				if (mc.gameSettings.keyBindForward.isKeyDown())
					riding.pressKey(0, player);

				if (mc.gameSettings.keyBindBack.isKeyDown())
					riding.pressKey(1, player);

				if (mc.gameSettings.keyBindLeft.isKeyDown())
					riding.pressKey(2, player);

				if (mc.gameSettings.keyBindRight.isKeyDown())
					riding.pressKey(3, player);

				if (mc.gameSettings.keyBindJump.isKeyDown())
					riding.pressKey(4, player);

				if (KeyHandler.downKey.isPressed())
					riding.pressKey(5, player);

				if (mc.gameSettings.keyBindSneak.isKeyDown())
					riding.pressKey(6, player);
			}

		}

	}

	@SubscribeEvent
	public void tick(TickEvent event) {
		switch (event.phase) {
		case START: {
			// Handle all packets received since last tick
			// RealLifeMod.network.handleServerPackets();
			break;
		}
		case END: {
			break;
		}
		}
		ClientHandler.roll += 0.025;
	}

	@SideOnly(Side.CLIENT)
	private final EntityVehicle getClosestEntity() {
		World world = FMLClientHandler.instance().getServer().getEntityWorld();
		List<Entity> entities = world.getLoadedEntityList();
		EntityVehicle i = null;
		EntityPlayer p = FMLClientHandler.instance().getClientPlayerEntity();
		for (Entity e : entities) {
			if (e instanceof EntityVehicle) {
				if (i == null) {
					i = (EntityVehicle) e;
				} else if (p.getDistanceSqToEntity(e) < p.getDistanceSqToEntity(i)) {
					i = (EntityVehicle) e;
				}
			}
		}
		return i;
	}

}
