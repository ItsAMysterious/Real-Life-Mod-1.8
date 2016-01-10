package itsamysterious.mods.reallifemod.core.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityPeeDrop extends Entity {

	public EntityPeeDrop(World worldIn) {
		super(worldIn);
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

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!this.onGround) {
			motionY += 0.981;
		}
	}
}
