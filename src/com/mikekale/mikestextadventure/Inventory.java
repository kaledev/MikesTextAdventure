package com.mikekale.mikestextadventure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Inventory for the user
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Inventory implements java.io.Serializable {
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Adds item to the user's inventory
     * @param item  item(s) to be added to inventory
     */
    public void addToInventory(Item ... item){
        items.addAll(Arrays.asList(item));
        
        //Remove duplicates if any (Since items cannot be a set due to several issues)
        HashSet setItems = new HashSet(items);
        items.clear();
        items.addAll(setItems);
        
        //Sort the list of items
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                return a.getItemName().compareTo(b.getItemName());
            }
        });
    }

    /**
     * Removes item from the user's inventory
     * @param item  item to be removed from inventory
     */
    public void removeFromInventory(Item item){
        items.remove(item);
    }
    
    /**
     * Get item from the user's inventory
     * @param itemName  string representation of item in player's inventory
     * @return item in player's inventory, null if not found
     */
    public Item getItem(String itemName){
        //Double check that the item is in the inventory
        if(isInInventory(itemName)){
            for(Item i : items){
                if(i.getItemName().equalsIgnoreCase(itemName)){
                    return i;
                }
            }
        }

        //Not in inventory
        return null;
    }

    /**
     * Check if the item given is in the user's inventory
     * @param item  item to check
     * @return true if item is found in player's inventory
     */
    public boolean isInInventory(Item item){
        //Is this item in the inventory
        return items.contains(item);
    }

    /**
     * Check if the item given is in the user's inventory
     * @param item  string representation of item to check
     * @return true if item is found in player's inventory
     */
    public boolean isInInventory(String item){
        //Is this item in the inventory
        Iterator<Item> itr = items.iterator();

        while(itr.hasNext()){
            if(itr.next().getItemName().equalsIgnoreCase(item)){
                return true;
            }
        }

        return false;
    }

    /**
     * Prints out the players inventory
     * @return  player's current inventory
     */
    @Override
    public String toString(){
        //Return items in the inventory
        if(items.isEmpty()){
            return "\nYour inventory is empty!\n";
        }

        Iterator<Item> itr = items.iterator();
        String inventory = "\nYour current inventory:\n";

        int invLen = 0;
        while(itr.hasNext()){
            String nextItem = "(" + itr.next().getItemName() + ") ";
            invLen += nextItem.length();
            
            if(invLen > GameSettings.CONSOLELEN - 1){ //Eliminate wrapping
                inventory += "\n" + nextItem;
                invLen = nextItem.length();
            } else {
                inventory += nextItem;
            }
        }

        return inventory + "\n";
    }
}
