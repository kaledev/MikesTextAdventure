package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.Inventory;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Bunker door - requires a code to open
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class BunkerDoor extends Door{
    
    private int code = 0; //Code to unlock
    
    /**
     * Create a new bunker door
     * @param doorDescription  description of door
     * @param code  code to open door
     */
    public BunkerDoor(String doorDescription, int code) {
        super(doorDescription, true);
        this.code = code;
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
            System.out.println("\nThe door is locked! An electronic pad is awaiting input.");
            Scanner sc = new Scanner(System.in);
            try {
                System.out.print("Input code for entry: ");
                int input = Integer.parseInt(sc.nextLine());
                if (input == code){
                    //Correct!
                    System.out.println("\nSUCCESS!\nThe door clicks open.\n");
                    this.unlockItem();
                } else {
                    System.out.println("\nInvalid Input!\nA mild electrical shock shoots through your body.\n");
                }
            } catch (NumberFormatException e){
                System.out.println("\nInvalid Input!\nA mild electrical shock shoots through your body.\n");
            }
        } else {
            if(!isOpened()){
                setOpened(true);
                System.out.println("\nYou open the door.\n");
            } else {
                setOpened(false);
                System.out.println("\nYou close the door.\n");
            }
        }
    }
}
