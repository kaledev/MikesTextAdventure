package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.Commands;
import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;
import com.mikekale.mikestextadventure.Room;
import com.mikekale.mikestextadventure.RoomElement;
import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * Key Class
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Key extends Item{
    private Room roomWithElement;                           //Room that contains this element
    private RoomElement.RoomElements roomElementUnlocks;    //Room element that this key unlocks
    private Commands.Directions direction;                  //Direction that the key unlocks
    private Item itemUnlocks;                               //Item this key unlocks
    
    /**
     * Create a key item that unlocks a room element
     * @param itemName  item name
     * @param itemDescription  item description
     * @param itemLocationDescription  item location description
     * @param canAdd  can item be added to the inventory?
     * @param roomWithElement  room that contains room element to be unlocked
     * @param roomElementUnlocks  room element to be unlocked
     * @param direction  direction of room element to be unlocked
     */
    public Key(String itemName, String itemDescription, String itemLocationDescription, boolean canAdd, 
            Room roomWithElement, RoomElement.RoomElements roomElementUnlocks, Commands.Directions direction){
        //Key opens a room element
        
        super(itemName, itemDescription, itemLocationDescription, null, canAdd, false, false);
        this.roomWithElement = roomWithElement;
        this.direction = direction;
        this.roomElementUnlocks = roomElementUnlocks;
        roomWithElement.getRoomElement(roomElementUnlocks, direction).setKeyUnlocks(this);
    }
    
    /**
     * Create a key item that unlocks an item
     * @param itemName  item name
     * @param itemDescription  item description
     * @param itemLocationDescription  item location description
     * @param canAdd  can item be added to the inventory?
     * @param itemUnlocks  item to be unlocked
     */
    
    public Key(String itemName, String itemDescription, String itemLocationDescription, boolean canAdd, Item itemUnlocks){
        //Key opens an item
        
        super(itemName, itemDescription, itemLocationDescription, null, canAdd, false, false);
        this.itemUnlocks = itemUnlocks;
        itemUnlocks.setKeyUnlocks(this);
    }

    /**
     * Use the key
     * @param playerInventory  player's current inventory
     * @param playerInputQueue  player's input
     */
    @Override
    public void use(Inventory playerInventory, LinkedList playerInputQueue){
        Room playerRoom = GameSettings.gameGrid.getPlayerRoom();    //Room player is currently in
        
        //Make sure modifiers exist
        try {
            playerInputQueue.element();
            //Make sure ON keyword exists
            if (!playerInputQueue.remove().toString().equalsIgnoreCase("ON")) {
                System.out.println("\nKey has to be used ON something...\n" + Commands.USE.getUse() + "\n");
                return;
            }
        } catch(NoSuchElementException e){
            System.out.println("\nKey has to be used ON something...\n" + Commands.USE.getUse() + "\n");
            return;
        }
        
        //Make sure item exists in inventory or room element after ON
        try {
            playerInputQueue.element(); //see if text exists after ON at all
            
            //Check if referring to a room element or an item and it exists in the room
            if(playerRoom.itemIsRoomElement(playerInputQueue.element().toString())) {
                //unlock room element
                
                RoomElement.RoomElements tempRE = RoomElement.RoomElements.valueOf(playerInputQueue.element().toString());
                
                //Check if this is the correct type of room element
                if(roomElementUnlocks != null && roomElementUnlocks.equals(tempRE)){
                    boolean roomElementUnlocked = playerRoom.unlockRoomElement(this, roomElementUnlocks, direction);
                    
                    if (roomElementUnlocked) {
                        //remove key from inventory if room element
                        playerInventory.removeFromInventory(this);
                        return;
                    } else {
                        //Item wasn't compatible with key
                        System.out.println("\nKey didn't work!\n");
                        return;
                    }
                }
                //Item wasn't compatible with key
                System.out.println("\nKey didn't work!\n");
                return;
            }
            
            int tempPISize = playerInputQueue.size();
            
            //Check room and inventory
            for (int x = tempPISize; x > 0; x--){
                String tempItem = "";

                //Get list up to 'i'
                for(int i = 0; i < x; i++){
                    tempItem += playerInputQueue.get(i) + " ";
                }

                if (playerRoom.itemIsInRoom(tempItem.trim())){
                    //Item found in a room
                    
                    //Remove items from the list already processed
                    for(int i = 0; i < x; i++){
                        playerInputQueue.remove();
                    }
                    
                    if (!playerRoom.getItem(tempItem.trim()).unlockItem(this)){      //Unlock
                        System.out.println("\nKey didn't work!\n");
                    } else {
                        System.out.println("\n" + playerRoom.getItem(tempItem.trim()).getItemName() + " has been unlocked!\n");
                        //remove key from inventory if item was unlocked
                        playerInventory.removeFromInventory(this);
                    }
                    return;
                } else if (playerInventory.isInInventory(tempItem.trim())) {
                    //Item found in the inventory

                    //Remove items from the list already processed
                    for(int i = 0; i < x; i++){
                        playerInputQueue.remove();
                    }
                    
                    if (!playerInventory.getItem(tempItem.trim()).unlockItem(this)){     //Unlock
                        System.out.println("\nKey didn't work!\n");
                    } else {
                        System.out.println("\n" + playerInventory.getItem(tempItem.trim()).getItemName() + " has been unlocked!\n");
                        //remove key from inventory if item was unlocked
                        playerInventory.removeFromInventory(this);
                    }
                    return;
                } else {
                    if (x == 1){
                        //Item doesn't exist in the room...
                        System.out.println("\nUse what?, you don't have that...\n" + Commands.USE.getUse() + "\n");
                        return;
                    }
                    //Not found, keep going
                }
            }         
        } catch(NoSuchElementException e){
            System.out.println("\nKey has to be used ON something...\n" + Commands.USE.getUse() + "\n");
            return;
        }
    }
}
