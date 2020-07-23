package com.mikekale.mikestextadventure.commands;

import com.mikekale.mikestextadventure.Commands;
import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;
import java.util.LinkedList;

/**
 * Use Command
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Use {
    
    /**
     * Use Item
     * @param playerInventory  player's current inventory
     * @param playerInputQueue  player's input
     */
    public Use(Inventory playerInventory, LinkedList playerInputQueue){
        Item roomElement = null;                //Room element
        Commands.Directions direction = null;   //Direction
        
        if(playerInputQueue.isEmpty()){
            //Make sure modifiers exist
            System.out.println("\n" + Commands.USE.getUse() + "\n");
            return;
        }
        
        int tempPISize = playerInputQueue.size();

        //See if the item to be used is in the inventory
        for (int x = tempPISize; x > 0; x--){
            String tempItem = "";
            
            //Get list up to 'i'
            for(int i = 0; i < x; i++){
                tempItem += playerInputQueue.get(i) + " ";
            }

            tempItem = tempItem.replace("(", "");                   //Remove any parenthesis
            tempItem = tempItem.replace(")", "");                   //Remove any parenthesis
            
            if (GameSettings.gameGrid.getPlayerRoom().itemIsRoomElement(tempItem.trim())){
                //Item is a room element (door, window, etc.)
                
                //Remove items from the list already processed
                for(int i = 0; i < x; i++){
                    playerInputQueue.remove();
                }

                //Check direction of room element
                try {
                    direction = direction.valueOf(playerInputQueue.peek().toString());
                } catch(IllegalArgumentException | NullPointerException e){
                    System.out.println("\nYou're missing a direction!\n" + Commands.USE.getUse() + "\n");
                    return;
                }

                roomElement = GameSettings.gameGrid.getPlayerRoom().getRoomElement(tempItem.trim(), direction);

                //Check if room element direction is valid
                if (roomElement == null){
                    //No room element in this direction
                    System.out.println("\nUse what? I don't see that.\n" + Commands.USE.getUse() + "\n");
                    return;
                }

                roomElement.use(playerInventory, playerInputQueue); //Use item
                return;
            }else if (GameSettings.gameGrid.getPlayerRoom().itemIsInRoom(tempItem.trim())){
                //Item found in a room
                Item itemFound = GameSettings.gameGrid.getPlayerRoom().getItem((tempItem.trim())); //Get the item
                
                //Remove items from the list already processed
                for(int i = 0; i < x; i++){
                    playerInputQueue.remove();
                }
                
                //See if item can be added to the inventory before using
                if(!playerInventory.isInInventory(itemFound) &&
                        itemFound.canAddToInventory()){
                    System.out.println("\nWait a second there... Let me examine that first...");
                    itemFound.examine(playerInventory); //Examine and add
                }
                
                itemFound.use(playerInventory, playerInputQueue); //Use item
                return;
            }else if (playerInventory.isInInventory(tempItem.trim())){
                //Item found in the player's inventory
                
                //Remove items from the list already processed
                for(int i = 0; i < x; i++){
                    playerInputQueue.remove();
                }
                
                playerInventory.getItem(tempItem.trim()).use(playerInventory, playerInputQueue); //Use item
                return;
            } else {
                //Not Found, keep going
            }
        }
        System.out.println("\nUse what? I don't see that.\n" + Commands.USE.getUse() + "\n");
    }
}
