package com.cfyifei.gui.tileentitys;


import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cfyifei.gui.blocks.BlockGuo;
import com.cfyifei.gui.blocks.ModGui;
import com.cfyifei.gui.recipes.Guorecipe;
import com.cfyifei.item.ModItem;


public class TileEntityGuo extends TileEntity implements IInventory{
	private ItemStack stack[] = new ItemStack[13];
	public int tableBurnTime = 0;
    public int currentItemBurnTime;
    public int furnaceCookTime;
	private String field_145958_o;
	public boolean isfire;
	public int cai;

	@Override
    public void updateEntity() {

	        isfire = BlockGuo.update(this.worldObj ,this.xCoord, this.yCoord, this.zCoord);
	        

	        if (this.tableBurnTime > 0)
	        {
	            --this.tableBurnTime;
	            
	        }
	        
	        if (!this.worldObj.isRemote)
	        {
	        	cai = this.canchao();
	            if (isfire && cai != 0)
	            {
	                ++this.furnaceCookTime;

	    
	                if (this.furnaceCookTime == 500)
	                {
	                    this.furnaceCookTime = 0;
	                    this.smeltItem();
	                }
	            }
	            else
	            {
	                this.furnaceCookTime = 0;
	            }
	          }
	}
		 

	@Override
	public int getSizeInventory() {
		// TODO �Զ����ɵķ������
		return stack.length;

	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		// TODO �Զ����ɵķ������
		return stack[var1];
	}

	@Override
    public ItemStack decrStackSize(int par1, int par2) {
            // TODO Auto-generated method stub
            if (this.stack[par1] != null)
    {
        ItemStack var3;

        if (this.stack[par1].stackSize <= par2)
        {
            var3 = this.stack[par1];
            this.stack[par1] = null;
            return var3;
        }
        else
        {
            var3 = this.stack[par1].splitStack(par2);

            if (this.stack[par1].stackSize == 0)
            {
                this.stack[par1] = null;
            }

            return var3;
        }
    }
    else
    {
        return null;
    }
    }

