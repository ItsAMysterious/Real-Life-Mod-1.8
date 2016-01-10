package itsamysterious.mods.reallifemod.core.entities;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.packets.DefecatePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntitySit extends Entity {

	public EntitySit(World worldIn) {
		super(worldIn);
		setSize(1F, 1F);
	}

	public EntitySit(World worldIn, BlockPos pos) {
		this(worldIn);
		posX = pos.getX() + 0.5;
		posY = pos.getY() - 1;
		posZ = pos.getZ() + 0.5;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!worldObj.isRemote) {
			if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer) {
				RLMPlayerProps props = RLMPlayerProps.get((EntityPlayer) riddenByEntity);
				if (props.pee_value <= 0) {
					if (props.poop_value > 0) {
						RealLifeMod.network.sendToServer(new DefecatePacket(false, true));
					} else {
						RealLifeMod.network.sendToServer(new DefecatePacket(false, false));
					}
				}

				if (props.poop_value <= 0) {
					if (props.pee_value > 0) {
						RealLifeMod.network.sendToServer(new DefecatePacket(true, false));
					} else {

						RealLifeMod.network.sendToServer(new DefecatePacket(false, false));
					}
				}
			}
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
	}

	public boolean interactFirst(EntityPlayer playerIn) {
		this.riddenByEntity = playerIn;
		return true;
	}

}
