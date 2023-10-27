# Mike's Text Adventure

A text-based adventure game, written in Java. Marvin McStevens awakes with amnesia and must explore his environment to determine what has happened. A game of deep thought and world domination. Requires Java version 1.7 and above.

***Note: This is a game I wrote many years ago - I haven't tested with modern Java versions!***

[Javadoc](https://github.com/kaledev/MikesTextAdventure/javadoc/index.html)

[Changelog](https://github.com/kaledev/MikesTextAdventure/changelog.txt)

[Executable](https://github.com/kaledev/MikesTextAdventure/bin/MikesTextAdventure.jar)

## Running
- Make sure Java 1.7+ is installed on your machine.
- Download the .Jar file above.
- Navigate to the saved file location inside of the terminal and run the command `java -jar MikesTextAdventure.jar`.

## Brief Programmatic analysis

This is a text-based adventure game that I've actually been working on for several years. It's been fully rewritten several times over. I wanted to produce not just a game, but something more akin to a game engine for building text-based adventure games.

The general structure consists of a game grid of a specific X,Y size, rooms that take up a single X,Y location, and items that are contained within those rooms. This structure leads to a class, GameGrid, that contains all of the objects for the game. The GameGrid is implemented in GameSettings, which holds 95% of the code needing to be changed to create a complete new game.

The vast majority of items end up not needing classes of their own because user interaction with them is the same. For the items that do have some sort of conditional interaction, such as Keys, classes are created with overridden functions.

The ultimate goal was to have a single class that needed to be updated for changes with minimal need for creating classes for every little item. In this regard I believe I've come extremely close. Since the entire game is based on a story, keeping that text together in one location is paramount. Given the rigid constraints I kept myself to in order to make the code reusable, this ended up being one of the greatest headaches. Luckily the only time I had to branch from this is when a custom item was required.

Sample Room (GameSettings.java):
```java
Room hallway = new Room("Hallway", "It leads to another room.");
//Doors
hallway.newDoor(new Door("A wooden door, leads to the bedroom.", false), Commands.Directions.WEST);
hallway.newDoor(new Door("A wooden door, leads into an office.", false), Commands.Directions.EAST);
//Windows
hallway.newWindow(new Window("A TREE sways in the wind."), Commands.Directions.NORTH);
//Items
hallway.addItem(new Item("TREE", "Colorful leaves, looks to be fall.", null, "I'm not in need of any firewood.", false, false, false));
//Add room to grid
gameGrid.addToGrid(0, 1, hallway);
```

With these few lines of code, a hallway was created at (0,1) on the game grid that the player can now explore. The tree is an example of an item that is described by examining the window, which when interacted with now displays the text shown in the Tree item. Both the examination and use text is defined when defining a new Item.

There is also an opportunity to set custom interactions between items. If the programmer wishes to display only text, this is defined as follows (here we see a towel interacting with the front door).

```
towel.setInteraction(frontDoor, "You twist the towel around the door handle and begin to pull.\n" + "Wait a second... yes, YES!\n" + "Nope that isn't going to do anything. A+ for effort.");
```

If an interaction needs to trigger an event rather than display text - this is when we have to create a class for the object an override the "interact" function in the item class. This also gives us the opportunity to override the "examine" and "use" functions to further customize how the user interacts with the item.

I ran into the dilemma of forcing the programmer to detail everything in the room, or allowing the program to list out items. I settled between the two, allowing either. The "itemLocationDescription" can be set for an Item, which will flag the program to list the item on examination of the room with a location description.

Lastly, I chose to allow a very limited set commands for the user. The USE command ends up being a catch-all in terms of doing something to/with the item, and EXAMINE is a catch-all for viewing. I did this for simplicity and the fact that I didn't want users to guess what commands might be able to be used. It's true that EXAMINE may also mean USE, such as when reading a book. In this instance I simply override the examine function in the Book class and force it to examine first, then use.

I'll end my analysis here as it could possibly go on for dozens of pages. There are still a ton of things I want to add into the game, including expanding the storyline. However, version 1.0 is a good stopping point and first release.