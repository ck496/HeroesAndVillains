package main.java.behavior;

import java.util.Vector;

import main.java.creation.Base;
import main.java.creation.Character;
import main.java.creation.World;

/**
 * RecoverChain class handles the behavior "recover " and allows each of the
 * members to recover after a fight.Recover Chain gives 25 health points back to
 * members of base that just fought, if member level is under 5 the cap is 100
 * health points. After recover you go to ExitChain.
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
 * @version 3.0
 *
 */
public class RecoverChain implements Chain {
    private Chain nextChain;

    /**
     * Sets the next member in the chain to pass the responsibility to.
     * 
     * @param nextChain in line
     */
    @Override
    public void setNextChain(Chain nextChain) {
        this.nextChain = nextChain;

    }

    /**
     * Adds 25 health points to base members who just fought.
     * 
     * @param aWorld to work on
     */
    @Override
    public void doWork(World aWorld) {
        Vector<Base> baseVector = aWorld.getBaseVector();
        Vector<Character> memberVector;
        boolean recover = false;

        System.out.println("\n\n\t\t\t[RECOVER-CHAIN]\n");

        for (Base b : baseVector) {
            if (b.getActionState().equals("Recover")) {
                recover = true;
                memberVector = b.getMembers();
                for (Character ch : memberVector) {
                    int health = ch.getHealth() + 25;
                    if (health > 100 && ch.getLevel() <= 5) {
                        ch.setHealth(100);
                    } else {
                        ch.setHealth(health);
                    }
                }
                System.out.println("Recovered Team: " + b.getName());
                b.print("\t");
            } else {
                System.out.println("\n\t\t\tNo memebers need to recover");
            }
            b.setActionState("Work");

        }

        // Display status if changed happened
        if (recover) {
            System.out.println("\n\n[RECOVER-CHAIN] Current Status: ");
            aWorld.print("");
        }
        System.out.println("\nSending to " + aWorld.getName() + " ExitChain\n");
        this.nextChain.doWork(aWorld);
    }

}
