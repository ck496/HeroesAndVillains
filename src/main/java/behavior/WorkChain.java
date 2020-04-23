package main.java.behavior;

import java.util.Random;
import java.util.Vector;

import main.java.creation.Base;
import main.java.creation.World;

public class WorkChain implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextChain) {
        this.nextChain = nextChain;

    }

    @Override
    public void doWork(World aWorld) {
//        Base base1 = aWorld.getBase(1);
//        Base base2= aWorld.getBase(2);
//        Base base3 = aWorld.getBase(3);
//        Base base4= aWorld.getBase(4);
        Vector<Base> baseVector = aWorld.getBaseVector();
        boolean switchChain = false;
        int newPoints = 0;
        int diceRoll = 0;
        // int numKileld = 0;
        Random random = new Random();

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
                            System.out.println("\n\t\t    [ACHIEVEMENT UNLOCKED] \n\tTeam " + b.getName()
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

        this.nextChain.doWork(aWorld);
    }

}
