# HeroesAndVillains 

Universe for Heroes and Villains with characters, cities, liars and battle grounds. 
Heroes and Villains battle for the for the fate of the Universe.

## Design patterns
The listed patterns will work together one will handle the creational aspect of things and the other will handle the behaviors and communications between objects. 

### Builder:
 will be responsible of character and environment creation. Step by step creation of different complex objects within the universe
 
 ### Chain of Responsibilities: 
* Will handle all the interactions/communications between characters.
* Dialog, battle, attack, post-fight interactions will ala be handled with chain of responsibilities design
* Issue interactions with several objects without specifying the receiver explicitly.


## Requirements 
The Design Patterns in the **bold** will handle the responsibilities listed after the **:**
* **Builder:** On Earth, there exists many cities where villains live and "spawn".
* **Builder:** New hot spots can spawn when one city gets too large. You need at least one city when your simulation starts.
* **Builder:** Every gang of villains will have their own lair in the city
* **Builder:** Heroes have their own bases and they also cooperate best in teams of 5. When there are more than 5 heroes, they will create a new base. 
	
* **Chain of Responsibilities:** Villains and Super Heroes battle each other when they run into each other (you can randomly make them meet how you see ﬁt). The losers die and the winners take over the others strength. It is always a one on one battle. 
* **Chain of Responsibilities:** Each super hero and villain has powers. These powers are transferred when they battle and the winner gets the power of the one defeated. 
* **Chain of Responsibilities:**  Each person (no matter if villain or hero) needs to rest and recover, during these breaks they cannot battle and if they are attacked they will lose
* **Chain of Responsibilities:**  If the person defeated has a power the winner already has, just increase that power
	
