package main.java.behavior;

import java.util.Random;
import java.util.Vector;

import main.java.creation.Base;
import main.java.creation.Character;
import main.java.creation.World;

/**
 * CreateChain class handles the behavior "Work" and allows each of the base to
 * decide to go to next chain to fight or create a member.Base needs at at least
 * 5 members to fight. If create is picked, create a new character and either
 * add it to current base, an allied base if their base is filled or to another
 * world if all the bases in the current world is filled.
 * 
 * <P>
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
 */
public class CreateChain implements Chain {
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
     * Handles the logic to see how to create or go to fight. If create, then calls
     * the addMEmeberToBase function to create a member and add them to the
     * specified base.
     * 
     * @param aWorld to do work on
     */
    @Override
    public void doWork(World aWorld) {
        Vector<Base> baseVector = aWorld.getBaseVector();
        Random random = new Random();
        int newPoints = 0;
        boolean fight = false;

        System.out.println("\n\n\t\t\t[CREATE-CHAIN]\n");

        for (Base b : baseVector) {
            if (b.getActionState().equals("Create") && b.getPoints() >= 50) {
                // need at least 4 members to fight
                if (b.getTotalMembers() < 4) {
                    // create new member in current base
                    System.out.println("\n\t\t    [ATTENTION:Create] \n   NOT ENOUGH to fight so "
                            + "Team " + b.getName() + " created a new member for the team!!\n");

                    addMemeberToBase(b, b.getWeakestMember());
                    newPoints = b.getPoints() - 50;
                    b.setPoints(newPoints);
                } else {
                    // roll dice to fight(3) or create
                    int diceRoll = random.nextInt(4 - 1 + 1) + 1;
                    if (diceRoll == 4) {
                        // got the 3 so send to fight
                        System.out.println("\n\t\t    [ATTENTION:Fight] \n\tTeam " + b.getName()
                                + " has a deiccded to FIGHT!!\n");
                        b.setActionState("Fight");
                        newPoints = b.getPoints() - 50;
                        b.setPoints(newPoints);
                        fight = true;
                        break;
                    } else {
                        // Didn't get the chance to fight so create a new member
                        // 1) if current base has less than 5 members then add to current base
                        if (b.getTotalMembers() < 5) {
                            System.out.println(
                                    "\n\t\t       [ATTENTION:Create] \n\tTeam " + b.getName()
                                            + " has a deiccded to Create a "
                                            + "\n\t new member and add them to the team !\n");
                            addMemeberToBase(b, b.getWeakestMember());
                            newPoints = b.getPoints() - 50;
                            b.setPoints(newPoints);
                        } else {
                            // 2) if current base members > 5
                            // 2.1) send to another base in current World if possible
                            boolean sentToNewWorld = true;
                            for (Base allyBase : baseVector) {
                                if (allyBase.getState().equals(b.getState())
                                        || allyBase.getState().equals("Open")) {
                                    if (allyBase.getTotalMembers() < 5) {
                                        System.out.println(
                                                "\n\t\t       [ATTENTION:Create] \n\tTeam "
                                                        + b.getName()
                                                        + " has a deiccded to Create and send "
                                                        + "\n\tnew memeber to an ALLY team in "
                                                        + aWorld.getName());
                                        addMemeberToBase(allyBase, b.getWeakestMember());
                                        newPoints = b.getPoints() - 50;
                                        b.setPoints(newPoints);
                                        sentToNewWorld = false;
                                        break;
                                    }
                                }
                            }

                            // 2.1) send to another another world in the universe if possible
                            if (sentToNewWorld) {
                                System.out.println(
                                        "-------------SEND TO CREATED TO ANOTHER WORLD---------"
                                                + "\n\t Not enough room in the base of this world");
                                fight = true;
                            }

                        }

                    }

                }
            }

        }
        if (!fight) {
            System.out.println("\n\n[Create-CHAIN] Current Status: ");
            aWorld.print("");
        }
        System.out.println("\nSending world " + aWorld.getName() + " to FightChain\n");
        this.nextChain.doWork(aWorld);
    }

    // Helper functions
    /**
     * addMEmeberToBase function creates a Character and add them to the specified
     * base based on a parent Character.
     * 
     * @param b      base to add to
     * @param parent to make new member from
     * @return true if no exception caught
     */
    private boolean addMemeberToBase(Base b, Character parent) {

        Character ch = new Character.Builder()
                .isHero((parent.isHero() ? true : false))
                .setLevel(parent.getLevel())
                .constructCharacter();
        // add it to base b
        try {

            b.addMember(ch);
            b.setActionState("Work");
            System.out.println("[ADD] : new member to Team " + b.getName()
                    + " from parent " + parent.getName());
            System.out.println("[Base]: " + b.getName());
            ch.print("");
            return true;
        } catch (Exception e) {
            System.out.println("[Exception] in CreateChain: " + e.getMessage());
        }
        return false;
    }

}
