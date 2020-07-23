package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;

/**
 * Bee Class
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Bees extends Item{

    /**
     * Create a new bee
     * @param itemName  item name
     * @param itemDescription  item description
     * @param itemLocationDescription  item location description
     * @param useText  use text
     */
    public Bees(String itemName, String itemDescription, String itemLocationDescription, String useText) {
        super(itemName, itemDescription, itemLocationDescription, useText, false, false, false);
    }
    
    @Override
    public void examine(Inventory playerInventory){
        //Disallow examining
        System.out.println("\n" + this.getItemDescription() + "\n");
    }
    
    @Override
    public void interact(Item item){
        if(item.getItemName().equalsIgnoreCase("CATTLE PROD")){ //Correct item
            //Distribute items inside
            super.examine(GameSettings.gameGrid.getPlayerInventory());
            GameSettings.gameGrid.getPlayerInventory().removeFromInventory(item); //Remove item
        }
    }
    
}
