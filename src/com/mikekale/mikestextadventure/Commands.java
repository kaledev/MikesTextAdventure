package com.mikekale.mikestextadventure;

import com.mikekale.mikestextadventure.commands.Transport;
import com.mikekale.mikestextadventure.commands.Combine;
import com.mikekale.mikestextadventure.commands.Examine;
import com.mikekale.mikestextadventure.commands.Walk;
import com.mikekale.mikestextadventure.commands.Save;
import com.mikekale.mikestextadventure.commands.Use;
import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * Menu options available to user
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-16 01:43:47 -0400 (Sun, 16 Mar 2014) $
 * $LastChangedRevision: 31 $
 */
public enum Commands {
    //** Commands ***  
    // <Command> (enabled, visible, whatUse, description)
    // enabled = command works
    // visible = command shows in the menu
    // whatUse = how do you use it
    // description = what it does
    //***************
    
    /**
     * Walk Command: Moves the player
     */
        
    WALK (true, true, "USE: WALK <direction>", "DESCRIPTION: Moves the player."),

    /**
     * Examine Command: Examines a room/item for more information
     */
    EXAMINE (true, true, "USE:\tEXAMINE ROOM\n\tEXAMINE <item>\n\tEXAMINE <item> <direction>", 
            "DESCRIPTION: Examine a room/item for more information."),

    /**
     * Use Command: Uses/Interacts with an item OR Uses an item ON another item.
     */
    USE (true, true, "USE:\tUSE <item>\n\tUSE <item> <direction>\n\tUSE <item> ON <item>", 
            "DESCRIPTION: Uses/Interacts with an item OR Uses an item ON another item."),

    /**
     * Combine Command: Combines two items
     */
    COMBINE (true, true, "USE: COMBINE <item> AND <item>", "DESCRIPTION: Combines two items together."),
    
    /**
     * Inventory Command: Displays inventory
     */
    INVENTORY (true, true, "USE: INVENTORY", "DESCRIPTION: Displays inventory."),

    /**
     * Map Command: Displays a map
     */
    MAP (true, true, "USE: MAP", "DESCRIPTION: Displays a map."),

    /**
     * Help Command: Displays help about a command.
     */
    HELP (true, true, "USE: HELP <command>", "DESCRIPTION: Displays help about a command."),

    /**
     * Save Command: Saves the current game and overwrites any previous save.
     */
    SAVE (true, true, "USE: SAVE", "DESCRIPTION: Saves the current game and overwrites any previous save."),

    /**
     * Exit Command: Exits the game.
     */
    EXIT (true, true, "USE: EXIT", "DESCRIPTION: Exits the game."),

    /**
     * Devmap Command: Displays a developer map - not for users
     */
    DEVMAP (true, false, "USE: DEVMAP", "DESCRIPTION: Displays a developer map."),
    
    /**
     * Devmap Command: Uses the developer transporter - not for users
     */
    DEVTRANSPORT (true, false, "USE: DEVTRANSPORT X,Y", "DESCRIPTION: Developer Command! Transports to the given X,Y coordinate.");

    /**
     * Cardinal Directions
     */
    public enum Directions {
        NORTH,
        EAST,
        SOUTH,
        WEST;
        
        /**
         * Check to see if string is direction
         * @param test  tester string
         * @return true if string is a direction
         */
        public static boolean contains(String test) {
            for (Directions dir : Directions.values()) {
                if (dir.name().equals(test)) {
                    return true;
                }
            }
            return false;
        }
    }
    
    private final String description; //Description of command
    private final String whatUse;     //Use of command
    private boolean enabled;          //Is the command enabled?
    private boolean visible;          //Is this command visible?

    Commands(boolean enabled, boolean visible, String whatUse, String description){
        this.enabled = enabled;
        this.whatUse = whatUse;
        this.description = description;
        this.visible = visible;
    }

    /**
     * Prints out all enabled commands
     * @return all enabled command values
     */
    public static String listMenuValues(){
        //Print out all enabled & visible values
	String menu = "";
        String commandValues = " - ";
        for(Commands c : Commands.values()){
            if(c.enabled && c.visible){
                commandValues += c + " - ";
            }
        }
		for(int i = 0; i < commandValues.length(); i++){
			menu += "=";
		}
		menu += "\n" + commandValues;
        return menu;
    }
    
