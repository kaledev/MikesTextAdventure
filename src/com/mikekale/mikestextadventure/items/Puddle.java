package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;

/**
 * Puddle Class - distributes items when dried up
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Puddle extends Item{

    /**
     * Create a new Puddle
     * @param itemName  item name
     * @param itemDescription  item description
     * @param itemLocationDescription  item location description
     * @param useText  use text
     */
    public Puddle(String itemName, String itemDescription, String itemLocationDescription, String useText) {
        super(itemName, itemDescription, itemLocationDescription, useText, false, false, false);
    }
    
    @Override
    public void examine(Inventory playerInventory){
        //Disallow examining - we want to dry up the puddle first!
        System.out.println("\n" + this.getItemDescription() + "\n");
    }
    
    @Override
    public void interact(Item item){
        if(item.getItemName().equalsIgnoreCase("TOWEL")){ //Correct item
            //Distribute items inside
            super.examine(GameSettings.gameGrid.getPlayerInventory());
            
            //Remove and clean up puddle from room
            this.getRoomContainedIn().removeItem(this);
        }
    }
}
