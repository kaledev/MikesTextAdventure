package com.mikekale.mikestextadventure.commands;

import com.mikekale.mikestextadventure.Commands;
import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;
import java.util.LinkedList;

/**
 * Combine - Two items that are combined to produce another
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Combine {

    /**
    * Combines two items together
    * @param playerInventory  player's current inventory
    * @param playerInputQueue  player's input
    */
    public Combine(Inventory playerInventory, LinkedList playerInputQueue){
        Item firstItem = null;
        Item secondItem = null;
        
        if(playerInputQueue.isEmpty()){
            //Make sure modifiers exist
            System.out.println("\n" + Commands.COMBINE.getUse() + "\n");
            return;
        }
        
        //--------------------
        //Look for first item
        //--------------------
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
                System.out.println("\nIt's a good idea, but I don't think it's going to work.\n");
                return;
            }else if (GameSettings.gameGrid.getPlayerRoom().itemIsInRoom(tempItem.trim())){
                //Item found in a room
                firstItem = GameSettings.gameGrid.getPlayerRoom().getItem((tempItem.trim())); //Get the item
                
                //Remove items from the list already processed
                for(int i = 0; i < x; i++){
                    playerInputQueue.remove();
                }
                
                break;
            }else if (playerInventory.isInInventory(tempItem.trim())){
                //Item found in the inventory
                firstItem = playerInventory.getItem(tempItem.trim()); //Get item
                
                //Remove items from the list already processed
                for(int i = 0; i < x; i++){
                    playerInputQueue.remove();
                }
                
                break;
            } else {
                if (x == 1){
                    //Return if you don't have the items
                    System.out.println("\nCombine what? I'm confused.\n" + Commands.COMBINE.getUse() + "\n");
                    return;
                }
                //Not found, keep going
            }
        }
        
        if (playerInputQueue.size() == 0){
            System.out.println("\nYou must combine two items!\n" + Commands.COMBINE.getUse() + "\n");
            return;
        }
        
        if(playerInputQueue.peek().toString().trim().equalsIgnoreCase("AND")){
            playerInputQueue.remove(); //Remove 'AND', and continue
        } else {
            System.out.println("\n" + Commands.COMBINE.getUse() + "\n");
            return;
        }
   
        if (playerInputQueue.size() == 0){
            System.out.println("\nYou must combine two items!\n" + Commands.COMBINE.getUse() + "\n");
            return;
        }
        
        tempPISize = playerInputQueue.size(); //reset
		
        //--------------------
        //Look for second item
        //--------------------
        
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
                System.out.println("\nIt's a good idea, but I don't think it's going to work.\n");
                return;
            }else if (GameSettings.gameGrid.getPlayerRoom().itemIsInRoom(tempItem.trim())){
                //Item found in a room
                secondItem = GameSettings.gameGrid.getPlayerRoom().getItem((tempItem.trim())); //Get the item
                
                //Remove items from the list already processed
                for(int i = 0; i < x; i++){
                    playerInputQueue.remove();
                }
                
                break;
            }else if (playerInventory.isInInventory(tempItem.trim())){
                //Item found in the inventory
                secondItem = playerInventory.getItem(tempItem.trim()); //Get item
                
                //Remove items from the list already processed
                for(int i = 0; i < x; i++){
                    playerInputQueue.remove();
                }
                
                break;
            } else {
                if (x == 1){
                    //Return if you don't have the items
                    System.out.println("\nCombine what? I'm confused.\n" + Commands.COMBINE.getUse() + "\n");
                    return;
                }
                //Not found, keep going
            }
        }
		
        //--------------------------
        //Combine items and clean up
        //--------------------------
        
        if(firstItem.getItemCombinesWith() != null && firstItem.getItemCombinesWith().equals(secondItem)){
            //Remove from inventory
            if(playerInventory.isInInventory(firstItem)){
                playerInventory.removeFromInventory(firstItem);
            }
            
            //Remove from room
            if(firstItem.getRoomContainedIn() != null) {
                firstItem.getRoomContainedIn().removeItem(firstItem); //Remove item from room
            }
            
            //Remove from inventory
            if(playerInventory.isInInventory(secondItem)){
                playerInventory.removeFromInventory(secondItem);
            }
            
            //Remove from room
            if(secondItem.getRoomContainedIn() != null) {
                secondItem.getRoomContainedIn().removeItem(secondItem); //Remove item from room
            }
            
            playerInventory.addToInventory(firstItem.getItemBecomes());
            System.out.println("\nSuccess!\n" + firstItem.getItemBecomes().getItemName() + " has been added to your inventory!\n");
        } else {
            if(firstItem.interactsWith(secondItem) || secondItem.interactsWith(firstItem)){
                System.out.println("\nYou can't necessarily combine these two items, but you're on the right track!\n");
            } else {
                System.out.println("\nIt's a good idea, but I don't think it's going to work.\n");
            }
        }
    }
}
