package com.mikekale.mikestextadventure.items;

import com.mikekale.mikestextadventure.Inventory;
import com.mikekale.mikestextadventure.Item;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Book Class
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Book extends Item implements java.io.Serializable {
    //Only needs to be serializable since an instance exists outside of the gameGrid
    
    private final String title;
    private final String author;
    private String description;
    private final LinkedHashMap<Integer,String> content = new LinkedHashMap();
    
    /**
     * Create a new Book item
     * @param itemName  title
     * @param author  author
     * @param itemDescription  description
     * @param itemLocationDescription item location description
     * @param canAdd  can this be added to the inventory
     * @param locked  is this book locked?
     */
    public Book(String itemName, String author, String itemDescription, String itemLocationDescription, boolean canAdd, boolean locked){
        super(itemName, "It's a book, let me take a look here." + "\n...Title: " + itemName + 
                "\n...Author: " + author + "\n...Description: " + itemDescription, itemLocationDescription, null, canAdd, true, locked);
        
        this.title = itemName;
        this.author = author;
        this.description = itemDescription;
    }
    
    /**
     * Add a new page to the book
     * @param content  content
     */
    public void addPage(String content) {
        //Add page to book
        int pageNum = this.content.size() + 1;
        this.content.put(pageNum, content);
    }
    
    /**
     * Examine the book
     * Examining a book may consist of actually reading, so this function
     * examines first and then uses.
     * @param playerInventory  player's current inventory
     */
    @Override
    public void examine(Inventory playerInventory){
        Item tempItem = this.getItemContanedIn(); //Hold item contained in
        super.examine(playerInventory);
        if(tempItem == null && !this.itemIsLocked()){
            //Only use if this item isn't inside of another item and isn't locked
            System.out.println("You anxiously flip open the cover.");
            use(playerInventory, null);
        }
    }
    
    /**
     * Use the book
     * @param playerInventory  player's current inventory
     * @param playerInputQueue  player's input
     */
    @Override
    public void use(Inventory playerInventory, LinkedList playerInputQueue){
        Scanner sc = new Scanner(System.in);
            
        if(!itemIsLocked()){
            Iterator it = content.entrySet().iterator();  //Iterate through pages
            System.out.println("");

            while(it.hasNext()){
                Map.Entry pairs = (Map.Entry)it.next();                 //Pull values out of hash
                String page = (String)content.get(pairs.getKey());      //Page
                int pageNum = (int)pairs.getKey();                      //Page Number
                System.out.println(page + "\n\nPress Enter...");        //Return page
                sc.nextLine();                                          //Wait for Enter
            }
        } else {
            System.out.println("\nAppears to be locked!\n");
        }
    }
}
