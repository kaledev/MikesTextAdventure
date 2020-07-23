package com.mikekale.mikestextadventure.commands;

import com.mikekale.mikestextadventure.GameSettings;
import com.mikekale.mikestextadventure.Main;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Save Class - responsible for saving the game
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-15 21:02:53 -0400 (Sat, 15 Mar 2014) $
 * $LastChangedRevision: 27 $
 */
public class Save {
    
    /**
     * Save Command
     */
    public Save(){
        //Prompt save
        Scanner savegame_sc = new Scanner(System.in);
        System.out.print("\nSaving the game will overwrite any existing saved game...\nContinue?:");
        String savegame_input = savegame_sc.nextLine();
        if(savegame_input.equalsIgnoreCase("Y") || savegame_input.equalsIgnoreCase("YES")){
            try {
                Main.gs.saveGame(GameSettings.gameGrid);
            } catch (FileNotFoundException ex) {
                System.out.println("\nError: There was a problem when attempting to save...\n");
            } catch (IOException ex) {
                System.out.println("\nError: There was a problem when attempting to save...\n");
            }
       } else {
            System.out.println("\nSave canceled.\n");
       }
    }
}
