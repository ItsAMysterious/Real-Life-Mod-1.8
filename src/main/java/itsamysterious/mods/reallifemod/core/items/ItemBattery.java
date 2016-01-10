package itsamysterious.mods.reallifemod.core.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBattery extends Item{
	private float voltage;
	
	public ItemBattery() {
		this.setMaxStackSize(1);
		this.voltage=100;
		setUnlocalizedName("item_Battery");
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));
		
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		if(!stack.hasTagCompound()){
			NBTTagCompound batteryTag = new NBTTagCompound();
			batteryTag.setFloat("Voltage", voltage);
			stack.writeToNBT(batteryTag);
		}else
		{
			NBTTagCompound batteryTag = stack.getTagCompound();
			batteryTag.setFloat("Voltage", voltage);
			stack.writeToNBT(batteryTag);
		}
	}
	
    public boolean updateItemStackNBT(NBTTagCompound nbt)
    {
    	NBTTagCompound batteryTag = nbt.getCompoundTag("BatteryTag");
    	this.voltage=batteryTag.getFloat("Voltage");
        return true;
    }

}
