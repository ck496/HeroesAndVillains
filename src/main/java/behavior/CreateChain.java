package main.java.behavior;

import java.util.Random;
import java.util.Vector;

import main.java.creation.Base;
import main.java.creation.Character;
import main.java.creation.World;

public class CreateChain implements Chain {
    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextChain) {
        this.nextChain = nextChain;

    }

    @Override
    public void doWork(World aWorld) {
        Vector<Base> baseVector = aWorld.getBaseVector();
        Random random = new Random();
        int newPoints = 0;

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
                    int diceRoll = random.nextInt(3 - 1 + 1) + 1;
                    if (diceRoll == 3 || diceRoll == 2) {
                        // got the 3 so send to fight
                        System.out.println("\n\t\t    [ATTENTION:Fight] \n\tTeam " + b.getName()
                                + " has a deiccded to FIGHT!!\n");
                        b.setActionState("Fight");
                        newPoints = b.getPoints() - 50;
                        b.setPoints(newPoints);
                        break;
                    } else {
                        // Didn't get the chance to fight so create a new member
                        // 1) if current base has less than 5 members then add to current base
                        if (b.getTotalMembers() < 5) {
                            System.out.println("\n\t\t       [ATTENTION:Create] \n\tTeam " + b.getName()
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
                                if (allyBase.getState().equals(b.getState()) ||
                                        allyBase.getState().equals("Open")) {
                                    if (allyBase.getTotalMembers() < 5) {
                                        System.out.println("\n\t\t       [ATTENTION:Create] \n\tTeam " + b.getName()
                                                + " has a deiccded to Create and send "
                                                + "\n\tnew memeber to an ALLY team in " + aWorld.getName());
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
                                System.out.println("-------------SEND TO ANOTHER WORLD---------"
                                        + "\n\t Not Implemented");

                            }

                        }

                    }

                }
            }

        }

        // Send to FightChain
        nextChain.doWork(aWorld);
        // System.out.println("\n\nCREATE CHAIN DONE SEND TO NEXT");
    }

    // Helper functions

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

            ch.print("");
            System.out.println();
            b.print("");
            return true;
        } catch (Exception e) {
            System.out.println("[Exception] in CreateChain: " + e.getMessage());
        }
        return false;
    }

}
