package main.java.behavior;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import main.java.creation.Character;
import main.java.creation.Base;
import main.java.creation.World;

public class FightChain implements Chain {
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
                    b.setPoints(b.getPoints() + 50);
                } else if (baseVector.size() == 1) {
                    beginBattle(b, baseVector.get(0));
                } else {
                    // Pick the smallest one to battle in order to secure victory
                    // sort the vector of sizes and use index 0;
                    Collections.sort(memberSizesVector);
                    for (Base enemyBase : enemyBases) {
                        if (enemyBase.getTotalMembers() == memberSizesVector.get(0)) {
                            beginBattle(b, enemyBase);
                            break;
                        }
                    }

                }

            }
        }
        System.out.println("\n\nSending to RecoverChain\n");
        aWorld.print("");
        // this.nextChain.doWork(aWorld);

    }

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

            System.out.println("\n_____________________________________________________________________\n"
                    + fighter.getName() + "\t-[Lvl]: " + fighter.getLevel()
                    + "  -[Health]: " + fighter.getHealth() + "\t-[Power]: "
                    + fighter.getPower() + "\t-[Weakness]: " + fighter.getWeakness());

            System.out.println("\t\t\t\t----VS---- \n" + opponent.getName() + "\t-[Lvl]: "
                    + opponent.getLevel() + "  -[Health]: " + opponent.getHealth()
                    + "\t-[Power]: " + opponent.getPower() + "\t[Weakness]: "
                    + opponent.getWeakness()
                    + "\n_____________________________________________________________________\n");

            // maxAttacks number of rounds per base member
            for (int j = 0; j < maxAttacks; j++) {
                System.out.println("\n\t\t\t[Round " + (j + 1) + "]");
                opponentMove = opponent.getLevel() * 3;
                fighterMove = fighter.getLevel() * 3;
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
                System.out.println("\nEnd of Round " + j + 1 + " : \n" + fighter.getName() + "'s [Health]: "
                        + fighter.getHealth() + "\n" + opponent.getName()
                        + "'s [Health]: " + opponent.getHealth() + "\n---------------------------------");

            }

            index++;
        }
        try {
            base.removeDead();
            enemyBase.removeDead();
        } catch (Exception e) {
            System.out.println("[Exception] in FightChain: " + e.getMessage());
        }
    }

}
