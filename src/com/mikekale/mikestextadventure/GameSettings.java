package com.mikekale.mikestextadventure;

import com.mikekale.mikestextadventure.RoomElement.RoomElements;
import com.mikekale.mikestextadventure.items.*;
import java.awt.Point;

/**
 * Main file for creating new games<br>
 * Holds grid settings and room objects
 * @author Mike Kale (kaledev@gmail.com)
 * $LastChangedDate: 2014-03-19 18:55:30 -0400 (Wed, 19 Mar 2014) $
 * $LastChangedRevision: 32 $
 */
public class GameSettings {
    public static GameGrid gameGrid;                        //Gamegrid
    public final static int ROWS = 4;                       //Rows in gamegrid
    public final static int COLS = 10;                      //Cols in gamegrid
    public final static Point START_LOC = new Point(0,0);   //Starting room for player
    public final static int CONSOLELEN = 80;                //Limit of text length on the console
    private Book startScreenBook;                           //'Book' Displayed at the beginning of the game
    
    /**
     * Defines a new GameGrid
     */
    public GameSettings(){
        gameGrid = new GameGrid(ROWS, COLS);
    }
    
    /**
     * Starts a new game
     */
    public void newGame(){
        //**********************
        // START MASTER BEDROOM
        //**********************
        
        Room masterBedroom = new Room("Master Bedroom", "A large furnished bedroom with a dark wood floor.\n"
                + "A FAN spins lazily overhead, and a large BED sits in the middle.\n"
                + "At the foot of the bed sits a small wooden CHEST.");
        
        //Doors
        masterBedroom.newDoor(new Door("A wooden door, leads outside of the bedroom.", true),
                Commands.Directions.EAST);
        masterBedroom.newDoor(new Door("A wooden door, leads to the bathroom.", false),
                Commands.Directions.SOUTH);
        
        //Windows
        masterBedroom.newWindow(new Window("A window, you see SMALL WOODLAND CREATURES in the distance."), Commands.Directions.NORTH);
        
        //Items
        masterBedroom.addItem(new Item("FAN", "A small ceiling fan.", null, "You stick your hand in the blades and scream.", 
                false, false, false));
        masterBedroom.addItem(new Item("BED", "A king sized bed, quilted and comfy.", null,
                "You cannon-ball onto the bed, bouncing off and into the wall.", false, false, false));
        masterBedroom.addItem(new Item("SMALL WOODLAND CREATURES", "I see a few squirrels, maybe a caribou?", null,
                "You scream at the small woodland creatures, whom prompty scatter.", false, false, false));
        
        Book marvinDiary = new Book("MARVINS DIARY 1", "Marvin McStevens", "The diary of Marvin McStevens, Part 1", null, true, false);
        marvinDiary.addPage("7/15/05\n" + 
                "Dear diary,\nI'm pretty sure that Pete is plotting against me.\n" + 
                "We've lived together for the past 3 years, and I thought we were\n" + 
                "always on the same page, but lately the negativity has been too\n" + 
                "much to bare. Sure, world domination can be a tricky but that doesn't\n" + 
                "mean that it can't be done. Plus, my methods are proven. I've run\n" +
                "dozens of simulations and the results are beyond promising.");
        marvinDiary.addPage("It's not that Pete has said anything per se, but I know.\n" + 
                "As a matter of fact, Pete is a little too quiet to be honest.\n" + 
                "I've made plans to test his resolve and loyalty. The problem is that\n" + 
                "day zero is fast approaching. The machine is nearly complete and if\n" + 
                "Pete has switched sides he may attempt to sabotage my plans. As a precaution\n" +
                "I've ordered a new bunker door. I hope that I'm wrong about this.");
        Key MBKey = new Key("MASTER KEY", "A key, looks like it unlocks the Master Bedroom door.", 
                null, true, masterBedroom, RoomElement.RoomElements.DOOR, Commands.Directions.EAST);        
        Item MBChest = new Item("CHEST", "A small wooden chest at the foot of the bed.", null, 
                "There might be something in here, maybe you should examine it!", false, true, true);
        MBChest.putItemInside(marvinDiary, MBKey);
        masterBedroom.addItem(MBChest);
        //Add room to Grid
        gameGrid.addToGrid(0, 0, masterBedroom);
        
        //**********************
        // END MASTER BEDROOM
        //**********************
        
        //**********************
        // START MASTER BATHROOM
        //**********************

        Room masterBathroom = new Room("Master Bathroom", "Smells like chicken for some odd reason.\nThere is a TOILET, and a SHOWER... looks like a bathroom.");
        
        //Doors
        masterBathroom.newDoor(new Door("A wooden door, leads to the bedroom.", false),
                Commands.Directions.NORTH);
        
        //Windows
        masterBathroom.newWindow(new Window("A glazed window... for those private moments."), Commands.Directions.WEST);
        
        //Items
        masterBathroom.addItem(new Item("TOILET", "The Toiletto 3000", null, "Ahhhhh yesss...", false, false, false));
        Item shower = new Item("SHOWER", "Looks like it's been used it recently, water is dripping from the sides.", 
                null, "I'm clean enough.", false, false, false);
        masterBathroom.addItem(shower);
        Item towel = new Item("TOWEL", "A large blue towel. Never, ever, forget your towel.", 
                null, "Ah yes, Fluffy. This was a good decision.", true, false, false);
        shower.putItemInside(towel);
        
        Key MBChestKey = new Key("SMALL KEY", "A key; tiny in it's stature, and shiny to boot.", "laying on the floor.", true, MBChest);
        masterBathroom.addItem(MBChestKey);
        
        //Add room to Grid
        gameGrid.addToGrid(1, 0, masterBathroom);
        
        //**********************
        // END MASTER BATHROOM
        //**********************
        
        //**********************
        // START HALLWAY
        //**********************

        Room hallway = new Room("Hallway", "It leads to another room.");
        
        //Doors
        hallway.newDoor(new Door("A wooden door, leads to the bedroom.", false),
                Commands.Directions.WEST);
        hallway.newDoor(new Door("A wooden door, leads into an office.", false),
                Commands.Directions.EAST);
				
	//Windows
        hallway.newWindow(new Window("A TREE sways in the wind."), Commands.Directions.NORTH);
        
        //Items
        Book wddfb = new Book("STRANGE BOOK", "Marvin McStevens", "'World Domination Device Football', one of the only books I've\n ever seen with a lock!", 
                "laying on the ground.", true, true);
        wddfb.addPage("I've decided to write my final instructions in a more secured\n" + 
                "location that can only be accessed when the WDD is prepped and ready.\n" + 
                "The Secure Room must be accessed in order to power the device. With\n" + 
                "the power securely sectioned off, there is no way that Pete can run\n" + 
                "the device himself and take the glory. I've set the code to 844655.\n" +
                "Glory awaits!");
        hallway.addItem(wddfb);
        hallway.addItem(new Item("TREE", "Colorful leaves, looks to be fall.", null, "I'm not in need of any firewood.", false, false, false));
                
        //Add room to grid
        gameGrid.addToGrid(0, 1, hallway);
        
        //**********************
        // END HALLWAY
        //**********************
        
        //**********************
        // START OFFICE
        //**********************

        Room office = new Room("Office", "A professional looking office.\nI see a COMPUTER, a DESK, and a small PICTURE on the wall.");
        
        //Doors
        office.newDoor(new Door("A wooden door, leads to the hallway.", false),
                Commands.Directions.WEST);
        office.newDoor(new Door("A wooden door, leads to the entryway.", false),
                Commands.Directions.SOUTH);
        
        //Windows
        office.newWindow(new Window("A beautiful fall morning."), Commands.Directions.NORTH);
        
        //Items
        Item computer = new Item("COMPUTER", "The Compumaster 2400", null, 
                "You slam your hands against the keyboard for several minutes.\n" +
                "Coincidently you have created a sequel to 'Twilight'.", false, false, false);
        office.addItem(computer);
        Item blueprint = new Item("BLUEPRINT", "A blueprint labeled 'World Domination Device'.\n" + 
                "It appears to consist of a large bungee cord, a beehive, and a cattle prod.", null, 
                "I'm not sure how.", true, false, false);
        computer.putItemInside(blueprint);
        
        office.addItem(new Item("DESK", "A large wooden desk.", null, "You sit behind the desk and feel important.\n" +
                "Thoughts of soon buying a boat are prominent.", false, false, false));
        
        office.addItem(new Item("PICTURE", "A small framed picture on the wall shows a man smiling next to another\n" +
                "figure that is scribbled out in black marker with the words 'Traitor' written\nabove.", null, 
                "You straighten the picture on the wall.", false, false, false));
        
        //Add room to grid
        gameGrid.addToGrid(0, 2, office);
        
        //**********************
        // END OFFICE
        //**********************
        
        //**********************
        // START ENTRYWAY
        //**********************

        Room entryway = new Room("Entryway", "Looks like the entryway of the house. There's a WASTEBASKET by the door.");
        
        //Doors
        entryway.newDoor(new Door("A wooden door, leads to the office.", false),
                Commands.Directions.NORTH);
        entryway.newDoor(new Door("A wooden door, leads to the kitchen.", false),
                Commands.Directions.SOUTH);
        FrontDoor frontDoor = new FrontDoor("The front door, leads outside. The seams are oddly covered in duct tape.\n"
                + "Badly scribbled handwriting on the door says 'PETE WAS HERE'.");
	entryway.newDoor(frontDoor, Commands.Directions.EAST);
				
	//Windows
        entryway.newWindow(new Window("There is a lovely FOUNTAIN."), Commands.Directions.WEST);
        
        //Items
        Item wastebasket = new Item("WASTEBASKET", "A small wastebasket.", null, "Nothing to throw away at the moment.", false, false, false);
        entryway.addItem(wastebasket);
        entryway.addItem(new Item("FOUNTAIN", "It sparkles in deep meaningful sparkliness.", null,
                "Maybe you need to head back to the bathroom for that.", false, false, false));
        
        Item wrench = new Item("WRENCH", "A wrench of sorts, hand-crafted and sturdy.", null,
                "Not much to do with it on it's on, I guess I could try using it on something else.", true, false, false);
        wastebasket.putItemInside(wrench);
        
        //Add room to grid
        gameGrid.addToGrid(1, 2, entryway);
        
        //**********************
        // END ENTRYWAY
        //**********************

        //**********************
        // START KITCHEN
        //**********************

        Room kitchen = new Room("Kitchen", "A DISHWAHER runs quietly. Someone must have been here recently.\n" + 
                "There's lovely granite COUNTERTOP.");
        
        //Doors
        kitchen.newDoor(new Door("A wooden door, leads to the entryway.", false),
                Commands.Directions.NORTH);
        kitchen.newDoor(new Door("A wooden door, leads to the dining room.", false),
                Commands.Directions.WEST);
        kitchen.newDoor(new Door("A wooden door, leads to the living room.", false),
                Commands.Directions.SOUTH);
        
        //Windows
        kitchen.newWindow(new Window("Hmmm... I see a large BUNKER of some sort."), Commands.Directions.EAST);
        
        //Items
        kitchen.addItem(new Item("DISHWASHER", "Das Wash-Disher X3, the expensive German model!\n" + 
                "Either way it appears that it's running, where is everyone?", null,
                "It's already on!", false, false, false));
	Item counterTop = new Item("COUNTERTOP", "Smooth granite countertop", null,
                "You twirl gleefully on it's surface.", false, false, false);
        kitchen.addItem(new Item("BUNKER", "A domed bunker. I see a shiny new bunker door.", null, 
                "I should probably get inside of the bunker somehow.", false, false, false));
        Item knife = new Item("KNIFE", "A shiny metal dagger.", null, 
                "You deftly twirl the knife. It slips out of your hand and lands in your foot.\n" +
                "You scream in pain and angrily pull the knife out and put it away.", true, false, false);
        counterTop.putItemInside(knife);
        kitchen.addItem(counterTop);
        
        //Add room to grid
        gameGrid.addToGrid(2, 2, kitchen);
        
        //**********************
        // END KITCHEN
        //**********************
		
	//**********************
        // START DINING ROOM
        //**********************

        Room dining = new Room("Dining Room", "An open dining room full of windows. A small circular TABLE " + 
                "sits in the center\nof the room.");
        
        //Doors
        dining.newDoor(new Door("A wooden door, leads to the kitchen.", false),
                Commands.Directions.EAST);
        
        //Windows
        dining.newWindow(new Window("There is a lovely FOUNTAIN."), Commands.Directions.NORTH);
	dining.newWindow(new Window("All I see is WILDERNESS for miles around."), Commands.Directions.WEST);
	dining.newWindow(new Window("All I see is WILDERNESS for miles around."), Commands.Directions.SOUTH);
		
        //Items
	Item table = new Item("TABLE", "A smooth, round, wooden table prime for putting things on.", null, 
                "You sit down at the table and ponder the mysteries of the WILDERNESS.", false, false, false);
	dining.addItem(table);
	dining.addItem(new Item("FOUNTAIN", "It sparkles in deep meaningful sparkliness.", null,
                "Maybe you need to head back to the bathroom for that.", false, false, false));
	dining.addItem(new Item("WILDERNESS", "Ah the wild. Beautiful.", null,
                "You can't USE the wilderness, only become one with it.", false, false, false));
				
        Book marvinDiary2 = new Book("MARVINS DIARY 2", "Marvin McStevens", "The diary of Marvin McStevens, Part 2", null, true, false);
        marvinDiary2.addPage("7/30/05\n" + 
                "Dear diary,\nThe trap has been set, but all signs point to mutiny.\n" + 
                "He's been ignoring me lately. I call his name over and over again\n" + 
                "to get his attention and he rarely even looks my way. However, when\n" + 
                "meal time rolls around I'm suddenly his best friend since I'm the\n" + 
                "only one who prepares any meals around here. The new bunker door has\n" +
                "been installed; The code is 6654. Sure he'll be angry, but that's");
        marvinDiary2.addPage("what he gets. I've 'mistakenly' left blueprints for the WDD\n" + 
                "out in the office. Frankly I think it goes above his head. But these\n" + 
                "blueprints will allow me to see just what happens when Pete sees them.\n" + 
                "Look at him... As I'm writing this he's just sitting there staring out\n" + 
                "the window. His fascination with wildlife is unnatural, he should be\n" +
                "focused on the plan.");
						
	table.putItemInside(marvinDiary2);
        
        //Add room to grid
        gameGrid.addToGrid(2, 1, dining);
        
        //**********************
        // END DINING ROOM
        //**********************
		
        //**********************
        // START LIVING ROOM
        //**********************

        Room livingroom = new Room("Living Room", "A simple yet elegant living room. I see a TV, and a COUCH");
        
        //Doors
        livingroom.newDoor(new Door("A wooden door, leads to the kitchen.", false),
                Commands.Directions.NORTH);
				
	//Windows
        livingroom.newWindow(new Window("All I see is WILDERNESS for miles around."), Commands.Directions.WEST);
        
        //Items
        livingroom.addItem(new Item("WILDERNESS", "Ah the wild. Beautiful.", null,
                "You can't USE the wilderness, only become one with it.", false, false, false));
        Book tvConversation = new Book("Pete Conversation", null, null, null, false, false);
        tvConversation.addPage("<You> Ah yes, hello there newly conquered peons, it is I - your new overlord!\n" +
                                "<You> With the cunning use of the 'World Domination Device', you now belong...\n" +
                                "<You> Pete, what the hell - the camera is too low. What are you doing?\n" +
                                "<Pete> *muffled noises*\n" +
                                "<You> Hey I heard that! We've talked about this - my plan is going to work.\n" +
                                "\nThe camera drops.");
        tvConversation.addPage("<You> Dammit Pete! Yes I've seen your mockups, and they don't make any sense.\n" +
                                "<You> I don't really see how a Salmon can...\n" +
                                "<Pete> *muffled noises*\n" +
                                "<You> Well how the hell else do you expect the bees to listen to me!?\n" +
                                "\nThe screaming fades as you walk out of the room. You see a cat quickly run by.");
        Television tv = new Television("TV", "50 inches of flat-screen beauty with a built-in entertainment system.",
                "You switch on the TV, looks like someone was watching Animal Planet.", tvConversation);
        livingroom.addItem(tv);
        livingroom.addItem(new Item("COUCH", "A large leather couch. Looks comfy.", null, 
                "You stretch out and switch on the television. This is the life.", false, false, false));
        
        //Add room to grid
        gameGrid.addToGrid(3, 2, livingroom);
        
        //**********************
        // END LIVING ROOM
        //**********************
        
        //**********************
        // START PORCH
        //**********************

        Room porch = new Room("Porch", "A wooden deck attached to the front door. I see a DRIVEWAY to the north, and\n" + 
				"and a large domed BUNKER to the east.");
        
        //Doors
        porch.newDoor(new Door("A wooden door, nothing special. Leads back inside the house.", false),
                Commands.Directions.WEST);
        BunkerDoor bunkerDoor = new BunkerDoor("Quite shiny and large. There's some sort of electronic number pad attached.\n" +
                                            "There are tiny scratches all over the pad, odd for something that looks so new.", 6654);
        porch.newDoor(bunkerDoor,Commands.Directions.EAST);
        
        //Items
        porch.addItem(new Item("DRIVEWAY", "A dirt road leading into the forest.", null, 
                "You can't leave now, think of how much time you've invested so far.", false, false, false));
        porch.addItem(new Item("BUNKER", "A large domed bunker. Maybe there are people inside.", null, 
                "Maybe if I could get inside.", false, false, false));
        
        //Add room to grid
        gameGrid.addToGrid(1, 3, porch);
        
        //**********************
        // END PORCH
        //**********************
        
        //**********************
        // START AIRLOCK
        //**********************

        Room airlock = new Room("Airlock", "I'm in some sort of airlock. I see a big red BUTTON.");
        
        //Doors
        
        airlock.newDoor(new Door("A reinforced bunker door. Leads back outside.", false),
                Commands.Directions.WEST);
        airlock.newDoor(new Door("A metal door, leads to a storage room.", false),
                Commands.Directions.EAST);
        airlock.newDoor(new Door("A metal door, leads to a filing room.", false),
                Commands.Directions.SOUTH);
        airlock.newDoor(new Door("A metal door, leads to a video room.", false),
                Commands.Directions.NORTH);
        
        //Items
        airlock.addItem(new Item("BUTTON", "A large round button on the wall.", null, 
                        "You happily slap the button, causing hurricane force winds to slam you against\n"
                        + "the wall. You scream as you flap about the room like a ragdoll.", false, false, false));

        //Add room to grid
        gameGrid.addToGrid(1, 4, airlock);
        
        //**********************
        // END AIRLOCK
        //**********************
        
        //**********************
        // START STORAGE ROOM
        //**********************

        Room storageRoom = new Room("Storage Room", "A large bare storage room with a concrete floor,"
                + " I see several CRATES\nof food");
        
        //Doors
        storageRoom.newDoor(new Door("A metal door, leads to a lab.", true),
                Commands.Directions.EAST);
        storageRoom.newDoor(new Door("A metal door, leads to the airlock.", false),
                Commands.Directions.WEST);
        
        //Items
        Item crates = new Item("CRATES", "Several crates of canned food, but it doesn't look like anything has been used.", null, 
                        "You gnaw a can open, impressive. Mmmm, premium potted meat.", false, false, false);
        Item salmon = new Item("SALMON", "A large salmon, tasty.", null, "You stuff your hand inside of the salmon to create a salmon puppet.",
                        true, false, false);
        crates.putItemInside(salmon);
        storageRoom.addItem(crates);

        //Add room to grid
        gameGrid.addToGrid(1, 5, storageRoom);
        
        //**********************
        // END STORAGE ROOM
        //**********************
        
        //**********************
        // START FILING ROOM
        //**********************

        Room filingRoom = new Room("Filing Room", "Rows and rows of nothing but filing CABINETS, that's a lot of paperwork.");
        
        //Doors
        filingRoom.newDoor(new Door("A metal door, leads to the airlock.", false),
                Commands.Directions.NORTH);
        
        //Windows
        filingRoom.newWindow(new Window("A small porthole window."), Commands.Directions.SOUTH);
        
        //Items
        Item cabinets = new Item("CABINETS", "Standard office issue, what in the world would they keep down here.", null, 
                        "Not really a way to use these, they are pretty heavy.", false, true, true);
        Item peteBlueprint = new Item("PETES BLUEPRINT", "A blueprint labeled 'Pete's World Domination Device'.\n"
                        + "It appears to consist of a large salmon, and... well I guess that's it. Hmm.", null,
                        "I'm not sure how.", true, false, false);
        Book marvinDiary3 = new Book("MARVINS DIARY 3", "Marvin McStevens", "The diary of Marvin McStevens, Part 3", null, true, false);
        marvinDiary3.addPage("8/05/05\n" + 
                "Dear diary,\nI knew I was right, Pete was never on-board with my plans.\n" + 
                "He discovered the blueprints in the office, and as I suspected it ended\n" + 
                "in a big argument. I had asked him to trust me, which be CLAIMED he\n" + 
                "did, but now it's all falling apart. Pete has mocked up his own plans\n" + 
                "for a device in an attempt to better redesign the WDD. I've read over them\n" +
                "of course, but I don't see how a single large fish will produce results.");
        marvinDiary3.addPage("I've always been the brain of the operations and now he's trying\n" + 
                "to take over! He jabbered on about 'worm holes' and 'quantum mechanics',\n" + 
                "but frankly I think he's just making things up as he goes along. The only\n" + 
                "solution now is to finish the original device in secret. He didn't take\n" + 
                "my criticisms well and may attempt sabotage. His plans are locked safely in the\n" +
                "filing cabinets in the bunker, and I have even locked the Science Lab. I've\n" +
                "attached the Science Lab key to this book so it will always be on my person.");    
        marvinDiary3.addPage("Although I know it's not possible, I have a sneaking suspicion that he has\n" +
                "somehow made it into the bunker, even with the new door installed. He is\n" +
                "extremely quick and cunning, always darting here and there at lightning speed\n" +
                "with little to no reason. The 'United Federation of Awesome' will be a reality!");  
        Key scienceKey = new Key("SCIENCE KEY", "A key - has the words 'Science Lab' printed on it.", 
                null, true, storageRoom, RoomElement.RoomElements.DOOR, Commands.Directions.EAST);    
        marvinDiary3.putItemInside(scienceKey);
        cabinets.putItemInside(peteBlueprint, marvinDiary3);
        filingRoom.addItem(cabinets);

        //Add room to grid
        gameGrid.addToGrid(2, 4, filingRoom);
        
        //**********************
        // END FILING ROOM
        //**********************
        
        //**********************
        // START VIDEO ROOM
        //**********************

        Room videoRoom = new Room("Video Room", "Looks very presidential, there's video EQUIPMENT, a DESK, and a BLUESCREEN.\n"
                + "There's a SEAL attached to the bluescreen. Looks like some sort of low budget\nstudio.");
        
        //Doors
        videoRoom.newDoor(new Door("A metal door, leads to the airlock.", false),
                Commands.Directions.SOUTH);
        
        //Items
        Item equipment = new Item("EQUIPMENT", "Professional video cameras pointed at the desk and bluescreen.", null, 
                        "Hmm, doesn't seem to work - maybe the batteries are dead.", false, false, false);
        Item tape = new Item("TAPE", "It's a standard tape labeled 'Message for conquered peons'.", null,
                        "I might be able to play this back using something I suppose.", true, false, false);
        equipment.putItemInside(tape);
        videoRoom.addItem(equipment);
        videoRoom.addItem(new Item("DESK", "A fancy wooden desk, smells of maple.", null, "You sit behind the desk and pretend to be " +
                "a very important man.", false, false, false));
        videoRoom.addItem(new Item("SEAL", "A round seal with the words 'United Federation of Awesome' printed in\ngold letters. " +
                "A panther battles a sasquatch in the center.", null, "There isn't really a way to use that.", false, false, false));
        videoRoom.addItem(new Item("Bluescreen", "A large bluescreen, possibly for inserting images behind the person\nat the desk.",
                null, "You poke the screen and ponder the meaning of life.", false, false, false));
        

        //Add room to grid
        gameGrid.addToGrid(0, 4, videoRoom);
        
        //**********************
        // END VIDEO ROOM
        //**********************
        
        //**********************
        // START SCIENCE LAB
        //**********************

        Room scienceLab = new Room("Science Lab", "Definitely looks like some sort of science takes place here.\n" + 
                "There are four TRAFFIC CONES in the center of the room. There are a great\n" + 
                "many empty SHELVES lining the walls.");
        
        //Doors
        scienceLab.newDoor(new Door("A metal door, leads to the storage room.", false),
                Commands.Directions.WEST);
        scienceLab.newDoor(new Door("A metal door, leads to a beekeeping room.", false),
                Commands.Directions.EAST);
        scienceLab.newDoor(new Door("A metal door, leads to the lower levels.", true),
                Commands.Directions.SOUTH);
        
        //Items
        Item device = new Item("DEVICE", "Looks like a strange device of some sort. It's a small metal box\n" +
                "with a slot. A light next to the word 'Fuel' is blinking red.", "hidden under a table.", 
                "You repeatedly press buttons to no avail.", true, false, false);
        scienceLab.addItem(device);
        
        scienceLab.addItem(new Item("TRAFFIC CONES", "Four traffic cones with a sign attached. It says 'Marvin's WDD'." + 
                "\nHowever, it appears the machine is missing!", null, 
                "You kick one of the cones over, that was incredibly rude.", false, false, false));
        
        Item shelves = new Item("SHELVES", "Empty shelves, that's odd. Seems like a Science Lab should have lab equipment.", null,
                "The shelves are all empty, nothing to use.", false, false, false);
        Item funnyNote = new Item("NOTE", "It's a small sheet of paper with the words 'PETE WAS HERE' pieced together\n" +
                "out of magazine clippings. I'm beginning to see why I didn't like him.", null, null, false, false, false);
        shelves.putItemInside(funnyNote);
        scienceLab.addItem(shelves);
        
        //Add room to grid
        gameGrid.addToGrid(1, 6, scienceLab);
        
        //**********************
        // END SCIENCE LAB
        //**********************
        
        //**********************
        // START BEE ROOM
        //**********************

        Room beeRoom = new Room("Beekeeping Room", "A room filled with beehives. There are BEES swarming about.");
        
        //Doors
        beeRoom.newDoor(new Door("A metal door", false),
                Commands.Directions.WEST);
        
        //Items
        Bees bees = new Bees("BEES", "An unfriendly looking swarm of BEES.", null,
                "You grab a big handful of bees, this was not an intelligent thing to do.\n" +
                "Among the pain and screaming, you contemplate the meaning of your own\n" +
                "existence. Perhaps it was your flawed upbringing that has caused you to\n" +
                "make so many questionable decisions today. Unfortunately you are still\n" +
                "lacking such memories. 'Perhaps I could train these bees.', you think to\n" +
                "yourself. You did apparently build a world domination device, and that\n" +
                "would definitely require an above-normal IQ. Plus, such deep thinking among\n" +
                "the mind numbing pain is impressive. You end these thoughts as you flail\n" +
                "comically about while bees sting every inch of your body.");
        Item bee = new Item("BEE", "A newly tamed bee, I really didn't think that would work.\n" + 
                "I shall call him Frank.", null,
                "Frank's too mellowed out to conquer your enemies at the moment.", true, false, false);
        bees.putItemInside(bee);
        beeRoom.addItem(bees);
        
        
        //Add room to grid
        gameGrid.addToGrid(1, 7, beeRoom);
        
        //**********************
        // END BEE ROOM
        //**********************
        
        //**********************
        // START PETES SHACK
        //**********************

        Room petesShack = new Room("Petes Shack", "A hidden shack in the woods. I see a ton of scientific supplies.\n" + 
                "I also see a bunch of TOYS, and a CAT TOWER. Do I have pets?");
        
        //Items
        petesShack.addItem(new Item("TOYS", "A very large assortment of feathery toys...", null,
                "You bat them around playfully, it's quite embarrassing.", false, false, false));
        
        petesShack.addItem(new Item("CAT TOWER", "A fancy cat tower.", null,
                "I don't think it can hold my weight.", false, false, false));
        
        Puddle puddle = new Puddle("PUDDLE", "The roof must be leaky.", "formed on the dirt floor.",
                "I would but I don't like getting wet.");
        Item bungeeCord = new Item("BUNGEE CORD", "A large bungee cord, could be useful.", null, 
                "You stretch the cord with your hands. It snaps back powerfully towards\n" + 
                "your face, knocking you to the ground.", true, false, false);
        Key lowerLevelsKey = new Key("LEVELS KEY", "A key with the words 'Lower Levels' stamped on the front.", null, true,
                scienceLab, RoomElements.DOOR, Commands.Directions.SOUTH);
        puddle.putItemInside(bungeeCord, lowerLevelsKey);
        petesShack.addItem(puddle);
        
        petesShack.addItem(new Item("Wilderness", "Ah the wild. Beautiful.", null,
                "You can't USE the wilderness, only become one with it.", false, false, false));
        
        //Doors
        petesShack.newDoor(new Door("Well it's not really a door, more of a small flap near the floor.\n" +
                "Apparently I built this room to be accessible to cats only.", true),
                Commands.Directions.WEST);
        
        //Windows
        petesShack.newWindow(new Window("All I see is WILDERNESS for miles around."), Commands.Directions.NORTH);
	petesShack.newWindow(new Window("All I see is WILDERNESS for miles around."), Commands.Directions.SOUTH);
        
        //Set to visited so it's visible on the transporter
        petesShack.setVisited(true);

        //Add room to grid
        gameGrid.addToGrid(0, 9, petesShack);
        
        //**********************
        // END PETES SHACK
        //**********************
        
        //**********************
        // START STAIRS
        //**********************

        Room stairs = new Room("Stairs", "A long stairway leading to the lower levels of the bunker.");
        
        //Doors
        stairs.newDoor(new Door("A metal door, leads to the science lab.", false),
                Commands.Directions.NORTH);
        stairs.newDoor(new Door("A metal door, leads to a training room.", false),
                Commands.Directions.SOUTH);
        
        //Items
        Book marvinDiary4 = new Book("MARVINS DIARY 4", "Marvin McStevens", "The diary of Marvin McStevens, Part 4", 
                "sitting on a step.", true, false);
        marvinDiary4.addPage("8/10/05\n" + 
                "Dear diary,\nPete has made it into the bunker. He has built some sort\n" + 
                "of transportation device and has slowly been stealing items out of\n" + 
                "my lab. For safety I have moved the WDD to the lower levels. He thinks\n" + 
                "that just because he's built a transporter that it proves something,\n" + 
                "but I'm not impressed. I sketched out plans for a transporter years\n" +
                "ago and decided it was beneath me. I showed Pete my old sketches and laughed");
        marvinDiary4.addPage("in his face. When I confronted him about stealing, he of course denied\n" + 
                "everything. I'm not quite sure where he keeps hiding my equipment.\n" + 
                "The beehives are coming along nicely and I believe the time is now.\n" + 
                "With the transporter complete, Pete could launch a full-scale attack\n" + 
                "at any moment. Who knows what else he has up his sleeves. I need to assemble\n" +
                "all of my components before making an attempt. Hopefully this time next week\n" +
                "I will be sitting on my throne while Pete amuses me with little dances\nand tricks.");   
        marvinDiary4.setVerbForm(2); //Change verb form to none
        stairs.addItem(marvinDiary4);
        
        //Add room to grid
        gameGrid.addToGrid(2, 6, stairs);
        
        //**********************
        // END STAIRS
        //**********************
        
        //**********************
        // START TRAINING ROOM
        //**********************

        Room trainRoom = new Room("Training Room", "A large padded room filled with training equipment.\n" +
                "A leader of the world needs to be in tip-top shape I guess.\n" + 
                "There is a PUNCHING BAG, a TREADMILL, and a WALL OF DEATH.");
        
        //Doors
        trainRoom.newDoor(new Door("A metal door, leads to a stairway.", false),
                Commands.Directions.NORTH);
        trainRoom.newDoor(new Door("A metal door, leads to the WDD room.", false),
                Commands.Directions.WEST);
        trainRoom.newDoor(new BunkerDoor("A rather large metal door - there's another electronic keypad attached.", 844655),
                Commands.Directions.EAST);
        
        //Items 
        trainRoom.addItem(new Item("PUNCHING BAG", "A punching bag, prime for the punch.", null,
                "You deftly bob and weave, swatting the bag with ninja-like quickness.", false, false, false));
        trainRoom.addItem(new Item("TREADMILL", "18 speeds and 65 levels of incline.", null,
                "Unfortunately level 65 is close to 90 degrees, you simply run into a\n" +
                "wall of tread. Who the hell designed this thing?", false, false, false));
        Item wod = new Item("WALL OF DEATH", "A wall with an assortment of deadly training weapons.", null,
                "You pick up the Face-Melter 3000 and proceed to spray yourself in the eyes.\n" +
                "The pain should subside in 3-4 hours.", false, false, false);
        Item cattleProd = new Item("CATTLE PROD", "'La Sorpresa De La Vaca'; I don't speak Spanish but it says '8 Million\nVolts'" + 
                "on the handle.", null, "You twirl the cattle prod in the air, pocketing it in a swift motion.\n" +
                "...I was expecting something horrible to happen.", true, false, false);
        wod.putItemInside(cattleProd);
        trainRoom.addItem(wod);

        //Add room to grid
        gameGrid.addToGrid(3, 6, trainRoom);
        
        //**********************
        // END TRAINING ROOM
        //**********************
        
        //**********************
        // START WDD ROOM
        //**********************

        Room wddRoom = new Room("WDD Room", "The official World Domination Device Room!, I see a WDD.");
        
        //Doors
        wddRoom.newDoor(new Door("A metal door, leads to the training room.", false),
                Commands.Directions.EAST);
        
        //Items 
        WDD wdd = new WDD("WDD", "My World Domination Device! Very unassuming with its many layers of\n" +
                "duct-tape and cardboard.", null);
        Key wddfbKey = new Key("GOLDEN KEY", "A small golden key with the letters WDDFB stamped on the front.", null, true, wddfb);
        wdd.putItemInside(wddfbKey);
        wddRoom.addItem(wdd);

        //Add room to grid
        gameGrid.addToGrid(3, 5, wddRoom);
        
        //**********************
        // END WDD ROOM
        //**********************
        
        //**********************
        // START SECURE ROOM
        //**********************

        Room secRoom = new Room("Secure Room", "A fortified power station with security CAMERAS, and MONITORS.");
        
        //Doors
        secRoom.newDoor(new Door("A large metal door, leads to the training room.", false),
                Commands.Directions.WEST);
        
        //Items 
        Book peteConversation = new Book("Pete Conversation", null, null, null, false, false);
        peteConversation.addPage("<You> Ah Pete. We meet again my furry nemesis!\n" +
                                 "<Pete> Meow?\n" + 
                                 "<You> No excuses Pete! I may have forgotten what happened to me... But\n" + 
                                 " I am remembering more and more as time goes on. I have no idea how you\n" + 
                                 " have managed to come this far, but victory will be mine! The WDD is prepped\n" +
                                 " and ready. Step aside four-legs, and I will conquer all.\n" + 
                                 "<Pete> Meow?\n" +
                                 "<You> What? Its 5:00pm according to my watch, what does that have to...\n" +
                                 "<Pete> Meow *purrr*\n" +
                                 "<You> There's an entire crate of Salmon in the storage room, you can get\n it yourself!");
        peteConversation.addPage("<You>And don't think I didn't notice that in the weekly budget meetings\n" + 
                                 " either. But I digress... Move aside!\n" +
                                 "\nYou power past Pete, and slam your hand down on a button labeled\n'Nuclear Power On'\n" +
                                 "\nA low rumble begins. In the WDD room, the device works itself free from the\n" +
                                 "generator due to vibration, causing the bungee cord to whip the box into the\n" +
                                 "wall. The radiation from the generator has mutated the bee into some sort of\n" +
                                 "'Super Bee'. Unfortunately the bungee cord may not have been such a brilliant\n" +
                                 "idea when you think about it. It appears to have agitated the mutant insect,\n" +
                                 "which is now heading towards you.");
        peteConversation.addPage("The 'Wall of Death' would be a wonderful place to be at the moment, but not to\n" + 
                                 "worry, you are trained in the deadly hand-to-hand arts. You prepare to attack,\n" +
                                 "as the bee picks you up and throws you against the wall. Pete begins to lick\n" +
                                 "himself absentmindedly as you scream in terrible pain. Deja Vu hits you as you\n" +
                                 "suddenly remember how terrible of an idea this all was. In fact you believe\n" +
                                 "this may have happened to you multiple times in the past. The venom of the bee\n" + 
                                 "has begun to take its effect as you slowly slip out of consciousness.'Salmon'\n" +
                                 "you say quietly to yourself, I should have gone with the Salmon.");
        peteConversation.addPage("Pete jumps down, gently whacking the creature into submission. He trots off\n" + 
                                 "merrily, jumping over you as he leaves.");
        Pete pete = new Pete("PETE", "A small furry creature, most likely feline in nature.", "is sitting on the table next to a power button!", peteConversation, wdd);
        pete.setVerbForm(2); //Sets to blank verb form
        secRoom.addItem(pete);
        secRoom.addItem(new Item("CAMERAS", "An assortment of security cameras, I feel very secure.", null, 
                "They are already in use!", false, false, false));
        secRoom.addItem(new Item("MONITORS", "An assortment of security monitors, I feel very secure.", null, 
                "You flip through the channels and see your bathroom. That's odd.", false, false, false));

        //Add room to grid
        gameGrid.addToGrid(3, 7, secRoom);
        
        //**********************
        // END SECURE ROOM
        //**********************
        
        //**********************
        // Place Player
        //**********************
        
        GameSettings.gameGrid.setPlayerLoc(START_LOC);
        
        //**********************
        // Configure Combinable Items
        //**********************
        Item knifewrench = new Item ("KNIFEWRENCH", "It's a knife combined with a wrench. You know, for kids.", null,
                "I might be able to use this for something... Not sure what.", true, false, false);
        knife.setItemCombinesWith(wrench, knifewrench);
        Transporter transporter = new Transporter("TRANSPORTER", "I guess this Pete guy was right about the power of salmon...\n" +
                "Looks to be some sort of transporter, there's an LCD screen listing rooms.");
        salmon.setItemCombinesWith(device, transporter);
        
        //**********************
        // Configure Special Items
        //**********************
        Key filingCabKey = new Key("CABINET KEY", "A small key, looks like it fits a filing cabinet.", null, true, cabinets);
        tv.putItemInside(filingCabKey); //Put key inside of TV

        //**********************
        // Configure Interactions
        //**********************
        towel.setInteraction(frontDoor, "You twist the towel around the door handle and begin to pull.\n" + 
                                        "Wait a second... yes, YES!\n" +
                                        "Nope that isn't going to do anything. A+ for effort.");
        towel.setInteraction(puddle, "I knew this towel would come in handy! You dry the puddle and examine\nthe floor.");
        tape.setInteraction(equipment, "You pop the tape in, but nothing happens.");
        tape.setInteraction(computer, "Unfortunately it doesn't have a built in VCR...");
        tape.setInteraction(tv, "You pop the tape in and see someone you recognize - yourself!\n" +
                                "You're sitting behind a desk and looking quite official. Images of\n" +
                                "soothing sunsets and oceans scroll behind you. Audio begins to cut in.");
        knife.setInteraction(frontDoor, "I can't do that! If I only cut the duct tape how will I fix the door jam?");
        wrench.setInteraction(frontDoor, "I can't wrench the door open with all that duct tape!");
        knifewrench.setInteraction(frontDoor, "You cut the duct tape while simultaneously wrenching it open. Brilliant!");
        cattleProd.setInteraction(bees, "With the reflexes of a deranged mongoose on crack, you shove the prod\n" + 
                "into the swarm of bees. Luckily you hit a rather large specimen, showing\n" +
                "the rest of them who's boss. You squint to find the now tamed bee.");
        bee.setInteraction(wdd, "Ah yes, the secret component - Frank. A tamed bee will do nicely in such\n" + 
                "an evil and genius device!");
        bungeeCord.setInteraction(wdd, "Sure it may not make sense to the Layman, but if we hook one end to\n" +
                "side of the box, and another to the wall... Now I can just pull the box and\n" +
                "brace it against this nuclear generator that Pete had laying around...\n" +
                "There we go! Only good things could come of this.");
        blueprint.setInteraction(wdd, "Brilliant!, The box obviously needs some sort of schematics. I'll just\n" +
                "stuff that down in there... Good to go!");
        peteBlueprint.setInteraction(wdd, "Really? My cardboard box is not Salmon-safe. Plus his design is ridiculous.");
        
        /**************************
         * Add/Display start text *
         *************************/
        //How-to book
        Book dontPanic = new Book("README", "Mike - Your humble programmer.", "Don't Panic", null, true, false);
        
        dontPanic.addPage("Ah yes, a video game. It can be daunting, but not to worry.\n" + 
                        "Perhaps you should start with EXAMINE ROOM, or maybe by examining a few items.\n" +
                        "If you see an object in CAPS, there's a good chance that you can\n" +
                        "EXAMINE and USE it! Be careful not to abbreviate the names of objects, I\n" +
                        "probably won't be able to understand you. Note: The order of using two items\n" +
                        "together might actually matter...");
        gameGrid.getPlayerInventory().addToInventory(dontPanic);
        
        //Add a book to use as the start text... doesn't appear in the inventory
        startScreenBook = new Book("START SCREEN", "Mike - Lord of Awesome", "Start screen book", null, false, false);
        
        startScreenBook.addPage("Welcome to Mike's Text Adventure!\n" + 
                                "You awake in a strange room, not quite sure as to how you've arrived.\n" + 
                                "You decide to explore a bit and see if anything can jog your memory.");
        
        startScreenBook.use(null,null); //Read book
    }
}
