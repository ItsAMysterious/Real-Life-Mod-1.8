package itsamysterious.mods.reallifemod.core.blocks.tiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.IChatComponent;

public class TileEntity_Table extends RLMTileEntity implements IInventory {
	private ItemStack[] tableInventory = new ItemStack[3];

	public TileEntity_Table() {
		this.tableInventory = new ItemStack[3];
	}

	@Override
	public String getName() {
		return "TableInventory";
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return tableInventory[index % 3];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
		stack.stackSize -= count % stack.stackSize;
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		return tableInventory[index % 3];
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.tableInventory[index % 3] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistance(pos.getX(), pos.getY(), pos.getZ()) < 5;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 3;
	}

	@Override
	public void clear() {
		tableInventory = new ItemStack[3];
	}

	@Override
	public void writeToNBT(NBTTagCompound parCompound) {
		super.writeToNBT(parCompound);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("Rotation", rotation);
		parCompound.setTag("RLMTileTag", tag);

		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < tableInventory.length ; i++) {
			if (tableInventory[i] != null) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte) i);
				tableInventory[i].writeToNBT(compound);
				itemList.appendTag(compound);
			}
		}
		tag.setTag("tableitems", itemList);

	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagCompound tag = compound.getCompoundTag("RLMTileTag");
		rotation = tag.getFloat("Rotation");

		NBTTagList itemlist = tag.getTagList("tableitems", 10);
		for (int i = 0; i < itemlist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = itemlist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;
			if (j >= 0 && j < this.tableInventory.length ) {
				this.tableInventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}

		}
	}

	@Override
	public Packet getDescriptionPacket() {

		// RLMTag
		NBTTagCompound RLMTag = new NBTTagCompound();
		writeToNBT(RLMTag);

		NBTTagList itemList = new NBTTagList();
		NBTTagCompound parCompound = new NBTTagCompound();
		parCompound.setTag("tableitems", itemList);

		for (int i = 0; i < tableInventory.length ; i++) {
			if (tableInventory[i] != null) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte) i);
				tableInventory[i].writeToNBT(compound);
				itemList.appendTag(compound);
			}
		}

		int metadata = getBlockMetadata();
		return new S35PacketUpdateTileEntity(this.pos, metadata, RLMTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	public boolean placeItemOnTable(ItemStack newItem) {
		for (int i = 0; i < tableInventory.length ; i++) {
			if (tableInventory[i] != null) {
				if (tableInventory[i].getItem() == newItem.getItem()) {
					tableInventory[i].stackSize += newItem.stackSize;
					ItemStack rest = newItem;

					if (tableInventory[i].stackSize > 64) {
						rest.stackSize = tableInventory[i].stackSize - (64 * (tableInventory[i].stackSize / 64));
					}

					EntityItem previousItem = new EntityItem(worldObj, pos.getX(), pos.getY(), pos.getZ(), rest);
					previousItem.setNoPickupDelay();
					if (!worldObj.isRemote)
						worldObj.spawnEntityInWorld(previousItem);
					return true;
				}
			} else {
				tableInventory[i] = newItem;
				return true;
			}
		}
		return false;
	}

	public void removeItemFromTable() {
		int lastFilledSlot = 0;
		for (int i = 0; i < tableInventory.length; i++) {
			if (tableInventory[i] != null) {
				lastFilledSlot = i;
			}
		}

		if (lastFilledSlot > -1 && tableInventory[lastFilledSlot] != null) {
			EntityItem previousItem = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY(), pos.getZ(),
					tableInventory[lastFilledSlot]);
			previousItem.setNoPickupDelay();
			if (!worldObj.isRemote)
				worldObj.spawnEntityInWorld(previousItem);
		}
		tableInventory[lastFilledSlot] = null;

	}

	public List<ItemStack> getContents() {
		List<ItemStack> contents=new ArrayList<ItemStack>();
		for(ItemStack stack:tableInventory){
			contents.add(stack);
		}
		return contents;
	}

	public void dropContents() {
		for(ItemStack stack:tableInventory){
			EntityItem previousItem = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY(), pos.getZ(),
					stack);
			previousItem.setNoPickupDelay();
			if (!worldObj.isRemote)
				worldObj.spawnEntityInWorld(previousItem);
		}
	}

}
