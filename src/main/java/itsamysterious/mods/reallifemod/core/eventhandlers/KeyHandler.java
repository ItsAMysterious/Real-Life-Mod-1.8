package itsamysterious.mods.reallifemod.core.eventhandlers;

import org.lwjgl.input.Keyboard;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.api.IControllable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value = Side.CLIENT)
public class KeyHandler {
	public static KeyBinding EnterVehicleKey = new KeyBinding("Enter Vehicle", Keyboard.KEY_RETURN, "Real Life Mod");
	public static KeyBinding CharacterKey = new KeyBinding("Character Menu", Keyboard.KEY_C, "Real Life Mod");
	public static KeyBinding PhoneKey = new KeyBinding("Open Phone", Keyboard.KEY_UP, "Real Life Mod");
	public static KeyBinding VehicleInventory = new KeyBinding("Open Car Inventory", Keyboard.KEY_I, "Real Life Mod");
	public static KeyBinding Horn = new KeyBinding("Horn", Keyboard.KEY_H, "Real Life Mod");
	public static KeyBinding RLMMenu = new KeyBinding("Real Life Mod - Menu", Keyboard.KEY_L, "Real Life Mod");
	//Driveable keys
	public static KeyBinding downKey = new KeyBinding("RLM-Driveable downwards", Keyboard.KEY_X, "Real Life Mod");

	
	Minecraft mc;


	public KeyHandler() {
		ClientRegistry.registerKeyBinding(EnterVehicleKey);
		ClientRegistry.registerKeyBinding(PhoneKey);
		ClientRegistry.registerKeyBinding(VehicleInventory);
		ClientRegistry.registerKeyBinding(Horn);
		ClientRegistry.registerKeyBinding(RLMMenu);
		ClientRegistry.registerKeyBinding(downKey);
		mc=Minecraft.getMinecraft();
		
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void onKeyInput(KeyboardInputEvent event)
	{
		if(FMLClientHandler.instance().isGUIOpen(GuiChat.class) || mc.currentScreen != null)
			return;
		
		EntityPlayer player = mc.thePlayer;
		Entity ridingEntity = player.ridingEntity;
		
		if(ridingEntity instanceof IControllable){
			IControllable riding =(IControllable)ridingEntity;
			
			if(mc.gameSettings.keyBindForward.isKeyDown())
				riding.pressKey(0, player);
			
			if(mc.gameSettings.keyBindBack.isKeyDown())
				riding.pressKey(1, player);
			
			if(mc.gameSettings.keyBindLeft.isKeyDown())
				riding.pressKey(2, player);
			
			if(mc.gameSettings.keyBindRight.isKeyDown())
				riding.pressKey(3, player);
			
			if(mc.gameSettings.keyBindJump.isKeyDown())
				riding.pressKey(4, player);
			
			if(downKey.isPressed())
				riding.pressKey(5, player);
			
			if(mc.gameSettings.keyBindSneak.isKeyDown())
				riding.pressKey(6, player);
			
	
		}
	}
}