    /**
     * Get the use of a particular command
     * @return use of command
     */
    public String getUse(){
        return this.whatUse;
    }
    
    /**
     * Check if a given command is valid
     * @param strCommand  possible command in string form
     * @return true if valid command
     */
    public boolean isValidCommand(String strCommand){
        //Check if string is a valid enum command
        Commands testCommand = null;
        try {
            testCommand = Commands.valueOf(strCommand);
        } catch(IllegalArgumentException e){
            return false;
        }
        return true;
    }
    
    /**
     * Parse a command from the input queue and use it
     * @param playerInventory  player's inventory
     * @param playerInputQueue  player's input
     */
    public void useCommand(Inventory playerInventory, LinkedList playerInputQueue){
        
        //Kick back if the command isn't enabled
        if (!this.enabled){
            System.out.println("\nError: That command is unavailable right now!\n");
            return;
        }
        
        switch (this){
            case WALK:
                /**********************
                 * Try to move player *
                 *********************/
                playerInputQueue.remove(); //Used, now remove
                try {
                    //Make sure modifiers exist
                    playerInputQueue.element();
                } catch(NoSuchElementException e){
                    System.out.println("\n" + this.getUse() + "\n");
                    return;
                }
                //Walk
                Walk walk = new Walk(playerInputQueue.remove().toString());
                break;
            case HELP:
                /****************
                 * General Help *
                 ****************/
                playerInputQueue.remove(); //Used, now remove
                
                //Make sure modifiers exist
                try {
                    playerInputQueue.element();
                } catch(NoSuchElementException e){
                    System.out.println("\n" + this.getUse() + "\n");
                    return;
                }
                
                //Check to see if user has entered valid commands
                if(!this.isValidCommand(playerInputQueue.peek().toString())){
                    System.out.println("\n" + this.getUse() + "\n");
                    return;
                }
                
                //Return help for valid command
                System.out.println("\n" + Commands.valueOf(playerInputQueue.peek().toString()).getUse() + 
                        "\n" + Commands.valueOf(playerInputQueue.peek().toString()).description + "\n");
                
                //Used, now remove
                playerInputQueue.remove();
                break;
            case MAP:
                /**********************
                 * Display Visual Map *
                 *********************/
                playerInputQueue.remove(); //Used, now remove
                System.out.println(GameSettings.gameGrid.toString());
                break;
            case INVENTORY:
                /**********************
                 * Show Inventory     *
                 *********************/
                playerInputQueue.remove(); //Used, now remove
                System.out.println(playerInventory.toString());
                break;
            case SAVE:
                /**********************
                 * Save Game          *
                 *********************/
                playerInputQueue.remove(); //Used, now remove
                Save saveGame = new Save();
                break;
            case USE:
                /**********************
                 * Use Item           *
                 *********************/
                playerInputQueue.remove(); //Used, now remove
                Use use = new Use(playerInventory, playerInputQueue);
                break;
            case EXAMINE:
                /**********************
                 * Examine Item       *
                 *********************/
                playerInputQueue.remove(); //Used, now remove
                Examine examine = new Examine(playerInventory, playerInputQueue);
                break;
            case COMBINE:
                /**********************
                 * Combine Item       *
                 *********************/
                playerInputQueue.remove(); //Used, now remove
                Combine combine = new Combine(playerInventory, playerInputQueue);
                break;
            case EXIT:
                /**********************
                 * Exits Game         *
                 *********************/
                System.exit(0);
                break;
            case DEVMAP:
                /**********************
                 * Dev Map            *
                 *********************/
                //Shows Developer Map
                playerInputQueue.remove(); //Used, now remove
                System.out.println(GameSettings.gameGrid.devMap());
                break;
            case DEVTRANSPORT:
                /**********************
                 * Dev Transport      *
                 *********************/
                //Transport the player - Developer Command!
                playerInputQueue.remove(); //Used, now remove
                Transport transport = new Transport(true);
                break;
            default:
                /**********************
                 * UNKNOWN            *
                 *********************/
                playerInputQueue.remove(); //Used, now remove
                System.out.println("\nProgramming Error: This command exists but" +
                        " has not yet been implemented!\n");
                System.exit(0);
                break;
        }
    }
}
