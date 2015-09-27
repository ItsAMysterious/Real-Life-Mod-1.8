package itsamysterious.mods.reallifemod.core.handlers;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keybindings {
	public static final KeyBinding EnterVehicleKey = new KeyBinding("Enter Vehicle", Keyboard.KEY_RETURN, "key.categories.reallifemod");
	public static final KeyBinding CharacterKey = new KeyBinding("Character Menu", Keyboard.KEY_C, "key.categories.reallifemod");
	public static final KeyBinding PhoneKey = new KeyBinding("Open Phone", Keyboard.KEY_UP, "key.categories.reallifemod");

	public void init(){
		ClientRegistry.registerKeyBinding(CharacterKey);
		ClientRegistry.registerKeyBinding(EnterVehicleKey);
		ClientRegistry.registerKeyBinding(PhoneKey);
	}
}
