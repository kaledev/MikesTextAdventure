package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.RoomElement;
import java.util.LinkedList;

/**
 * Window Class
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Window extends RoomElement{
    private boolean opened = false;
    
    /**
     * Create a window item
     * @param windowDescription  description of window
     */
    public Window(String windowDescription){
        super(RoomElement.RoomElements.WINDOW, "WINDOW", windowDescription, null, null, false, false);
    }

    /**
     * Use the window
     * @param playerInventory  player's current inventory
     * @param playerInputQueue  player's input queue
     */
    @Override
    public void use(Inventory playerInventory, LinkedList playerInputQueue) {
        //Use window
        if(!opened){
            opened = true;
            System.out.println("\nYou open the window.\n");
        } else {
            opened = false;
            System.out.println("\nYou close the window.\n");
        }
    }
}
