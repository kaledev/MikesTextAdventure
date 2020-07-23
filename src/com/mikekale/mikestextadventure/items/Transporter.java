package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;
import com.mikekale.mikestextadventure.commands.Transport;
import java.util.LinkedList;

/**
 * Personal Transportation System
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Transporter extends Item{

    /**
     * Create a new Transporter
     * @param itemName  item name
     * @param itemDescription  item description
     */
    public Transporter(String itemName, String itemDescription) {
        super(itemName, itemDescription, null, null, true, false, false);
    }
    
    /**
     * Use the transporter
     * @param playerInventory  player's current inventory
     * @param playerInputQueue  player's input
     */
    @Override
    public void use(Inventory playerInventory, LinkedList playerInputQueue) {
        //Transport the player
        Transport transport = new Transport(false);
    }
}
