package com.mikekale.mikestextadventure;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Main Class
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-19 18:55:30 -0400 (Wed, 19 Mar 2014) $
 * $LastChangedRevision: 32 $
 */
public class Main {
    private static boolean win = false;
    private Scanner sc;
    private GameSettings gameSettings;
    private LinkedList<String> playerInputQueue;
    public static GameStatus gs = new GameStatus();

    /**
     * New Instance of Mike's Text Adventure.
     */
    Main(){
        this.sc = new Scanner(System.in);
        this.gameSettings = new GameSettings();
    }

    /**
     * Main Function, displays start screen
     * @param args  command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.startScreen();
    }
    
    /**
     * Initial Start Screen
     * @throws java.io.IOException 
     */
    public void startScreen() throws java.io.IOException{
        Scanner stc = new Scanner(System.in);    //Player Input
        int input = 0;                           //Player Input
        boolean validInput = false;              //Valid Input
        
        System.out.println("\nMike's Text Adventure\n");
        
        //Check for saved game
        if(gs.saveExists()) {
            while(!validInput){ //Force valid input
                try {
                    System.out.print("1. CONTINUE\n2. NEW\n3. EXIT\n\nSelection: ");
                    input = Integer.parseInt(stc.nextLine());
                    if (input == 1 || input == 2 || input ==3 ){
                        validInput = true;
                    } else {
                        System.out.println("\nInvalid Input!\n");
                    }
                } catch (NumberFormatException e){
                    System.out.println("\nInvalid Input!\n");
                }
            }
        } else {
            while(!validInput){ //Force valid input
                try {
                    System.out.print("1. NEW\n2. EXIT\n\nSelection: ");
                    input = Integer.parseInt(stc.nextLine());
                    if (input == 1 || input == 2){
                        validInput = true;
                    } else {
                        System.out.println("\nInvalid Input!\n");
                    }
                } catch (NumberFormatException e){
                    System.out.println("\nInvalid Input!\n");
                }
            }
        }
        
        switch(input){
            case 1:
                if(!gs.saveExists()){
                    //New Game
                    gameSettings.newGame();
                    gameLoop();
                } else {
                    //Continue
                    GameSettings.gameGrid = gs.loadGame();
                    if (GameSettings.gameGrid == null){ //Problem loading game
                        System.out.println("Exiting game...");
                        System.exit(1); //Exit the game
                    }
                    gameLoop();
                }
                break;
            case 2:
                //New or Exit
                if(gs.saveExists()){
                    //New Game
                    gameSettings.newGame();
                    gameLoop();
                } else {
                    //Exit
                    System.exit(0);
                }
                break;
            case 3:
                //Exit
                System.exit(0);
                break;
        }
    }

    /**
     * Game Loop
     */
    private void gameLoop() throws IOException{
        //Main game loop
        String playerInput;

        while(!win){
            //--Display and check menu input
            System.out.println(Commands.listMenuValues());
            System.out.print("Command: ");
            playerInput = sc.nextLine();
            //for( int i = 0; i < 25; i++ ) {
            //    System.out.println(""); //Clear screen
            //}
            checkInput(playerInput);
            //------------------------------
            
            //Won the game!
            if(win){
                System.out.println("Congratulations, you've won! Well... sort of. I guess in reality you\n" +
                    "were just attacked by a giant mutant insect. However, the feeling of\n" +
                    "accomplishment can still exist in your heart. Feel free to continue to\n" +
                    "explore this fine example of a text-based adventure game.");
                System.out.print("\nPress Enter...\n");
                sc.nextLine();
                setWin(false);
            }
        }
    }
    
    /**
     * Set win
     * @param win true or false 
     */
    public static void setWin(boolean win){
        Main.win = win;
    }

    /**
     * Check the input of the player
     * @param input  string representation of player's input
     */
    private void checkInput(String input){
        Commands command = null;
        String delims = " ";                      //Delimiter to split
        String[] tokens = input.split(delims);    //Split up input
        playerInputQueue = new LinkedList<>();    //Reset input queue
        
        for (String token : tokens) {
            //Put inputs in queue
            playerInputQueue.add(token.toUpperCase());
        }

        //Check to see if user has entered valid commands
        try {
            command = command.valueOf(playerInputQueue.peek());
        } catch(IllegalArgumentException e){
            System.out.println("\nError: Please choose a valid command!\n");
            return;
        }
        
        //Use the command selected
        command.useCommand(GameSettings.gameGrid.getPlayerInventory(), playerInputQueue);
    }
}
