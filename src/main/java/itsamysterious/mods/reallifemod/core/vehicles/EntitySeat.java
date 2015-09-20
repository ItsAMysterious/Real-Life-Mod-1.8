package itsamysterious.mods.reallifemod.core.vehicles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntitySeat extends Entity {
	private EntityVehicle parent;
	public EntitySeat(World worldIn) {
		super(worldIn);
	}
	
	public EntitySeat(World worldIn, double x, double y, double z) {
		this(worldIn);
		setPosition(x, y, z);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(1));
		this.dataWatcher.addObject(19, new Float(0.0F));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readFromNBT(tagCompund);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {

	}

	public void onUpdate() {
		super.onUpdate();
	}

	 @Override
		public boolean interactFirst(EntityPlayer player)
	    {
	        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != player)
	        {
	            return true;
	        }
	        else
	        {
	            if (!this.worldObj.isRemote)
	            {
	            	player.mountEntity(this);
	            }
	            return true;
	        }
	    }
	 
	@Override
	public void updateRiderPosition() {
		if (riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
			double rad = Math.toRadians(rotationYaw);
			double newX = posX;
			double newY = posY;
			double newZ = posZ;
			riddenByEntity.setPosition(newX, newY, newZ);
		}
	};

}
