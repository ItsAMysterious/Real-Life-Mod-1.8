package itsamysterious.mods.reallifemod.core.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityPylon extends Entity{
	public double rotationRoll;
	
	public EntityPylon(World worldIn) {
		super(worldIn);
		this.setSize(0.5f, 1);
	}
	
	public EntityPylon(World world, double x, double y, double z) {
		this(world);
		setPosition(x, y, z);
		ignoreFrustumCheck=true;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(1));
		this.dataWatcher.addObject(19, new Float(0.0F));
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		if(worldObj.getBlockState(getPosition()).getBlock()==Blocks.air){
			motionY-=9.81f/20;
			BlockPos p=new BlockPos(getPosition().getX(), getPosition().getY()+1, getPosition().getZ());
			if(worldObj.getBlockState(p).getBlock()!=Blocks.air){
				rotationPitch+=9.81*20;
			}
		}else
		{
			motionY=0;
		}
		
		motionX*=0.988881;
		motionZ*=0.988881;
		moveEntity(motionX, motionY, motionZ);
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {}
	
	public boolean canBePushed(){
		return true;
	}
	
	public boolean canBeCollidedWith() {
		return !this.isDead;
	};
	
	@Override
	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return entityIn.getEntityBoundingBox();
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return this.getEntityBoundingBox();
	}
	 @Override
	public void applyEntityCollision(Entity entityIn) {
		super.applyEntityCollision(entityIn);
		 this.addVelocity(entityIn.motionX, entityIn.motionY, entityIn.motionZ);
	}
}
