package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;
import java.util.LinkedList;

/**
 * Front door to the house, interacts with the Knifewrench
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class FrontDoor extends Door{

    /**
     * Creare a new front door
     * @param doorDescription  description of door
     */
    public FrontDoor(String doorDescription) {
        super(doorDescription, true);
    }
    
    @Override
    public void interact(Item item){
        if(item.getItemName().equalsIgnoreCase("KNIFEWRENCH")){ //Only the knifewrench fixes the door
            //Unlock and remove item from inventory
            this.unlockItem();
            this.setItemDescription("The front door, leads outside.\nBadly scribbled handwriting on the door says 'Pete was here'.");
            GameSettings.gameGrid.getPlayerInventory().removeFromInventory(item);
        }
    }
    
    /**
     * Use the door
     * @param playerInventory  player's current inventory
     * @param playerInputQueue  player's input
     */
    @Override
    public void use(Inventory playerInventory, LinkedList playerInputQueue) {
        //Use door
        if(super.itemIsLocked()){
            System.out.println("\nThe door is jammed! And the duct tape doesn't help either.\n"
                    + "If only I could cut the tape and wrench it open...\n");
        } else {
            if(!isOpened()){
                setOpened(true);
                System.out.println("\nYou open the door.\n");
            } else {
                setOpened(false);
                System.out.println("\nYou close the door.\n");
            }
        }
    }
}
