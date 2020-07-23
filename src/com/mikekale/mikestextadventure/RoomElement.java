package com.mikekale.mikestextadventure;

/**
 * Room element
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class RoomElement extends Item {
    
    public enum RoomElements implements java.io.Serializable { 
        DOOR, WINDOW;

        /**
         * Check to see if string is a room element
         * @param test  tester string
         * @return true if string is a reserved name
         */
        public static boolean contains(String test) {
            for (RoomElements re : RoomElements.values()) {
                if (re.name().equals(test)) {
                    return true;
                }
            }
            return false;
        }
    }  //Room elements

    private final RoomElement.RoomElements roomElementType; //Room element type
    private Commands.Directions direction;                  //Direction this room element holds

    /**
     * Create a new room element
     * @param roomElementType  type of room element
     * @param itemName  room element name
     * @param itemDescription  room element description
     * @param itemLocationDescription  room element location description
     * @param useText  text displayed when using, normally overridden
     * @param lockable  can this room element be locked
     * @param locked  is this room element locked
     */
    public RoomElement(RoomElement.RoomElements roomElementType, String itemName, String itemDescription, 
            String itemLocationDescription, String useText, boolean lockable, boolean locked) {
        super(itemName, itemDescription, itemLocationDescription, useText, false, lockable, locked);
        this.roomElementType = roomElementType;
        this.setRoomElement(true);
    }
    
    /**
     * Set direction when placed in a room
     * @param direction  direction of room element
     */
    public void setDirection(Commands.Directions direction){
        this.direction = direction;
    }
    
    /**
     * Get direction of the room element in the room
     * @return  direction of the room element, null if not placed in a room
     */
    public Commands.Directions getDirection(){
        return direction;
    }
}
