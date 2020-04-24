package main.java.behavior;

import main.java.creation.Character;
import main.java.creation.Base;
import main.java.creation.World;
import java.util.Collections;
import java.util.Vector;

/**
 * FightChain class handles the behavior "Fight" and allows each of the members
 * to fight a member from another base. Also assigns levels and removes the
 * dead. If a base decides to fight they will fight each member on another team
 * chosen strategically to optimize wining. At the end of the fight everyone
 * that fought levels up by 1 but if a member kills an enemy, member get gets
 * their level +1 + enemy's level. After every fight, fighter have to recover so
 * the nextCHain is usually set to RecoverChain while this class is used.
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
public class FightChain implements Chain {
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
     * Goes through the bases in the world to see if state = "Fight" and if so it
     * sets up the fighting bases and calls the beginBattle method
     * 
     * @param aWorld
     */
    @Override
    public void doWork(World aWorld) {
        Vector<Base> baseVector = aWorld.getBaseVector();
        int newPoints = 0;
        Boolean fight = false;

        System.out.println("\n\n\t\t\t[FIGHT-CHAIN]\n");

        for (Base b : baseVector) {

            if (b.getActionState().equals("Fight")) {
                // Create a vector of enemy basses to choose from
                Vector<Base> enemyBases = new Vector<Base>();
                Vector<Integer> memberSizesVector = new Vector<Integer>();
                for (Base base : baseVector) {
                    if (!base.getState().equals(b.getState()) &&
                            !base.getState().equals("Open")) {
                        enemyBases.add(base);
                        memberSizesVector.add(base.getTotalMembers());

                    }
                }

                if (baseVector.size() == 0) {
                    System.out.println("\nNo Enemies to fight in this world, "
                            + "points are returend +50");
                    newPoints = b.getPoints();
                    b.setPoints(newPoints + 50);
                } else if (baseVector.size() == 1) {
                    beginBattle(b, baseVector.get(0));
                    fight = true;
                } else {
                    // Pick the smallest one to battle in order to secure victory
                    // sort the vector of sizes and use index 0;
                    Collections.sort(memberSizesVector);
                    for (Base enemyBase : enemyBases) {
                        if (enemyBase.getTotalMembers() == memberSizesVector.get(0)) {
                            beginBattle(b, enemyBase);
                            fight = true;
                            break;
                        }
                    }

                }

            }
        }
        if (fight) {
            System.out.println("\n\n[FIGHT-CHAIN] Current Status: ");
            aWorld.print("");
        }

        System.out.println("\nSending world " + aWorld.getName() + " to RecoverChain\n");
        this.nextChain.doWork(aWorld);
    }

    /**
     * BeginBattle takes 2 bases and allows each of the members from one base to
     * fight a member form the other base. Also assigns levels and removes up the
     * dead.
     * 
     * @param base
     * @param enemyBase
     */
    private void beginBattle(Base base, Base enemyBase) {
        int maxAttacks = 5;
        Vector<Character> baseMembers = base.getMembers();
        Vector<Character> enemyMembers = enemyBase.getMembers();
        int fighterHealth = 0;
        int opponentHealth = 0;
        int opponentMove = 0;
        int fighterMove = 0;
        int index = 0;

        System.out.println("\n\n+++++++++++++++++++++++ HEROS AND VILLANS FIGHT "
                + "+++++++++++++++++++++++\n"
                + "\n\t\t " + base.getName() + " -VS- " + enemyBase.getName());

        for (int i = 0; i < baseMembers.size(); i++) {

            if (index == enemyMembers.size()) {
                // index i used to traverse different sized enemyMember vectors
                index = 0;
            }
            Character fighter = baseMembers.get(i);
            Character opponent = enemyMembers.get(index);
            opponentHealth = opponent.getHealth();
            fighterHealth = fighter.getHealth();

            System.out.println("\n_________________________________________________"
                    + "_______________________________________\n"
                    + fighter.getName() + "\t-[Lvl]: " + fighter.getLevel()
                    + "  -[Health]: " + fighter.getHealth() + "\t-[Power]: "
                    + fighter.getPower() + "\t-[Weakness]: " + fighter.getWeakness());

            System.out.println("\t\t\t\t----VS---- \n" + opponent.getName() + "\t-[Lvl]: "
                    + opponent.getLevel() + "  -[Health]: " + opponent.getHealth()
                    + "\t-[Power]: " + opponent.getPower() + "\t[Weakness]: "
                    + opponent.getWeakness()
                    + "\n_________________________________________________________"
                    + "_______________________________\n");

            // maxAttacks number of rounds per base member
            for (int j = 0; j < maxAttacks; j++) {
                System.out.println("\n\t\t\t[Round " + (j + 1) + "]");
                opponentMove = opponent.getLevel() * 2;
                fighterMove = fighter.getLevel() * 2;
                // 1) fighter's power is opponents weakness
                if (fighter.getPower().equals(opponent.getWeakness())) {
                    // Double damage on fighters attacks
                    fighterMove = 2 * fighterMove;
                    opponentHealth = opponentHealth - fighterMove;
                    System.out.println("\n" + fighter.getName() + " used " + fighter.getPower()
                            + " attack on" + opponent.getName() + "\n--it is SUPER EFFECTIVE, Double Dammage: -"
                            + fighterMove);

                    // opp Move
                    fighterHealth = fighterHealth - opponentMove;
                    System.out.println("\n" + opponent.getName() + " used " + opponent.getPower()
                            + " attack on " + fighter.getName() + "\n--Damage: -" + opponentMove);

                } else if (opponent.getPower().equals(fighter.getWeakness())) {
                    opponentHealth = opponentHealth - fighterMove;
                    System.out.println("\n" + fighter.getName() + " used " + fighter.getPower()
                            + " attack on" + opponent.getName() + "\n--Dammage: -" + fighterMove);

                    // Double damage on opps attacks
                    opponentMove = 2 * opponentMove;
                    fighterHealth = fighterHealth - opponentMove;
                    System.out.println("\n" + opponent.getName() + " used " + opponent.getPower()
                            + "attack on " + fighter.getName() + "\n--it is SUPER EFFECTIVE, Double Dammage: -" + opponentMove);
                } else {
                    opponentHealth = opponentHealth - fighterMove;
                    System.out.println("\n" + fighter.getName() + " used " + fighter.getPower()
                            + " attack on" + opponent.getName() + "\n--Dammage: -" + fighterMove);

                    fighterHealth = fighterHealth - opponentMove;
                    System.out.println("\n" + opponent.getName() + " used " + opponent.getPower()
                            + " attack on " + fighter.getName() + "\n--Damage: -" + opponentMove);
                }

                // if fighter health = 0 opp wins and gets up to fighters level vice versa
                if (fighterHealth <= 0) {
                    int level = fighter.getLevel() + opponent.getLevel();
                    opponent.setLevel(level);
                    fighter.setHealth(0);
                    System.out.println("\n\t\t     [VICTORY] \n\t\t" + opponent.getName()
                            + " has Killed " + fighter.getName() + "\n\t\tLevel UP:"
                            + opponent.getName() + "'s new [Lvl]: " + opponent.getLevel());
                    break;
                } else if (opponentHealth <= 0) {
                    int level = fighter.getLevel() + opponent.getLevel();
                    fighter.setLevel(level);
                    opponent.setHealth(0);
                    System.out.println("\n\t\t     [VICTORY] \n\t" + fighter.getName() + " has Killed "
                            + opponent.getName() + "\n\t " + "Level UP!\n"
                            + fighter.getName() + "'s new [Lvl]: " + fighter.getLevel());
                    break;
                }

                fighter.setHealth(fighterHealth);
                opponent.setHealth(opponentHealth);
                System.out.println("\nEnd of Round " + (j + 1) + " : \n" + fighter.getName() + "'s [Health]: "
                        + fighter.getHealth() + "\n" + opponent.getName()
                        + "'s [Health]: " + opponent.getHealth() + "\n---------------------------------");

            }

            index++;
        }
        // Done Looping
        // Set Action state to recover so bases that fought can recover
        base.setActionState("Recover");
        enemyBase.setActionState("Recover");

        // Remove dead
        try {
            base.removeDead();
            enemyBase.removeDead();
        } catch (Exception e) {
            System.out.println("[Exception] in FightChain: " + e.getMessage());
        }
        // Level up the members who fought anmd are alive by 1
        baseMembers = base.getMembers();
        for (Character ch : baseMembers) {
            int level = ch.getLevel() + 1;
            ch.setLevel(level);
        }
        enemyMembers = enemyBase.getMembers();
        for (Character ch : enemyMembers) {
            int level = ch.getLevel() + 1;
            ch.setLevel(level);
        }

    }

}
