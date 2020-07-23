package com.mikekale.mikestextadventure.commands;

import com.mikekale.mikestextadventure.Commands;
import com.mikekale.mikestextadventure.GameSettings;
import java.awt.Point;

/**
 * Walk Command
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Walk {
    
    /**
     * Walk
     * @param direction  direction to walk 
     */
    public Walk(String direction){
        /***************
         * Move Player *
         **************/
        Commands.Directions dir = null;
        //Check to see if user has entered valid directions
        try {
            dir = dir.valueOf(direction);
            //dir = 
        } catch(IllegalArgumentException e){
            System.out.println("\nError: Please choose either North, East, " +
                    "South, or West with this command!\n");
            return;
        }

        //Check for doors
        if(GameSettings.gameGrid.getPlayerRoom().checkForDoor(dir)){
            //Check if door is locked
            if(!GameSettings.gameGrid.getPlayerRoom().getDoor(dir).itemIsLocked()){
                //Check for room
                if(!GameSettings.gameGrid.checkForRoom(dir)){
                    System.out.println("\nYou run painfully into the edge of the map, " +
                            "that must have hurt...\n");
                    return;
                }
                
                //Move player
                switch(dir){
                    case NORTH:
                        GameSettings.gameGrid.setPlayerLoc(new Point(GameSettings.gameGrid.getPlayerLoc().x - 1,
                                                        GameSettings.gameGrid.getPlayerLoc().y));
                        break;
                    case EAST:
                        GameSettings.gameGrid.setPlayerLoc(new Point(GameSettings.gameGrid.getPlayerLoc().x,
                                                        GameSettings.gameGrid.getPlayerLoc().y + 1));
                        break;
                    case SOUTH:
                        GameSettings.gameGrid.setPlayerLoc(new Point(GameSettings.gameGrid.getPlayerLoc().x + 1,
                                                        GameSettings.gameGrid.getPlayerLoc().y));
                        break;
                    case WEST:
                        GameSettings.gameGrid.setPlayerLoc(new Point(GameSettings.gameGrid.getPlayerLoc().x,
                                                        GameSettings.gameGrid.getPlayerLoc().y - 1));
                        break;
                    default:
                        //Nothing
                }
                //Tell the user where they are now
                System.out.println(GameSettings.gameGrid.getPlayerRoom().info()); //Show info
            } else {
                System.out.println("\nThere's a locked DOOR in this direction!\n");
            }
        } else {
            System.out.println("\nYou run painfully into the edge of the map, " +
                    "that must have hurt...\n");
        }
    }
}
