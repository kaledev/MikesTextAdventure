package com.mikekale.mikestextadventure;

import com.mikekale.mikestextadventure.items.Door;
import com.mikekale.mikestextadventure.items.Key;
import com.mikekale.mikestextadventure.items.Window;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Room Class
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Room implements java.io.Serializable{
    private HashSet<Door> doors = new HashSet();            //List of where doors are in this room (direction/door)
    private HashSet<Window> windows = new HashSet();        //List of where windows are in this room (direction/door)
    private final String name;                              //Name of room
    private String description;                             //Description of room
    private HashSet<Item> items = new HashSet();            //Items in the room
    private boolean visited = false;                        //Room has been visited by the player
    
    /**
    * Creates a new room with description
    * @param name  name of the room
    * @param description  description of the room
    */
    Room(String name, String description){
        this.name = name;
        this.description = description;
    }

    /**
     * Get the name of the room
     * @return  name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * Adds an item into the room
     * @param item  item to be added into the room
     */
    public void addItem(Item item){
        //Check for dupes
        for(Item i: items){
            if(i.getItemName().equalsIgnoreCase(item.getItemName())){
                System.out.println("Programming Error: Rooms must have uniquely named items!");
                System.exit(0);
                return;
            }
        }
        
        //Add item to room
        items.add(item);
        item.setRoomLocation(this); //Tell item what room this is
    }
    
    /**
     * Removes an item from inside the room
     * @param item  remove item from the room
     */
    public void removeItem(Item item){
        items.remove(item);
        item.setRoomLocation(null);
    }
    
    /**
     * Get an item from inside the room
     * @param item  string representation of item to get from the room
     * @return item if found, null if not found
     */
    public Item getItem(String item){
        //Get item from room
        for(Item i : items){
            if(i.getItemName().equalsIgnoreCase(item)){
                return i;
            }
        }
        
        //not found
        return null;
    }
    
    /**
     * Check to see if a given item is a room element<br>
     * AND that it exists in the room
     * @param item  string representation of item to check
     * @return true if found
     */
    public boolean itemIsRoomElement(String item){
        //Check to see if user has entered a valid room element
        RoomElement.RoomElements roomElement = null;
        
        //Valid element syntax
        try {
            roomElement = roomElement.valueOf(item);
        } catch(IllegalArgumentException e){
            return false;
        }
        
        //Element exists in the room
        switch(roomElement){
            case DOOR:
                return !doors.isEmpty();
            case WINDOW:
                return !windows.isEmpty();
        }
        
        return true;
    }
    
    /**
     * Get a room element from the given item and direction of room element
     * @param item  string representation of room element to get
     * @param direction  direction of room element
     * @return room element if found, null if not found
     */
    public Item getRoomElement(String item, Commands.Directions direction){
        //Get room element based on direction
        RoomElement.RoomElements roomElement = null;
        
        //Make sure it's a valid room element
        try {
            roomElement = roomElement.valueOf(item);
        } catch(IllegalArgumentException e){
            return null;
        }
        
        switch(roomElement){
            case DOOR:
                for (Door d : doors){
                    if(d.getDirection().equals(direction)){
                        //Return door if exists
                        return d;
                    }
                }
                return null;
            case WINDOW:
                for (Window w : windows){
                    if(w.getDirection().equals(direction)){
                        //Return door if exists
                        return w;
                    }
                }
                return null;
        }
        
        return null;
    }
    
    /**
     * Get room element based the room element and direction
     * @param roomElement  room element enum
     * @param direction  direction of room element
     * @return room element
     */
    public RoomElement getRoomElement(RoomElement.RoomElements roomElement, Commands.Directions direction){
        
        switch(roomElement){
            case DOOR:
                for (Door d : doors){
                    if(d.getDirection().equals(direction)){
                        //Return door if exists
                        return d;
                    }
                }
                return null;
            case WINDOW:
                for (Window w : windows){
                    if(w.getDirection().equals(direction)){
                        //Return door if exists
                        return w;
                    }
                }
                return null;
        }
        
        return null;
    }
    
    /**
     * Check to see if an item is located in the room
     * @param item  string representation of item to check
     * @return true if found
     */
    public boolean itemIsInRoom(String item){
        //Is this item in the room
        
        for(Item i : items){
            if(i.getItemName().equalsIgnoreCase(item)){
                return true;
            }
        }

        return false;
    }
    
    /**
     * See if an item can interact with a room element
     * @param roomElementToInteractWith  room element
     * @param item  item
     * @return true if can interact
     */
    public boolean canInteractWithRoomElement(String roomElementToInteractWith, Item item){
        //Check to see if user has entered a valid room element
        RoomElement.RoomElements roomElement = null;
        
        //Valid element syntax
        try {
            roomElement = roomElement.valueOf(roomElementToInteractWith);
        } catch(IllegalArgumentException e){
            return false;
        }
        
        //Interact with room element
        switch(roomElement){
            case DOOR:
                //Interact with Door
                for(Door d : doors){
                    if (item.interactsWith(d)){
                        return true;
                    }
                }
            case WINDOW:
                //Interact with Window
                for(Window w : windows){
                    if (item.interactsWith(w)){
                        return true;
                    }
                }
        }
        
        return false;
    }
    
    /**
     * Unlock a room element given the element and direction<br>
     * Used for keys
     * @param key  key to attempt to unlock with
     * @param roomElement  string representation of room element to unlock
     * @param direction  direction of room element
     * @return true if unlocked
     */
    public boolean unlockRoomElement(Key key, RoomElement.RoomElements roomElement, Commands.Directions direction){
        //Unlock room element
        
        switch(roomElement){
            case DOOR:
                //Find door and check if it's even locked or can be unlocked with this key
                for(Door d : doors){
                    if(d.getDirection().equals(direction) && d.itemIsLocked()){
                        //Unlock door
                        if (d.getKeyUnlocks().equals(key) && d.unlockItem(key)){
                            System.out.println("\nThe DOOR to the " + direction + " has been unlocked!\n");
                            return true;
                        }
                    }
                }
            //In case of future room elements that can be unlocked...
        }
        
        return false;
    }

    /**
     * Create a new door in the room given a direction
     * @param newDoor  door to be created
     * @param direction  direction to place door
     */
    public void newDoor(Door newDoor, Commands.Directions direction){
        //Add new doors to the room

        //Make sure the key doesn't exist
        for(Door d : doors){
            if (d.getDirection().equals(direction)){
                System.out.println("\nProgramming Error: Already a door here!\n");
                System.exit(0);
                return;
            }
        }
        
        newDoor.setDirection(direction);    //Set direction of door
        newDoor.setRoomLocation(this);      //Set room location of door
        doors.add(newDoor);                 //Add to room
    }
    
    /**
     * Create a new window in the room given a direction
     * @param newWindow  window to be created
     * @param direction  direction to place window
     */
    public void newWindow(Window newWindow, Commands.Directions direction){
        //Add new windows to the room
        
        //Make sure the key doesn't exist
        for(Window w : windows){
            if (w.getDirection() .equals(direction)){
                System.out.println("\nProgramming Error: Already a window here!\n");
                System.exit(0);
                return;
            }
        }
        
        newWindow.setDirection(direction);    //Set direction of door
        newWindow.setRoomLocation(this);      //Set room location of door
        windows.add(newWindow);               //Add to room
    }

    /**
     * Get a door given the direction
     * @param direction  direction of door desired
     * @return door if found, null if not found
     */
    public Door getDoor(Commands.Directions direction){
        for(Door d: doors){
            if(d.getDirection().equals(direction)){
                return d;
            }
        }
        return null;
    }
    
    /**
     * Check to see if a door exists in the given direction
     * @param direction  direction of door to check
     * @return true if found
     */
    public boolean checkForDoor(Commands.Directions direction){
        for (Door d : doors){
            if (d.getDirection().equals(direction)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Set that a room has been visited
     * @param visited  true or false
     */
    public void setVisited(boolean visited){
        this.visited = visited;
    }
    
    /**
     * Determine if a room has been visited
     * @return true if visited
     */
    public boolean hasBeenVisited(){
        return visited;
    }

    /**
     * Print out information about the current room
     * @return information about the current room
     */
    public String info(){
        //Look at what is in the room
        String info;
        ArrayList<Commands.Directions> directions = new ArrayList<>();
        int directionSize = 0;
        
        if(description == null){
            info = "\nYour location: " + name + "\n";
        } else {
            info = "\nYour location: " + name + ".\n" + description + "\n";
        }
        
        //-- Print out items with location descriptions
        for(Item i : items){
            if(i.getItemLocationDescription() != null){
                info += i.getCurVerbForm() + i.getItemName() + " " + i.getItemLocationDescription() + "\n";
            }
        }
        
        //--- Print out doors
        if(doors.isEmpty()) { 
            //No doors
            info += "There appears to be no doors in this room.\n";
        } else {         
            for(Door d : doors){ //Iterate through doors
                directions.add(d.getDirection());
                directionSize++;
            }
            info += "There is a DOOR to the ";
            for(Commands.Directions d : directions){
                directionSize--;
                if(directionSize == 0 && directions.size() > 1) {
                    info += ", and " + d.name() + ".\n";
                } else if (directionSize == 1) {
                    info += d.name();
                } else if(directions.size() == 1){
                    info += d.name() + ".\n";
                } else {
                    info += d.name() + ", ";
                }
            }
            directions.clear();
        }
        
        //-- Print out windows
        if(windows.isEmpty()) { 
            //No windows
            info += "There appears to be no windows in this room.\n";
        } else {         
            for(Window w : windows){ //Iterate through windows
                directions.add(w.getDirection());
                directionSize++;
            }
            info += "There is a WINDOW to the ";
            for(Commands.Directions d : directions){
                directionSize--;
                if(directionSize == 0 && directions.size() > 1) {
                    info += ", and " + d.name() + ".\n";
                } else if (directionSize == 1) {
                    info += d.name();
                } else if(directions.size() == 1){
                    info += d.name() + ".\n";
                } else {
                    info += d.name() + ", ";
                }
            }
        }
        return info;
    }
}