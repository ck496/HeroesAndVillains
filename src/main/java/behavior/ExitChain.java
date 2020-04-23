package main.java.behavior;

import java.util.Vector;

import main.java.creation.Base;
import main.java.creation.World;

public class ExitChain implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextChain) {
        this.nextChain = nextChain;
    }

    @Override
    public void doWork(World aWorld) {
        Vector<Base> baseVector = aWorld.getBaseVector();
        String state1 = baseVector.get(0).getState();
        String state2 = baseVector.get(1).getState();
        String state3 = baseVector.get(2).getState();
        String state4 = baseVector.get(3).getState();

        System.out.println("\n\n\t\t\t[EXIT-CHAIN]\n");

        if (state1.equals(state2) && state2.equals(state3) && state3.equals(state4)) {
            aWorld.setState(state1.equals("Base") ? "Secured" : "Fallen");
            System.out.println("\n\n\t\t\t" + aWorld.getName() + " is "
                    + aWorld.getState() + "!!");
            if (aWorld.getState().equals("Secured")) {
                System.out.println("\n\n[Exit-CHAIN] Current Status: ");
                aWorld.print("");
                System.out.println("\n\n\t\t\tThe Heros have once again triumphed "
                        + "the villans" + "\n\t\t\tand saved the fate of " + aWorld.getName()
                        + "\n\n" + aWorld.getName() + "is Secure for now!");
                System.out.println("\n\nExitting Chain for " + aWorld.getName() + "....");

            } else {
                System.out.println("\n\n[Exit-CHAIN] Current Status: ");
                aWorld.print("");
                System.out.println("\n\n\t\t\tThe Heros fought the good fight but the"
                        + " Villians have defeated them....."
                        + "\n\n\t\t\t" + aWorld.getName() + " has fallen.....");
                System.out.println("\n\nExitting Chain for " + aWorld.getName() + "....");
            }
        } else {
            System.out.println("\n\n\t\t\t" + aWorld.getName() + " is still At Risk, continue working");

            System.out.println("\n\n[Exit-CHAIN] Current Status: ");
            aWorld.print("");
            System.out.println("\nSending to WorkChain\n");
            this.nextChain.doWork(aWorld);
        }

    }

}
