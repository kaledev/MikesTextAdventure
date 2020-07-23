package com.mikekale.mikestextadventure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Game Status - responsible for saving/loading the game
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-19 20:59:46 -0400 (Wed, 19 Mar 2014) $
 * $LastChangedRevision: 33 $
 */
public class GameStatus {
    
    private final String filename = "savegame";   //Savegame filename

    /**
     * Saves the game
     * @param gameGrid  game grid object
     * @throws FileNotFoundException  cannot find file
     * @throws IOException  cannot read file
     */
    public void saveGame(GameGrid gameGrid) throws FileNotFoundException, IOException{
        // Save the object to file
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
          fos = new FileOutputStream(filename);
          out = new ObjectOutputStream(fos);
          out.writeObject(gameGrid);

          out.close();
        } catch (IOException ex) {
            System.out.println("\nError: There was a problem when attempting to save...\n");
        }
        
        System.out.println("\nGame Saved!\n");
    }
    
    /**
     * Loads the game
     * @return loaded game
     */
    public GameGrid loadGame(){
        // Read the object from file
        // Save the object to file
        GameGrid gameGrid = null;
        
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
          fis = new FileInputStream(filename);
          in = new ObjectInputStream(fis);
          gameGrid = (GameGrid) in.readObject();
          in.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nError: Couldn't find the saved game file!\n");
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("\nError: There was an issue when attempting to load the game.\n");
            return null;
        }
        
        System.out.println("\nGame Loaded!\n");
        return gameGrid;
    }
    
    /**
     * Check if a save already exists
     * @return true if savegame file found
     */
    public boolean saveExists(){
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            return false;
        }
        return true;
    }
}
