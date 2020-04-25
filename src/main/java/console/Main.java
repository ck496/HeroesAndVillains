package main.java.console;

import java.util.Vector;

import main.java.behavior.Chain;
import main.java.behavior.CreateChain;
import main.java.behavior.FightChain;
import main.java.behavior.ExitChain;
import main.java.behavior.RecoverChain;
import main.java.behavior.WorkChain;

import main.java.creation.Base;
import main.java.creation.Character;
import main.java.creation.Universe;
import main.java.creation.World;

/**
 * Main Class of the HeroesAndVillans project creates the heroes, Villains,
 * Bases, World and the Universe where the simulation will take place. Main sets
 * up the console for the simulation to run. Main creates all the initial
 * objects needed to start the game.
 * 
 * <p>
 * Read the README.MD for additional information.
 * 
 * <p>
 * BUILDER DESIGN PATTERN is used to provide a better and more flexible approach
 * to object creation. Allows you to create objects with or without manual entry
 * of all the instance variables. This pattern is used to create all the
 * creation objects like heroes, Villains, Bases, World and the Universe in this
 * project. Universe has Worlds, Worlds have 4 Bases, Bases have 5 Characters in
 * each
 * 
 * <p>
 * CHAIN OF RESPONSIBILITY DESIGN PATTERN is used to handle the different
 * behavior and states of all the different objects. A World object is passed to
 * the first class in the chain and it does the work and passes it to the next
 * and loops back until the Worlds "State" is no longer at risk
 * WorkChain->CreateChain->FightChain->RecoverChain->ExitChain. ExitChain loops
 * back to WorkChain if the World's state is "At Risk", if the World is no
 * longer "At Risk" the program exits and prints final messages
 * 
 * @author Chris Kurian
 * @version 2.0
 *
 */

public class Main {
    /**
     * Main function.
     * 
     * @param args String
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the heroes and Villans Universe! "
                + "\n\nWhich side will you choose?? \nWho will emerge "
                + "victorious? \nWhat will be the fate of the universe?"
                + "\n\nFind out soon..........\n\n");

        // Villain -create 2 villains and place them in a Lair named "Thanos"
        Character villian1 = new Character.Builder().isHero(false).constructCharacter();
        Character villian2 = new Character.Builder().isHero(false).constructCharacter();
        Vector<Character> villainVector = new Vector<Character>(5);
        villainVector.add(villian1);
        villainVector.add(villian2);
        // BUILDER DESIGN PATTERN
        Base thanos = new Base.Builder().setName("Thanos").setState("Lair")
                .setMemberVector(villainVector)
                .baseConstruct();

        // heroes -create 2 heros and place them in a base named "Avengers"
        Character hero1 = new Character.Builder().isHero(true).constructCharacter();
        Character hero2 = new Character.Builder().isHero(true).constructCharacter();
        Vector<Character> heroVector = new Vector<Character>(5);
        heroVector.add(hero1);
        heroVector.add(hero2);
        // BUILDER DESIGN PATTERN
        Base avengers = new Base.Builder().setName("Avengers").setState("Base")
                .setMemberVector(heroVector)
                .baseConstruct();

        // Create 2 open bases without any members
        // BUILDER DESIGN PATTERN
        Base alpha = new Base.Builder().setName("Alpha")
                .baseConstruct();
        // BUILDER DESIGN PATTERN
        Base omega = new Base.Builder().setName("Belsnickel")
                .baseConstruct();
        // Add bases into baseVector to set base vector for world
        Vector<Base> baseVector = new Vector<Base>();
        baseVector.add(thanos);
        baseVector.add(avengers);
        baseVector.add(alpha);
        baseVector.add(omega);

        // Create World named "Nebula" add bases to it
        // BUILDER DESIGN PATTERN
        World nebula = new World.Builder().setName("Nebula")
                .setbaseVector(baseVector).worldConstruct();

        // Create Universe add World to it, to keep the output/display as short
        // as possible, only one world will be added to it for the time being.
        // BUILDER DESIGN PATTERN
        Universe universe = new Universe.Builder().setName("Universe")
                .addWorld(nebula)
                .universeConstruct();

        // Create a chain to handle behavior that takes care different states
        // of the world, bases and its members
        // CHAIN OF RESPONSIBILITY DESIGN PATTERN
        Chain workChain = new WorkChain();
        Chain createChain = new CreateChain();
        Chain fightChain = new FightChain();
        Chain recoverChain = new RecoverChain();
        Chain exitChain = new ExitChain();
        // Set the nextChain to pass the responsibility to once current chain is done
        workChain.setNextChain(createChain);
        createChain.setNextChain(fightChain);
        fightChain.setNextChain(recoverChain);
        recoverChain.setNextChain(exitChain);
        exitChain.setNextChain(workChain);

        try {
            // Start the chain and catch any exceptions
            workChain.doWork(universe.getWorld("Nebula"));
        } catch (Exception e) {
            System.out.println("Exception In Main: \n" + e.getMessage());
        }

    }

}
