package com.mikekale.mikestextadventure.commands;

import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Room;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Transports the player
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Transport {
    
    /**
     * Transport the player by selection of room
     * @param devOverride  displays all rooms and ignores if the player has visited it
     */
    public Transport(boolean devOverride){
        Scanner sc = new Scanner(System.in);
        int input;
        int i = 1;
        HashMap<Point,Room> rooms = GameSettings.gameGrid.getRooms();
        ArrayList<String> roomNames = new ArrayList<>();
        
        //Put room names into array
        for (Map.Entry pairs : rooms.entrySet()) {
            Room tempRoom = (Room)rooms.get(pairs.getKey());    //Room
            if(tempRoom.hasBeenVisited() || devOverride){
                roomNames.add(tempRoom.getName());
            }
        }
        
        //Sort the list of items
        Collections.sort(roomNames);
        
        System.out.println(""); //Spacing
        
        for (String rn : roomNames) {
            System.out.println(i + ": " + rn); //Print room names
            i++;
        }
        
        i = 1; //Reset
        
        System.out.print("Choose room: ");
        try {
            input = Integer.parseInt(sc.nextLine());
            if (input > 0 && input <= roomNames.size()){
                //Transport player
                for (String rn : roomNames) {
                    if(input == i) {
                        System.out.println("\nTransported to " + rn + "\n");
                        for (Map.Entry pairs : rooms.entrySet()) {
                            Room tempRoom = (Room)rooms.get(pairs.getKey());    //Room
                            Point tempPoint = (Point)pairs.getKey();            //Pointer of room
                            if (tempRoom.getName().equalsIgnoreCase(rn)){
                                GameSettings.gameGrid.setPlayerLoc(tempPoint);  //Transport
                                return;
                            }
                        }
                        return;
                    }
                    i++;
                }
            } else {
                System.out.println("\nDevice Error: Incorrect input!\n");
                return;
            }
        } catch (NumberFormatException e){
            System.out.println("\nDevice Error: Incorrect input!\n");
            return;
        }
    }
}
