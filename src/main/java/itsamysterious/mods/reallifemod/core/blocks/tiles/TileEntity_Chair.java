package itsamysterious.mods.reallifemod.core.blocks.tiles;

import org.lwjgl.input.Keyboard;

import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntity_Chair extends RLMTileEntity implements IUpdatePlayerListBox {

	private int entityID;

	@Override
	public void update() {
		if (!this.isEmpty()) {
			getSittingEntity().setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
				standUp();
			}
		}
	}

	public void sitDown(EntityPlayer p) {
		this.standUp();
		this.entityID = p.getEntityId();
	}

	private void standUp() {
		if (!this.isEmpty()) {
			getSittingEntity().setPosition(pos.getX() - 0.5, pos.getY(), pos.getZ());
			this.entityID = 0;
		}
	}

	public boolean isEmpty() {
		return getSittingEntity() == null;
		
	}

	private EntityPlayer getSittingEntity() {
		EntityPlayer entity = (EntityPlayer) worldObj.getEntityByID(entityID);
		return entity;
	}
}
