package com.mikekale.mikestextadventure;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * Game grid
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class GameGrid implements java.io.Serializable {
    private final int[][] GRID;                             //Grid size
    private final int ROWS;                                 //Rows on Grid
    private final int COLS;                                 //Cols on Grid
    private HashMap<Point,Room> rooms = new HashMap();      //Rooms on Grid
    private Point playerLoc = new Point();                  //Player location on Grid
    private Inventory playerInventory = new Inventory();    //Player's Inventory

    /**
     * Create a new game grid
     * @param ROWS  Rows on Grid
     * @param COLS  Cols on Grid
     */
    public GameGrid(int ROWS, int COLS){
        this.ROWS = ROWS;
        this.COLS = COLS;

        //Setup grid size
        this.GRID = new int[ROWS][COLS];
        //Set to default (no rooms)
        for(int x = 0; x < ROWS; x++){
            for(int y = 0; y < COLS; y++){
                GRID[x][y] = 0;
            }
        }
    }

    /**
     * Adds a room object to the grid
     * @param x  x-coordinate of room
     * @param y  y-coordinate of room
     * @param room  room to be added to grid
     */
    public void addToGrid(int x, int y, Room room){
        //Check for dupe names
        if(rooms.containsValue(room)){
            System.out.println("Programming Error: There is already a room on the " +
                        "grid with this name!");
            System.exit(0);
        }
        
        //Add room to grid, store in hashmap with point
        Point roomPoint = new Point(x,y);
        try {
            if(!rooms.containsKey(roomPoint)){ //Make sure the key doesn't exist
                rooms.put(roomPoint, room);
                GRID[x][y] = 1; //Set as 'occupied'
            } else {
                System.out.println("Programming Error: There is already a room in " +
                    "this location!");
                System.exit(0);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Programming Error: Can't place a room out of bounds!");
            System.exit(0);
        }
    }

    /**
     * Returns the room that the player is currently in
     * @return player's current room location, null if no room found
     */
    public Room getPlayerRoom() {
        //Return the room the player is in
        Room playerRoom = null;
        try {
            playerRoom = (Room)rooms.get(playerLoc);
        } catch(NullPointerException e){
            System.out.println("Programming Error: There is no room at these" +
                    " coordinates!");
            System.exit(0);
            return null;
        }
        return playerRoom;
    }

    /**
     * Returns the X,Y position of the player
     * @return player's current X,Y position
     */
    public Point getPlayerLoc(){
        return playerLoc;
    }

    /**
     * Sets the players location on the grid
     * @param playerLoc  X,Y location for player
     */
    public void setPlayerLoc(Point playerLoc) {
        if(playerLoc.x > ROWS || playerLoc.y > COLS){
            System.out.println("Programming Error: Player located off Grid!");
            System.exit(0);
        } else {
            if(hasRoom(playerLoc)) {
                this.playerLoc = playerLoc;
            } else {
                System.out.println("Programming Error: Player has to be placed in a room!");
                System.exit(0);
            }
        }
        
        //Set visited flag to true
        for (Map.Entry pairs : rooms.entrySet()) {
            Room tempRoom = (Room)rooms.get(pairs.getKey());    //Room
            Point tempPoint = (Point)pairs.getKey();            //Pointer of room
            if (tempPoint.equals(playerLoc)){
                tempRoom.setVisited(true);                      //Set true
            }
        }
    }
    
    /**
     * Prints out the game grid
     * @return world map
     */
    @Override
    public String toString(){
        String gameMap = "\nWORLD MAP\nPlayer Location: " + getPlayerRoom().getName() + "\n\n";

        //Game Map
        for(int x = 0; x < ROWS; x++){
            for(int y = 0; y < COLS; y++){
                if(playerLoc.x == x && playerLoc.y == y){
                    //Mark player location
                    gameMap += "[P]";
                } else {
                    if (GRID[x][y] == 0) {
                        gameMap += "   "; //On the grid, but no room exists
                    } else {
                        gameMap += "[ ]";
                    }
                }
            }
            gameMap += "\n";
        }

        //Key
        gameMap += "\nKey:\n" +
                "P = Player\n" +
                "[] = Room\n";
        return gameMap;
    }

    /**
     * Prints out a developer map
     * @return world map, developer version
     */
    public String devMap(){
        String gameMap = "\nWORLD MAP\nPlayer Location: " + getPlayerRoom().getName() + "\n\n";

        //Game Map
        for(int x = 0; x < ROWS; x++){
            for(int y = 0; y < COLS; y++){
                if(playerLoc.x == x && playerLoc.y == y){
                    //Mark player location
                    gameMap += "[P," + GRID[x][y] + "]";
                } else {
                    if (playerLoc.y == y) {
                        gameMap += "[ " + GRID[x][y] + " ]";
                    } else {
                        gameMap += "[" + GRID[x][y] + "]";
                    }
                }
            }
            gameMap += "\n";
        }

        //Key
        gameMap += "\nKey:\n" +
                "P = Player\n" +
                "[] = Room\n" +
                "0,1 = Room/No Room";
        gameMap += "\n\nPlayer Position:\n" + playerLoc.getLocation() + "\n";
        return gameMap;
    }
    
    /**
     * Return HashMap of rooms
     * @return HashMap of rooms
     */
    public HashMap getRooms(){
        return rooms;
    }
    
    /**
     * See if room exists at point
     * @param point  test pointer
     * @return true if room exists
     */
    public boolean hasRoom(Point point){
        return rooms.containsKey(point);
    }
    
    /**
     * Check if a room exists on the grid when moving a specific direction
     * @param direction  direction of room
     * @return true if room found in direction given
     */
    public boolean checkForRoom(Commands.Directions direction){
        switch(direction){
            case NORTH:
                if(rooms.containsKey(new Point(playerLoc.x - 1, playerLoc.y)))
                    return true;
                break;
            case EAST:
                if(rooms.containsKey(new Point(playerLoc.x, playerLoc.y + 1)))
                    return true;
                break;
            case SOUTH:
                if(rooms.containsKey(new Point(playerLoc.x + 1, playerLoc.y)))
                    return true;
                break;
            case WEST:
                if(rooms.containsKey(new Point(playerLoc.x, playerLoc.y - 1)))
                    return true;
                break;
            default:
                return false;
        }
        return false;
    }
    
    /**
     * Returns the players inventory
     * @return player's current inventory
     */
    public Inventory getPlayerInventory(){
        //Get player inventory
        return playerInventory;
    }
}
