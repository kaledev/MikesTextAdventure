package com.mikekale.mikestextadventure.commands;

import com.mikekale.mikestextadventure.Commands;
import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;
import java.util.LinkedList;

/**
 * Examine Command
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Examine {
    
    /**
     * Examine an item
     * @param playerInventory  player's current inventory
     * @param playerInputQueue  player's input
     */
    public Examine(Inventory playerInventory, LinkedList playerInputQueue){
        Item roomElement = null;
        Commands.Directions direction = null;
        
        if(playerInputQueue.isEmpty()){
            //Make sure modifiers exist
            System.out.println("\n" + Commands.EXAMINE.getUse() + "\n");
            return;
        }
        
        //See if item is in the room
        //Check room and inventory
        for (int x = playerInputQueue.size(); x > 0; x--){
            String tempItem = "";
            //Get list up to 'i'
            for(int i = 0; i < x; i++){
                tempItem += playerInputQueue.get(i) + " ";
            }

            if (tempItem.trim().equalsIgnoreCase("ROOM") || 
                    tempItem.trim().equalsIgnoreCase(GameSettings.gameGrid.getPlayerRoom().getName())){
                //Item is the room itself
                
                System.out.println(GameSettings.gameGrid.getPlayerRoom().info()); //Show info
                return;
            } else if (GameSettings.gameGrid.getPlayerRoom().itemIsRoomElement(tempItem.trim())){
                //Item is a room element (door, window, etc.)
                
                //Check direction of room element
                try {
                    direction = direction.valueOf(playerInputQueue.get(1).toString());
                } catch(IllegalArgumentException | NullPointerException | IndexOutOfBoundsException e){
                    System.out.println("\nYou're missing a direction!\n" + Commands.EXAMINE.getUse() + "\n");
                    return;
                }

                roomElement = GameSettings.gameGrid.getPlayerRoom().getRoomElement(tempItem.trim(), direction);

                //Check if room element exists
                if (roomElement == null){
                    //No element in that direction
                    System.out.println("\nExamine what?, I don't see that...\n" + Commands.EXAMINE.getUse() + "\n");
                    return;
                }

                roomElement.examine(playerInventory); //Examine item
                return;
            }else if (GameSettings.gameGrid.getPlayerRoom().itemIsInRoom(tempItem.trim())){
                //Item found in a room
                GameSettings.gameGrid.getPlayerRoom().getItem(tempItem.trim()).examine(playerInventory); //Examine item
                return;
            } else if (playerInventory.isInInventory(tempItem.trim())) {
                //Item found in the inventory
                playerInventory.getItem(tempItem.trim()).examine(playerInventory); //Examine item
                return;
            } else {
                //Not found, keep going
            }
        }
        
        //Item doesn't exist in the room...
        System.out.println("\nExamine what?, I don't see that...\n" + Commands.EXAMINE.getUse() + "\n");
    }
}
