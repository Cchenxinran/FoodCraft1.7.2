package com.cfyifei.api;

import net.minecraft.creativetab.CreativeTabs;
import com.cfyifei.FoodCraft;
public class FcAPI {
/**
 * Get the mod name.
 *
 */
	public static String getName(){
		return "FoodCraft";
	}
	/**
	 * Get the mod version.
	 *
	 */
	public static String getVersion(){
		return "1.1.5";
	}
	/**
	 * Get the mod creativeTab.
	 *
	 */
	public static CreativeTabs getCreativeTab(){
		return FoodCraft.FcTab;
	}
	
	
}
