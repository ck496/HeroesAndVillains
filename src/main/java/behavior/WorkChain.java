package main.java.behavior;

import java.util.Random;
import java.util.Vector;

import main.java.creation.Base;
import main.java.creation.World;

/**
 * WorkChain class handles the behavior "Work" and allows each of the bases to
 * Kill or Save People and get points for their work. When a base gets at least
 * 50 points they get to go to the next state and decide if they want to create
 * or fight
 * 
 * <p>
 * CHAIN OF RESPONSIBILITY DESIGN PATTERN is used to handle the different
 * behavior and states of all the different objects. A World object is passed to
 * the first class in the chain and it does the work and passes it to the next
 * and loops back until the Worlds "State" is no longer at risk
 * WorkChain->CreateChain->FightChain->ExitChain. ExitChain loops back to
 * WorkChain if the World's state is "At Risk", if the World is no longer "At
 * Risk" the program exits and prints final messages
 * 
 * @author Chris Kurian
 * @version 2.0
 *
 */
public class WorkChain implements Chain {

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
     * Goes through the bases in the world to see if state = "Work" and if so allow
     * each of the bases to Kill or Save People and get points for their work. When
     * a base gets at least 50 points they get to go to the next state and decide if
     * they want to create or fight
     * 
     * @param aWorld to do work on
     */
    @Override
    public void doWork(World aWorld) {

        Vector<Base> baseVector = aWorld.getBaseVector();
        boolean switchChain = false;
        int newPoints = 0;
        int diceRoll = 0;
        // int numKileld = 0;
        Random random = new Random();

        System.out.println("\n\n\t\t\t[WORK-CHAIN]\n");

        while (!switchChain) {
            for (Base b : baseVector) {
                if (b.getActionState().equals("Work") && !b.getState().equals("Open")) {
                    diceRoll = random.nextInt(3 - 1 + 1) + 1;
                    if (b.getState().equals("Base") || b.getState().equals("Lair")) {
                        // TODO if u want to sub points from other bases turn
                        newPoints = diceRoll * b.getTotalMembers();
                        b.setPoints(b.getPoints() + newPoints);
                        if (b.getState().equals("Base")) {
                            System.out.println("\nTeam " + b.getName() + " saved " + newPoints
                                    + " people in " + aWorld.getName());
                        } else {
                            System.out.println("\nTeam " + b.getName() + " killed " + newPoints
                                    + " people in " + aWorld.getName());
                        }

                        if (b.getPoints() > 50) {
                            System.out.println(
                                    "\n\t\t    [ACHIEVEMENT UNLOCKED] \n\tTeam " + b.getName()
                                            + " has a total of " + b.getPoints() + " points, "
                                            + "\n\tThey get to Create a New Member or Fight!!");
                            b.setActionState("Create");
                            switchChain = true;
                            break;
                        }
                    }

                }
            }
        }

        System.out.println("\nSending world " + aWorld.getName() + " to CreateChain\n");
        this.nextChain.doWork(aWorld);
    }

}
