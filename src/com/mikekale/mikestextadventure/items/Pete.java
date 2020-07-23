package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;
import com.mikekale.mikestextadventure.Main;
import java.util.LinkedList;

/**
 * Pete
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-19 18:55:30 -0400 (Wed, 19 Mar 2014) $
 * $LastChangedRevision: 32 $
 */
public class Pete extends Item{
    
    private final Book conversation;
    private final WDD wdd;

    /**
     * Create a new Pete
     * @param itemName  item name
     * @param itemDescription  item description
     * @param itemLocationDescription  item description location
     * @param conversation  conversation to have
     * @param wdd  world domination device
     */
    public Pete(String itemName, String itemDescription, String itemLocationDescription, Book conversation, WDD wdd) {
        super(itemName, itemDescription, itemLocationDescription, null, false, false, false);
        this.conversation = conversation;
        this.wdd = wdd;
    }
    
    @Override
    public void use(Inventory playerInventory, LinkedList playerInputQueue){
        if(wdd.isReady()){
            conversation.use(playerInventory, playerInputQueue);
            Main.setWin(true); //Won the game!
            GameSettings.gameGrid.getPlayerRoom().removeItem(this); //Remove Pete
        } else {
            System.out.println("\n<Pete> How in the world did you make it in this room without\n" +
                    "properly prepping the World Domination Device? Go back and fix it!\n");
        }
    }
}