	@Override
	 public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.stack[par1] != null)
        {
            ItemStack itemstack = this.stack[par1];
            this.stack[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
        this.stack[var1] = var2;
        if (var2 != null && var2.stackSize > this.getInventoryStackLimit())
        {
                var2.stackSize = this.getInventoryStackLimit();
        }
	}

	@Override
	public String getInventoryName() {
		// TODO �Զ����ɵķ������
		return "Guo";
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO �Զ����ɵķ������
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO �Զ����ɵķ������
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public void openInventory() {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void closeInventory() {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		// TODO �Զ����ɵķ������
		return false;
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("ItemsGuo", 10);
        this.stack = new ItemStack[this.getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.stack.length)
            {
                this.stack[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        this.tableBurnTime = par1NBTTagCompound.getShort("tableBurnTime");
        this.furnaceCookTime = par1NBTTagCompound.getShort("furnaceCookTime");
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("tableBurnTime", (short)this.tableBurnTime);
        par1NBTTagCompound.setShort("furnaceCookTime", (short)this.furnaceCookTime);
        NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.stack.length; ++var3)
        {
            if (this.stack[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.stack[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("ItemsGuo", var2);
       
    }
    

    
    private boolean canSmelt()
    {
        if (this.stack[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = Guorecipe.smelting().getSmeltingResult(this.stack[0]);
            if (itemstack == null) return false;
            if (this.stack[1] == null) return true;
            if (!this.stack[1].isItemEqual(itemstack)) return false;
            int result = stack[1].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.stack[1].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }
    public boolean isBurning()
    {
        return this.tableBurnTime > 0;
    }
   
    public void smeltItem()
    {

           // ItemStack itemstack = Guorecipe.smelting().getSmeltingResult(this.stack[0]);
    	ItemStack itemstack =new ItemStack(ModItem.ItemFanqiechaojidanfan,1);
    	ItemStack itemstack2 =new ItemStack(ModItem.ItemDisanxian,1);
    	ItemStack itemstack3 =new ItemStack(ModItem.ItemYuxiangrousi,1);
    	ItemStack itemstack4 =new ItemStack(ModItem.ItemGongbaojiding,1);
            if (this.stack[12] == null)
            {
                if(cai == 1) this.stack[12] = itemstack.copy();
                if(cai == 2) this.stack[12] = itemstack2.copy();
                if(cai == 3) this.stack[12] = itemstack3.copy();
                if(cai == 4) this.stack[12] = itemstack4.copy();
            }
            else if (this.stack[12].getItem() == itemstack.getItem() || this.stack[12].getItem() == itemstack2.getItem() || this.stack[12].getItem() == itemstack3.getItem() || this.stack[12].getItem() == itemstack4.getItem())
            {
            	
 this.stack[12].stackSize += 1; // Forge BugFix: Results may have multiple items

            }

            
            if(cai == 1) {
            	--this.stack[0].stackSize;
            	--this.stack[1].stackSize;
            	--this.stack[2].stackSize;
            	--this.stack[4].stackSize;
            	--this.stack[5].stackSize;
            	--this.stack[6].stackSize;
                
            	if (this.stack[0].stackSize <= 0)
                {
                    this.stack[0] = null;
                }
                if (this.stack[1].stackSize <= 0)
                {
                    this.stack[1] = null;
                }
                if (this.stack[2].stackSize <= 0)
                {
                    this.stack[2] = null;
                }
                if (this.stack[4].stackSize <= 0)
                {
                    this.stack[4] = null;
                }
                if (this.stack[5].stackSize <= 0)
                {
                    this.stack[5] = null;
                }
                if (this.stack[6].stackSize <= 0)
                {
                    this.stack[6] = null;
                }
            }
            
            if(cai == 2 ||cai == 3||cai == 4) {
            	--this.stack[0].stackSize;
            	--this.stack[1].stackSize;
            	--this.stack[2].stackSize;
            	--this.stack[3].stackSize;
            	--this.stack[4].stackSize;
            	--this.stack[5].stackSize;
            	--this.stack[6].stackSize;
            	--this.stack[7].stackSize;
                
            	if (this.stack[0].stackSize <= 0)
                {
                    this.stack[0] = null;
                }
                if (this.stack[1].stackSize <= 0)
                {
                    this.stack[1] = null;
                }
                if (this.stack[2].stackSize <= 0)
                {
                    this.stack[2] = null;
                }
                if (this.stack[3].stackSize <= 0)
                {
                    this.stack[3] = null;
                }
                if (this.stack[4].stackSize <= 0)
                {
                    this.stack[4] = null;
                }
                if (this.stack[5].stackSize <= 0)
                {
                    this.stack[5] = null;
                }
                if (this.stack[6].stackSize <= 0)
                {
                    this.stack[6] = null;
                }
                if (this.stack[7].stackSize <= 0)
                {
                    this.stack[7] = null;
                }
            }

        }
    

    @SideOnly(Side.CLIENT)
    public float getCookProgressScaled(int int1)
    {
        return this.furnaceCookTime * int1 / 500;
    }

    public int canchao(){
    	//��3��3
    	  if (this.stack[0] != null && this.stack[1] != null && this.stack[2] != null && this.stack[4] != null && this.stack[5] != null && this.stack[6] != null)
{
    	if(stack[0].getItem() == Items.egg && stack[1].getItem() == ModItem.ItemFanqie && stack[2].getItem() == ModItem.ItemBaifan) {
    		if(stack[4].getItem() == ModItem.ItemHuashenyou && stack[5].getItem() == ModItem.ItemYan && stack[6].getItem() == Items.sugar)return 1;
    	}

}    
    	//��4��4
    	  if (this.stack[0] != null && this.stack[1] != null && this.stack[2] != null &&  this.stack[3] != null && this.stack[4] != null && this.stack[5] != null && this.stack[6] != null && this.stack[7] != null)
{
    		  if(stack[0].getItem() == ModItem.ItemLajiao && stack[1].getItem() == Items.potato && stack[2].getItem() == ModItem.ItemQiezi && this.stack[3].getItem() == ModItem.ItemBaifan) {
    	    		if(stack[4].getItem() == ModItem.ItemHuashenyou && stack[5].getItem() == ModItem.ItemYan && stack[6].getItem() == Items.sugar && stack[7].getItem() == ModItem.ItemJiangyou)return 2;	  
}
     		  if(stack[0].getItem() == Items.cooked_porkchop && stack[1].getItem() == ModItem.ItemLajiao && stack[2].getItem() == Items.carrot && this.stack[3].getItem() == ModItem.ItemBaifan) {
  	    		if(stack[4].getItem() == ModItem.ItemHuashenyou && stack[5].getItem() == ModItem.ItemYan && stack[6].getItem() == Items.sugar && stack[7].getItem() == ModItem.ItemCu)return 3;	  
}
     		  if(stack[0].getItem() == Items.cooked_chicken && stack[1].getItem() == ModItem.ItemHuashen && stack[2].getItem() == ModItem.ItemLajiao && this.stack[3].getItem() == ModItem.ItemBaifan) {
  	    		if(stack[4].getItem() == ModItem.ItemHuashenyou && stack[5].getItem() == ModItem.ItemYan && stack[6].getItem() == Items.sugar && stack[7].getItem() == ModItem.ItemCu)return 4;	  
}
  		   
    }
    	  return 0;
    }
    }
