package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.RoomElement;
import java.util.LinkedList;

/**
 * Door class
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Door extends RoomElement{
    private String description;
    private boolean opened = false;

    /**
     * Create a new door item
     * @param doorDescription  description of door
     * @param locked  is this door locked?
     */
    public Door(String doorDescription, boolean locked){
        super(RoomElement.RoomElements.DOOR, "DOOR", doorDescription, null, null, true, locked);
        
        this.description = doorDescription;
    }
    
    /**
     * Check if door is opened
     * @return true if opened
     */
    public boolean isOpened(){
        return opened;
    }
    
    /**
     * Open or close the door
     * @param opened  true or false
     */
    public void setOpened(boolean opened){
        this.opened = opened;
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
            System.out.println("\nThe door is locked!\n");
        } else {
            if(!opened){
                opened = true;
                System.out.println("\nYou open the door.\n");
            } else {
                opened = false;
                System.out.println("\nYou close the door.\n");
            }
        }
    }

}
