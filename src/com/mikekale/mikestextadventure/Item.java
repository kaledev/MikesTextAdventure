package com.mikekale.mikestextadventure;

import com.mikekale.mikestextadventure.items.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Item Class
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Item implements java.io.Serializable {

    private final String itemName;                              //Name of the item
    private String itemDescription;                             //Item description
    private String itemLocationDescription;                     //Describes where the item is located
    private final String[] verbForm = {"There is a ", "There are ", ""};  //Verb form for location description
    private String curVerbForm = verbForm[0];                   //Current verb form for the location description
    private ArrayList<Item> items = new ArrayList<>();          //Items contained within this item, not a HashSet because of examining issues
    private Item itemContainedIn;                               //Item this item is contained in
    private Room roomContainedIn;                               //Room this item is contained in
    private final boolean canAdd;                               //Can this item be added to the inventory or not
    private final boolean lockable;                             //Can this item be locked?
    private boolean locked;                                     //Locked
    private Item itemCombinesWith;                              //Item combines with
    private Item itemBecomes;                                   //Item becomes once combined
    private final String useText;                               //Use text of the item
    private HashMap<Item, String> interactions = new HashMap(); //HashMap of interactions
    private boolean roomElement = false;                        //Item is a room element, false by default
    private Key keyUnlocks;                                     //Sets the key that this item is unlocked by
    
    /**
     * Create a new item
     * @param itemName  Name of the item
     * @param itemDescription  Item description
     * @param itemLocationDescription  Describes where the item is located, will display in EXAMINE ROOM - use NULL if this isn't desired
     * @param useText  Text displayed when item is used
     * @param canAdd  Can this item be added to the inventory or not
     * @param lockable  Can this item be locked?
     * @param locked   Locked
     */
    public Item(String itemName, String itemDescription, String itemLocationDescription, String useText, boolean canAdd, 
            boolean lockable, boolean locked){
        //New item
        if(itemName.length() > GameSettings.CONSOLELEN - 2){
            System.out.println("Programming Error: Item name length limit is " + (GameSettings.CONSOLELEN - 2));
            System.exit(0);
        }
        if(RoomElement.RoomElements.contains(itemName) && !(this instanceof RoomElement)){
            System.out.println("Programming Error: Item can't use a reserved room element name!");
            System.exit(0);
        }
        this.itemName = itemName;                                   //Set name
        this.itemDescription = itemDescription;                     //Set Item description
        this.itemLocationDescription = itemLocationDescription;     //Set Item Location Description
        this.canAdd = canAdd;                                       //Set if item can be added to the inventory
        this.lockable = lockable;                                   //Set lockable
        if (!lockable && locked){
            System.out.println("Programming Error: Item can't be locked if unlockable!");
            System.exit(0);
        } else {
            this.locked = locked;                                   //Set locked
        }
        this.useText = useText;                                     //Set useText
    }

    /**
     * Get the name of this item
     * @return item name
     */
    public String getItemName() {
        return itemName;
    }
    
    /**
     * Get the item description of this item
     * @return item description
     */
    public String getItemDescription(){
        return this.itemDescription;
    }
    
    /**
     * Put a given item inside of this item
     * @param item  item(s) to be put inside of this item
     */
    public void putItemInside(Item ... item){
        for (Item newItem : item) {
            this.items.add(newItem);
            newItem.itemContainedIn = this;
        }
        
        //remove duplicates if any (Since items cannot be a set due to several issues)
        HashSet setItems = new HashSet(items);
        items.clear();
        items.addAll(setItems);
    }
    
    /**
     * Remove an item contained within this item
     * @param item  item to be removed from within this item
     */
    public void removeItemInside(Item item){
        items.remove(item);
        item.itemContainedIn = null;
    }
    
    /**
     * Get the item location description of the item<br>
     * I.E. Text describing where its located
     * @return item location description
     */
    public String getItemLocationDescription(){
        return itemLocationDescription;
    }
    
    /**
     * Set the item description of the item
     * @param itemDescription  item description
     */
    public void setItemDescription(String itemDescription){
        this.itemDescription = itemDescription;
    }
    
    /**
     * Returns the current verb form for the item location description
     * @return verb form
     */
    public String getCurVerbForm(){
        return this.curVerbForm;
    }
    
    /**
     * Sets the verb form for item location description<br>
     * Defaults to 0<br>
     * 0: 'There is a'<br>
     * 1: 'There are'<br>
     * 2: No form
     * @param form  verb form
     */
    public void setVerbForm(int form){
        if(form > verbForm.length){
            System.out.println("Programming Error: Verb Form out of bounds!");
            System.exit(0);
        } else {
            this.curVerbForm = verbForm[form];
        }
    }
    
    /**
     * Set the room location of this item
     * @param roomLocation  room where this item is location
     */
    public void setRoomLocation(Room roomLocation) {
        this.roomContainedIn = roomLocation;
    }
    
    /**
     * See if item can be added to the player's inventory
     * @return true if item can be added to the player's inventory
     */
    public boolean canAddToInventory(){
        return canAdd;
    }
    
    /**
     * Get item that this item is contained in
     * @return parent item that this item is contained in
     */
    public Item getItemContanedIn(){
        return itemContainedIn;
    }
    
    /**
     * Get room this item is contained in
     * @return room that this item is contained in
     */
    public Room getRoomContainedIn(){
        return roomContainedIn;
    }
    
    /**
     * Return use text for item
     * @return use text
     */
    public String getUseText(){
        return useText;
    }
    
    /**
     * Unlock an item unconditionally
     */
    public void unlockItem(){
        locked = false;
    }
    
    /**
     * Unlocks an item; used for keys
     * @param keyUnlocks  key sent to unlock
     * @return true if item has been unlocked
     */
    public boolean unlockItem(Key keyUnlocks){
        //Unlock Item
        if(lockable){
            if(keyUnlocks.equals(this.keyUnlocks)){ //Check if this is the correct item to be unlocked
                locked = false; //Unlock
                return true;
            }
        } else {
            return false;
        }
        
        return false;
    }
    
    /**
     * See if this item is locked
     * @return true if item is locked
     */
    public boolean itemIsLocked(){
        return locked;
    }
    
    /**
     * Set if an item is a room element or not
     * @param roomElement true or false
     */
    public void setRoomElement(boolean roomElement){
        this.roomElement = roomElement;
    }
    
    /**
     * Check if item is a room element
     * @return true if room element
     */
    public boolean isRoomElement(){
        return roomElement;
    }
    
    /**
     * Sets the item that this item combines with
     * @param itemCombinesWith  item that this item can be combined with
     * @param itemBecomes  item that this item becomes when combined
     */
    public void setItemCombinesWith(Item itemCombinesWith, Item itemBecomes){
        //Disallow combining of room elements for obvious reasons
        if(this.isRoomElement() || itemCombinesWith.isRoomElement() || itemBecomes.isRoomElement()){
            System.out.println("Programming Error: You can't combine room elements or combine to create a room element.");
            System.exit(0);
            return;
        }
        
        if (this.itemCombinesWith == null){
            this.itemCombinesWith = itemCombinesWith;
            this.itemBecomes = itemBecomes;
            itemCombinesWith.setItemCombinesWith(this, itemBecomes);
        }
    }
    
    /**
     * Gets the item that this item combines with
     * @return  item that this item combines with, null if none.
     */
    public Item getItemCombinesWith(){
        return this.itemCombinesWith;
    }
    
    /**
     * Gets the item that this item becomes
     * @return  item that this item becomes, null if none.
     */
    public Item getItemBecomes(){
        return this.itemBecomes;
    }
    
    /**
     * Sets an interaction between two items
     * @param item  item to interact with
     * @param interactionText  text to display on interaction
     */
    public void setInteraction(Item item, String interactionText){
        //Disallow interaction of two room elements for obvious reasons
        if(this.isRoomElement() && item.isRoomElement()){
            System.out.println("Programming Error: A room element can't interact with another room element.");
            System.exit(0);
            return;
        }
        
        interactions.put(item, interactionText);
    }
    
    /**
     * See if an item interacts with another
     * @param item  item to test
     * @return true if interacts
     */
    public Boolean interactsWith(Item item){
        return interactions.containsKey(item);
    }
    
    /**
     * See if an item interacts with another
     * @param item  item to test
     * @return true if interacts
     */
    private Boolean interactsWith(String item){
        for (Map.Entry pairs : interactions.entrySet()) {
            Item tempItem = (Item)pairs.getKey();            //Item to interact with
            if(tempItem.getItemName().equalsIgnoreCase(item)){
                return true; //Item found
            }
        }
        return false; //Not found
    }
    
    /**
     * Return interaction text
     * @param item item to grab interaction text from
     * @return string interaction text, null if not found
     */
    private String getInteractText(String item){
        for (Map.Entry pairs : interactions.entrySet()) {
            Item tempItem = (Item)pairs.getKey();                               //Item to interact with
            String interactText = (String)interactions.get(pairs.getKey());     //Interaction text
            if(tempItem.getItemName().equalsIgnoreCase(item)){
                return interactText; //Item found
            }
        }
        return null; //Not found
    }
    
    /**
     * Interact with an item
     * @param item  string name of item to interact with
     */
    private void interactWithItem(String item){
        for (Map.Entry pairs : interactions.entrySet()) {
            Item tempItem = (Item)pairs.getKey();                               //Item to interact with
            String interactText = (String)interactions.get(pairs.getKey());     //Interaction text
            if(tempItem.getItemName().equalsIgnoreCase(item)){
                tempItem.interact(this); //Item found
            }
        }
        //Not found
    }
    
        
    /**
     * Sets a single key that unlocks this item
     * @param keyUnlocks  key that unlocks this item
     */
    public void setKeyUnlocks(Key keyUnlocks){
        this.keyUnlocks = keyUnlocks;
    }
    
    /**
     * Gets a key that unlocks this item
     * @return key
     */
    public Key getKeyUnlocks(){
        return keyUnlocks;
    }
    
    /**
     * Examine an item
     * @param playerInventory  player's current inventory
     */
    public void examine(Inventory playerInventory){
        //Examine item
        
        Scanner sc = new Scanner(System.in);
        
        if(playerInventory.isInInventory(this) || roomContainedIn != null || !canAdd){
            //Don't display description unless in inventory or a room
            //Or if the item can't be added to the inventory
            System.out.println("\n" + getItemDescription());
        } 

        //Location description if the item has one and isn't in the inventory
        //if (itemLocationDescription != null && !playerInventory.isInInventory(this)){
        //    System.out.println("..." + getItemLocationDescription());
        //}
        
        //Locked or not
        if (lockable && locked) {
            System.out.println("...Appears to be locked!");
        }
        
        //Examine any items inside of this item
        //Don't go past second level        
        if (items.size() > 0 && itemContainedIn == null){  
            int i;
            for(i = 0; i < items.size(); i++){
                int checkSize = items.size(); //starting size to compare
                if(locked){
                    break; //Exit if the item is lockable, have to unlock before viewing children
                }
                System.out.println("\nAppears to be something else here...");
                System.out.print("\nPress Enter...");
                sc.nextLine();                                 //Wait for Enter
                items.get(i).examine(playerInventory);
                if(items.size() > 0 && items.size() != checkSize){
                    i = i-1; //Adjust for removal
                }
            }
            if (items.isEmpty() || i <= items.size()){
                System.out.println(""); //For proper spacing
            }
        } else {
            if(playerInventory.isInInventory(this) || canAdd || roomContainedIn != null){
                System.out.println(""); //For proper spacing
            }
        }

        //If item can be added to inventory and isn't already in it, add it and clean up
        if(canAdd && !playerInventory.isInInventory(this)){
            playerInventory.addToInventory(this);  //Add to inventory
            itemLocationDescription = null; //Reset location description
            System.out.println(this.getItemName() + " has been added to your inventory!");
            if(roomContainedIn != null) {
                roomContainedIn.removeItem(this); //Remove item from room
            }
            
            if(itemContainedIn != null) {
                itemContainedIn.removeItemInside(this); //Remove item from inside of another item
            } else {
                System.out.println(""); //For proper spacing
            }
        }
    }

    /**
     * Use the item
     * @param playerInventory  player's current inventory
     * @param playerInputQueue  player's input
     */
    public void use(Inventory playerInventory, LinkedList playerInputQueue){
        Room playerRoom = GameSettings.gameGrid.getPlayerRoom();
        
        //Check if item is locked
        if(locked){
            System.out.println("\nAppears to be locked!\n");
            return;
        }
        
        //See if modifiers exist
        try {
            playerInputQueue.element();
            //See if ON keyword exists
            if (!playerInputQueue.remove().toString().equalsIgnoreCase("ON")) {
                System.out.println("\nThe item can only be used ON something.\n" + Commands.USE.getUse() + "\n");
                return;
            }
        } catch(NoSuchElementException e){
            System.out.println("\n" + useText + "\n");
            return;
        }
		
        //Make sure item exists after ON
        try {
            playerInputQueue.element(); //see if text exists after ON at all
            
            //Check if referring to a room element
            if(playerRoom.itemIsRoomElement(playerInputQueue.element().toString())) {
                
                if (itemCombinesWith != null && itemCombinesWith.itemName.equalsIgnoreCase(playerInputQueue.element().toString())){
                    //Refers to the item that this one combines with
                    System.out.println("\nNothing happened!\nBut it looks like these two items may fit together somehow...\n");
                    return;
                } else if (interactsWith((playerInputQueue.element().toString()))) {
                    //Refers to the item that this one interacts with
                    if (playerRoom.canInteractWithRoomElement(playerInputQueue.element().toString(), this)){
                        System.out.println("\n" + getInteractText(playerInputQueue.element().toString()) + "\n");
                        interactWithItem(playerInputQueue.element().toString());
                        return;
                    } else {
                        System.out.println("\nNothing happened!\n");
                        return;
                    }
                } else {
                    System.out.println("\nNothing happened!\n");
                    return;
                }
            }
            
            //Check room and inventory
            String tempItem = "";
            int tempPISize = playerInputQueue.size();
            for (int x = 0; x < tempPISize; x++){
                tempItem += playerInputQueue.peek().toString() + " ";

                if (playerRoom.itemIsInRoom(tempItem.trim())){
                    //Item found in a room
                    playerInputQueue.remove();	//Found, remove
                    
                    if (this.itemCombinesWith != null && this.itemCombinesWith.itemName.equalsIgnoreCase(tempItem.trim())){
                        //See if the item mentioned combines
                        System.out.println("\nNothing happened!\nBut it looks like these two items may fit together somehow...\n");
                    } else if (interactsWith(tempItem.trim())) {
                        //Refers to the item that this one interacts with
                        System.out.println("\n" + getInteractText(tempItem.trim()) + "\n");
                        interactWithItem(tempItem.trim());
                    } else {
                        System.out.println("\nNothing happened!\n");
                    }
                    return;
                } else if (playerInventory.isInInventory(tempItem.trim())) {
                    //Item found in the inventory
                    playerInputQueue.remove();  //Found, remove
                    
                    if (this.itemCombinesWith != null && this.itemCombinesWith.itemName.toString().equalsIgnoreCase(tempItem.trim())){
                        //See if the item mentioned combines
                        System.out.println("\nNothing happened!\nBut it looks like these two items may fit together somehow...\n");
                    } else if (interactsWith(tempItem.trim())) {
                        //Refers to the item that this one interacts with
                        System.out.println("\n" + getInteractText(tempItem.trim()) + "\n");
                        interactWithItem(tempItem.trim());
                    } else {
                        System.out.println("\nNothing happened!\n");
                    }
                    return;
                } else {
                    if (x == tempPISize - 1){
                        //Item doesn't exist in the room...
                        System.out.println("\nUse what?, I don't see that.\n" + Commands.USE.getUse() + "\n");
                        return;
                    }
                    //Not found, keep going
                    playerInputQueue.remove();
                }
            }         
        } catch(NoSuchElementException e){
            System.out.println("\nThe item can only be used ON something.\n" + Commands.USE.getUse() + "\n");
            return;
        }
    }
    
    /**
     * Called if function isn't overriden
     * @param item
     */
    public void interact(Item item){
        //Overrideable function
    }
}
