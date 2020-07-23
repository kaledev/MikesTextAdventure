package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;

/**
 * Television, distributes a key when a tape is played in the VCR
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Television extends Item{
    
    private final Book conversation;
    private boolean played = false;

    /**
     * Create a new Television
     * @param itemName  item name
     * @param itemDescription  item description
     * @param useText  use text
     * @param conversation  conversation to have
     */
    public Television(String itemName, String itemDescription, String useText, Book conversation) {
        super(itemName, itemDescription, null, useText, false, false, false);
        this.conversation = conversation;
    }
    
    @Override
    public void examine(Inventory playerInventory){
        //Only display description
        System.out.println("\n" + this.getItemDescription() + "\n");
    }
    
    @Override
    public void interact(Item item){
        if(item.getItemName().equalsIgnoreCase("TAPE")){ //Only the tape distributes the key
            //Distribute items inside
            conversation.use(null, null);
            if(!played){
                played = true;
                System.out.println("Something catches your eye under the TV, maybe I should examine it again.\n");
                super.examine(GameSettings.gameGrid.getPlayerInventory());
            }
        }
    }
}
