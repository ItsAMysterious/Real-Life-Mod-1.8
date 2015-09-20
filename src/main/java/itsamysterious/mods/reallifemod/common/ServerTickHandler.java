package itsamysterious.mods.reallifemod.common;

import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ServerTickHandler {
	 @SubscribeEvent
	 public void onPlayerTick(TickEvent.PlayerTickEvent event) {
	}
	 
	 @SubscribeEvent
	 public void onServerTick(TickEvent.ServerTickEvent event) {
	 }
	 
	 @SubscribeEvent
	 public void onWorldTick(TickEvent.WorldTickEvent event) {

	}
}
