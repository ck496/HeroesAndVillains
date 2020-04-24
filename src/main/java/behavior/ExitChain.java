package main.java.behavior;

import java.util.Vector;

import main.java.creation.Base;
import main.java.creation.World;

/**
 * ExitChain class handles allows you to loop back or exit the chain. ExitChain
 * loops back if the World's state is "At Risk", if the World is no longer
 * "AtRisk" the program exits and prints final messages
 * 
 * CHAIN OF RESPONSIBILITY DESIGN PATTERN is used to handle the different
 * behavior and states of all the different objects. A World object is passed to
 * the first class in the chain and it does the work and passes it to the next
 * and loops back until the Worlds "State" is no longer at risk
 * WorkChain->CreateChain->FightChain->ExitChain. ExitChain loops back to
 * WorkChain if the World's state is "At Risk", if the World is no longer "At
 * Risk" the program exits and prints final messages
 * 
 * @author Chris Kurian
 * @version 3.0
 *
 */
public class ExitChain implements Chain {

    private Chain nextChain;

    /**
     * Sets the next member in the chain to pass the responsibility to.
     * 
     * @param nextChain
     */
    @Override
    public void setNextChain(Chain nextChain) {
        this.nextChain = nextChain;
    }

    /**
     * Method allows to loops back if the World's state is "At Risk", if the World
     * is no longer "AtRisk" the program exits and prints final messages
     * 
     * @param aWorld
     */
    @Override
    public void doWork(World aWorld) {
        Vector<Base> baseVector = aWorld.getBaseVector();
        String state1 = baseVector.get(0).getState();
        String state2 = baseVector.get(1).getState();
        String state3 = baseVector.get(2).getState();
        String state4 = baseVector.get(3).getState();

        System.out.println("\n\n\t\t\t[EXIT-CHAIN]\n");
        // IF all the bases are one type (not open) then exit, no more battle
        if (state1.equals(state2) && state2.equals(state3) && state3.equals(state4)) {
            aWorld.setState(state1.equals("Base") ? "Secured" : "Fallen");
            System.out.println("\n\n\t\t\t" + aWorld.getName() + " is "
                    + aWorld.getState() + "!!");
            if (aWorld.getState().equals("Secured")) {
                System.out.println("\n\n[Exit-CHAIN] Final Status: ");
                aWorld.print("");
                System.out.println("\n\n\t\t\tThe Heros have once again triumphed "
                        + "the villans" + "\n\t\t\tand saved the fate of " + aWorld.getName()
                        + "\n\n" + aWorld.getName() + " is Secure for now!");
                System.out.println("\n\nExitting Chain for " + aWorld.getName() + "....");

            } else {
                System.out.println("\n\n[Exit-CHAIN] Final State: ");
                aWorld.print("");
                System.out.println("\n\n\t\t\tThe Heros fought the good fight but the"
                        + " Villians have defeated them....."
                        + "\n\n\t\t\t" + aWorld.getName() + " has fallen.....");
                System.out.println("\n\nExitting Chain for " + aWorld.getName() + "....");
            }
        } else {
            System.out.println("\n\n\t\t\t" + aWorld.getName() + " is still At Risk, continue working");
            System.out.println("\nSending world " + aWorld.getName() + "to WorkChain\n");
            this.nextChain.doWork(aWorld);
        }

    }

}
