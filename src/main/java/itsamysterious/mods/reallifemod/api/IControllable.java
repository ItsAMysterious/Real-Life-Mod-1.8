package itsamysterious.mods.reallifemod.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public interface IControllable {
	public void onMouseMoved(int deltaX, int deltaY);

	/**
	 * 
	 * @param key
	 *            the keycode of the key. see @link:KeyInputHandler
	 * @return boolean to indicate it this key was handled.
	 */
	public boolean pressKey(int key, EntityPlayer player);
	
	public void updateKeyHeldState(int key, boolean held);
	
	public Entity getControllingEntity();
	
	public boolean isDead();
	
	/** @return The player's view roll */
	public float getPlayerRoll();
	public float getPrevPlayerRoll();

}
