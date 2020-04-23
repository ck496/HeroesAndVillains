package main.java.behavior;

import java.util.Vector;

import main.java.creation.Base;
import main.java.creation.World;
import main.java.creation.Character;

public class RecoverChain implements Chain {
    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextChain) {
        this.nextChain = nextChain;

    }

    @Override
    public void doWork(World aWorld) {
        Vector<Base> baseVector = aWorld.getBaseVector();
        Vector<Character> memberVector;

        System.out.println("\n\n\t\t\t[RECOVER-CHAIN]\n");

        for (Base b : baseVector) {
            if (b.getActionState().equals("Recover")) {
                memberVector = b.getMembers();
                for (Character ch : memberVector) {
                    int health = ch.getHealth() + 20;
                    if (health > 100 && ch.getLevel() <= 5) {
                        ch.setHealth(100);
                    } else if (health > 100 && ch.getLevel() > 5) {
                        ch.setHealth(health);
                    } else {
                        ch.setHealth(health);
                    }
                }
                System.out.println("Recovered Team: " + b.getName());
                b.print("\t");
            }
            b.setActionState("Work");

        }
        System.out.println("\n\n[RECOVER-CHAIN] Current Status: ");
        aWorld.print("");
        System.out.println("\nSending to ExitChain\n");
        this.nextChain.doWork(aWorld);
    }

}
