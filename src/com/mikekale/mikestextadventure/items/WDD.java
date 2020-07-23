package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * World Domination Device
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class WDD extends Item{
    
    private boolean ready = false;  //Device ready
    private boolean bee = false;    //Bee input
    private boolean bc = false;     //Bungee Cord input
    private boolean bp = false;     //Blueprint input

    /**
     * Create a new World Domination Device
     * @param itemName  item name
     * @param itemDescription  item description
     * @param itemLocationDescription   item location description
     */
    public WDD(String itemName, String itemDescription, String itemLocationDescription) {
        super(itemName, itemDescription, itemLocationDescription, null, false, false, false);
    }
    
    public boolean isReady(){
        return ready;
    }
    
    @Override
    public void examine(Inventory playerInventory){
        //Disallow examining
        System.out.println("\n" + this.getItemDescription() + "\n");
        this.use(playerInventory, null);
    }
    
    @Override
    public void use(Inventory playerInventory, LinkedList playerInputQueue){
        if(!bp && (!bee || !bc)){ //No blueprint and missing either component
            System.out.println("\nYou look at the flashing text on the LCD screen.");
            System.out.println("SCHEMATICS AND COMPONENTS REQUIRED!");
            if(!bee && !bc){
                System.out.println("0/2 Components Input\n");
            } else {
                System.out.println("1/2 Components Input\n");
            }
        } else if(bp && (!bee || !bc)){ //No bee or bungee cord
            System.out.println("\nYou look at the flashing text on the LCD screen.");
            System.out.println("COMPONENTS REQUIRED!");
            if(!bee && !bc){
                System.out.println("0/2 Components Input\n");
            } else {
                System.out.println("1/2 Components Input\n");
            }
        } else if(!bp && (bee && bc)){ //No blueprint
            System.out.println("\nYou look at the flashing text on the LCD screen.");
            System.out.println("SCHEMATICS REQUIRED!\n");
        } else if (bp && bee && bc){
            System.out.println("\nYou look at the flashing text on the LCD screen.");
            System.out.println("SYSTEM READY, POWER REQUIRED!\n");
        }
    }
    
    @Override
    public void interact(Item item){
        if(item.getItemName().equalsIgnoreCase("BEE")){ //Input Bee
            bee = true;
            checkDone();
            GameSettings.gameGrid.getPlayerInventory().removeFromInventory(item);
        } else if (item.getItemName().equalsIgnoreCase("BUNGEE CORD")){ //Input Bungee Cord
            bc = true;
            checkDone();
            GameSettings.gameGrid.getPlayerInventory().removeFromInventory(item);
        } else if (item.getItemName().equalsIgnoreCase("BLUEPRINT")){ //Input Blueprint
            bp = true;
            checkDone();
            GameSettings.gameGrid.getPlayerInventory().removeFromInventory(item);
        }
    }
    
    private void checkDone(){
        //Check if device is ready
        Scanner sc = new Scanner(System.in);
        if(bee && bc && bp){
            ready = true;
            System.out.println("\nThe LCD screen beeps a ready signal! Something small popped out of the\n" +
                    "side, maybe I should examine the WDD again!");
            System.out.println("\nPress Enter...");        //Return page
            sc.nextLine();                                          //Wait for Enter
            //Distribute items inside
            super.examine(GameSettings.gameGrid.getPlayerInventory());
        }
    }
}
