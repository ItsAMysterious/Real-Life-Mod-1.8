package itsamysterious.mods.reallifemod.core.handlers;

import java.util.List;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.gui.GuiModInit;
import itsamysterious.mods.reallifemod.core.gui.lifesystem.GuiCharacterMenu;
import itsamysterious.mods.reallifemod.core.gui.phone.GuiPhone;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.packets.MountVehicleMessage;
import itsamysterious.mods.reallifemod.core.vehicles.EntityVehicle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonHandler {
	private int tickrun = 0;
	private boolean countticks = true;

	@SubscribeEvent
	public void updateRealLifeProps(PlayerTickEvent event) {
		tickrun++;
		if (RLMPlayerProps.get(event.player) != null) {
			if (event.player.worldObj.isRemote && event.phase == event.phase.END) {
				RLMPlayerProps.get(event.player).circleOfLife();
				if (RLMPlayerProps.get(event.player).getName() == null) {
					BlockPos p = event.player.getPosition();
					event.player.openGui(RealLifeMod.instance, GuiModInit.ID, event.player.worldObj, 0, 0, 0);
				} else {
					if (RLMPlayerProps.get(event.player).getName().isEmpty()) {
						BlockPos p = event.player.getPosition();
						event.player.openGui(RealLifeMod.instance, GuiModInit.ID, event.player.worldObj, p.getX(),
								p.getY(), p.getZ());
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			if (RLMPlayerProps.get((EntityPlayer) event.entity) == null) {
				RLMPlayerProps.register((EntityPlayer) event.entity);
			}
		}
	}

	/*
	 * @SubscribeEvent public void onClonePlayer(PlayerEvent.Clone event) {
	 * NBTTagCompound compound = new NBTTagCompound();
	 * RLMPlayerProps.get(event.original).saveNBTData(compound);
	 * RLMPlayerProps.get(event.entityPlayer).loadNBTData(compound); }
	 */

	@SubscribeEvent
	public void showTheGui(PlayerLoggedInEvent event) {
		/*
		 * if (RLMPlayerProps.get(event.player) != null) { RLMPlayerProps props
		 * = RLMPlayerProps.get(event.player); if (props.shownGui = true) { if
		 * (props.getName() == null || props.getSurname() == null ||
		 * props.getGender() == null) { BlockPos p = event.player.getPosition();
		 * event.player.openGui(RealLifeMod.instance, GuiModInit.ID,
		 * event.player.worldObj, p.getX(), p.getY(), p.getZ()); } } }
		 */
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		RLMPlayerProps.get(event.entityPlayer).copy(RLMPlayerProps.get(event.original));
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyPressed(ClientTickEvent e) {
		if (e.phase==e.phase.END) {
			if (Keybindings.CharacterKey.isKeyDown()) {
				System.out.println("Testen");
			}else
			if (Keybindings.EnterVehicleKey.isPressed()) {
				if (getClosestEntity() != null) {
					EntityVehicle v = getClosestEntity();
					RealLifeMod.network.sendToServer(new MountVehicleMessage(v.getEntityId()));
				}
			}
			
			if(Keybindings.PhoneKey.isPressed()){
				Minecraft.getMinecraft().displayGuiScreen(new GuiPhone());
			}

		}

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
