# HeroesAndVillains

Universe for Heroes and Villains with characters, cities, liars and battle grounds.
Heroes and Villains battle for the for the fate of the Universe.

## Design patterns
The listed patterns will work together one will handle the creational aspect of things and the other will handle the behaviors and communications between objects.

### Builder:
 will be responsible of character and environment creation. Step by step creation of different complex objects within the universe

 ### Chain of Responsibilities:
* Will handle all the interactions/communications between characters.
* Dialog, work, battle, recover, post-fight interactions will all be handled with chain of responsibilities design


## Requirements
The Design Patterns in the **bold** will handle the responsibilities listed after the **:**
* **Builder:** On Earth, there exists many Worlds where villains live and "spawn".
* **Builder:** New hot spots can spawn when one World gets too large. You need at least one World when your simulation starts.
* **Builder:** Every gang of villains will have their own lair in the Worlds
* **Builder:** Heroes have their own bases and they also cooperate best in teams of 5. When there are more than 5 heroes, they will create a new base.
* **Builder:** in the CreationChain characters are created from another member in the base.

* **Chain of Responsibilities:** Villains and Super Heroes battle each other when they run into each other (you can randomly make them meet how you see ﬁt). The losers die and the winners take over the others strength. It is always a one on one battle.
* **Chain of Responsibilities:** Each super hero and villain has powers. These powers are transferred when they battle and the winner gets the power of the one defeated. (Levels are transferred instead to be more meaningful and functional)
* **Chain of Responsibilities:**  Each person (no matter if villain or hero) needs to rest and recover, during these breaks they cannot battle and if they are attacked they will lose
* **Chain of Responsibilities:**  If all villains are killed then no new villains can spawn and they are extinct. If all of the heroes are killed, the villains have won and the simulation is over.
* **Chain of Responsibilities:**  Strategy is used to fight the smallest base in order to ensure chances winning and Characters also have strengths and weaknesses. If your opponents power is you weakness then you take 2x the damage.


### Notes
* Cities are called Worlds in this program to keep up with the theme of "Heroes And Villain Universe"
* Only one world is added to the universe in the main to keep the output/display as short as possible since the simulation is very lengthy.
* You may want to uncheck the "Limit Console Content" in your IDE or Terminal in order to read all the events (it gets really lengthy even with one world)


## Summary
The simulation will start with a Hero base, a Villain base and 2 empty bases. The Bases will have 2 characters each. Heroes and Villains will get appropriate names for their type based on a random hero name generator and villain name generator. They will also be assigned a power based on a random power generator and appropriate strengths and weakness based on their power.
Then the heroes and villains go through different states, working to save/kill people, get enough points to create a new character and either add it to their base, an allied base if their base is full or send to another world if all the bases in the current world is filled. If a base decides to fight they will fight each member from another base chosen strategically to optimize wining. At the end of the fight everyone that fought levels up by 1 but if a member kills an enemy, member get gets their level +1 + enemy's level. After every fight, fighter have to recover and get 25 health points back, if member level is under 5 the cap is 100 health points. After recover you go to ExitChain where you either loop back or exit the game

### BUILDER DESIGN PATTERN
 Builder Pattern is used to  provide a better and more flexible approach to object creation. Allows you to create objects with or without manual entry of all the instance variables values. This pattern is used to create all the creation objects like Heroes, Villains, Bases, World and the Universe in this project. Universe has Worlds, Worlds have 4 Bases, Bases have 5 Characters in each

### CHAIN OF RESPONSIBILITY DESIGN PATTERN
Chain of responsibilities is used to handle the different behavior and states of all the different objects. A World object is passed to the first class in the chain and it does the work and passes it to the next and loops back until the Worlds "State" is no longer at risk.
* WorkChain->CreateChain->FightChain->ExitChain.
ExitChain loops back to WorkChain if the World's state is "At Risk", if the World is no longer "At Risk" the program exits and prints final messages
